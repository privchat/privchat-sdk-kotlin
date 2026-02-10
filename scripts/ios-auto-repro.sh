#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"

# Load JDK from local shell config without sourcing full rc files.
if [ -z "${JAVA_HOME:-}" ]; then
  if [ -f "$HOME/.xzshrc" ]; then
    ZSH_JAVA_HOME="$(sed -n 's/^export JAVA_HOME=//p' "$HOME/.xzshrc" | tail -n 1 | tr -d '"' | tr -d "'")"
    if [ -n "$ZSH_JAVA_HOME" ] && [ -d "$ZSH_JAVA_HOME" ]; then
      export JAVA_HOME="$ZSH_JAVA_HOME"
    fi
  fi
fi
if [ -z "${JAVA_HOME:-}" ]; then
  if [ -f "$HOME/.zshrc" ]; then
    ZSH_JAVA_HOME="$(sed -n 's/^export JAVA_HOME=//p' "$HOME/.zshrc" | tail -n 1 | tr -d '"' | tr -d "'")"
    if [ -n "$ZSH_JAVA_HOME" ] && [ -d "$ZSH_JAVA_HOME" ]; then
      export JAVA_HOME="$ZSH_JAVA_HOME"
    fi
  fi
fi
if [ -z "${JAVA_HOME:-}" ] && command -v /usr/libexec/java_home >/dev/null 2>&1; then
  export JAVA_HOME="$(/usr/libexec/java_home)"
fi

SIMULATOR_NAME="${SIMULATOR_NAME:-iPhone 17 Pro}"
BUNDLE_ID="${BUNDLE_ID:-com.github.privchat.app}"
SCHEME="${SCHEME:-iosApp}"
WORKSPACE_PATH="${WORKSPACE_PATH:-$ROOT_DIR/sample/iosApp/iosApp.xcworkspace}"
DERIVED_DATA="${DERIVED_DATA:-$ROOT_DIR/.ios-derived-data}"
WAIT_SECONDS="${WAIT_SECONDS:-45}"
RUN_BUILD_SCRIPT="${RUN_BUILD_SCRIPT:-1}"
BUILD_ARGS="${BUILD_ARGS:-}"

LOG_DIR="$ROOT_DIR/.repro-logs"
mkdir -p "$LOG_DIR"
STAMP="$(date +%Y%m%d-%H%M%S)"
RUNTIME_LOG="$LOG_DIR/ios-runtime-$STAMP.log"
SUMMARY_LOG="$LOG_DIR/ios-summary-$STAMP.log"
APP_STDOUT_LOG="$LOG_DIR/ios-app-stdout-$STAMP.log"
APP_PROCESS_LOG="$LOG_DIR/ios-app-process-$STAMP.log"
HANG_SAMPLE_LOG="$LOG_DIR/ios-hang-sample-$STAMP.txt"
CRASH_IPS_LOG="$LOG_DIR/ios-crash-$STAMP.ips"
CRASH_TOP_LOG="$LOG_DIR/ios-crash-top-$STAMP.log"

terminate_pid() {
  local pid="$1"
  [ -z "${pid:-}" ] && return 0
  kill "$pid" >/dev/null 2>&1 || true
  for _ in 1 2 3 4 5; do
    if ! ps -p "$pid" >/dev/null 2>&1; then
      return 0
    fi
    sleep 1
  done
  kill -9 "$pid" >/dev/null 2>&1 || true
}

run_with_soft_timeout() {
  local seconds="$1"
  shift
  local kill_pattern="${SOFT_TIMEOUT_KILL_PATTERN:-}"
  (
    "$@" &
    local cmd_pid=$!
    (
      sleep "$seconds"
      kill "$cmd_pid" >/dev/null 2>&1 || true
      if [ -n "$kill_pattern" ]; then
        pkill -f "$kill_pattern" >/dev/null 2>&1 || true
      fi
      sleep 1
      kill -9 "$cmd_pid" >/dev/null 2>&1 || true
    ) &
    local watchdog_pid=$!
    wait "$cmd_pid"
    local rc=$?
    kill "$watchdog_pid" >/dev/null 2>&1 || true
    exit "$rc"
  )
}

echo "== iOS auto repro =="
echo "root: $ROOT_DIR"
echo "simulator: $SIMULATOR_NAME"
echo "bundle id: $BUNDLE_ID"
echo "workspace: $WORKSPACE_PATH"
echo "runtime log: $RUNTIME_LOG"
echo "summary log: $SUMMARY_LOG"
echo "app stdout: $APP_STDOUT_LOG"
echo "app process: $APP_PROCESS_LOG"
echo "hang sample: $HANG_SAMPLE_LOG"
echo "crash ips: $CRASH_IPS_LOG"
echo "crash top: $CRASH_TOP_LOG"

if [ "$RUN_BUILD_SCRIPT" = "1" ]; then
  echo ""
  echo "== 1) build-ios.sh =="
  if [ -n "$BUILD_ARGS" ]; then
    # shellcheck disable=SC2086
    ./build-ios.sh $BUILD_ARGS
  else
    ./build-ios.sh
  fi
fi

echo ""
echo "== 2) resolve simulator =="
SIM_UDID="$(xcrun simctl list devices available | sed -n "s/.*$SIMULATOR_NAME (\\([A-F0-9-]*\\)).*/\\1/p" | head -n 1)"
if [ -z "$SIM_UDID" ]; then
  echo "error: simulator not found: $SIMULATOR_NAME"
  exit 1
fi
echo "udid: $SIM_UDID"
xcrun simctl boot "$SIM_UDID" >/dev/null 2>&1 || true
xcrun simctl bootstatus "$SIM_UDID" -b

echo ""
echo "== 3) xcodebuild app =="
env JAVA_HOME="$JAVA_HOME" OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED=YES xcodebuild \
  -workspace "$WORKSPACE_PATH" \
  -scheme "$SCHEME" \
  -configuration Debug \
  -destination "id=$SIM_UDID" \
  -derivedDataPath "$DERIVED_DATA" \
  build >/tmp/ios-auto-repro-xcodebuild.log

APP_PATH="$DERIVED_DATA/Build/Products/Debug-iphonesimulator/$SCHEME.app"
if [ ! -d "$APP_PATH" ]; then
  echo "error: built app not found: $APP_PATH"
  exit 1
fi
echo "app: $APP_PATH"

echo ""
echo "== 4) install + launch + capture logs =="
SOFT_TIMEOUT_KILL_PATTERN="simctl uninstall $SIM_UDID" run_with_soft_timeout 60 xcrun simctl uninstall "$SIM_UDID" "$BUNDLE_ID" >/dev/null 2>&1 || true
if ! SOFT_TIMEOUT_KILL_PATTERN="simctl install $SIM_UDID" run_with_soft_timeout 90 xcrun simctl install "$SIM_UDID" "$APP_PATH"; then
  echo "install timeout/failure, reboot simulator once and retry..."
  pkill -f "simctl install $SIM_UDID" >/dev/null 2>&1 || true
  xcrun simctl shutdown "$SIM_UDID" >/dev/null 2>&1 || true
  xcrun simctl boot "$SIM_UDID" >/dev/null 2>&1 || true
  xcrun simctl bootstatus "$SIM_UDID" -b
  SOFT_TIMEOUT_KILL_PATTERN="simctl install $SIM_UDID" run_with_soft_timeout 90 xcrun simctl install "$SIM_UDID" "$APP_PATH"
fi
xcrun simctl terminate "$SIM_UDID" "$BUNDLE_ID" >/dev/null 2>&1 || true

PREDICATE='process == "iosApp" OR eventMessage CONTAINS[c] "LoginFlow" OR eventMessage CONTAINS[c] "Privchat" OR eventMessage CONTAINS[c] "panic" OR eventMessage CONTAINS[c] "fatal"'
xcrun simctl spawn "$SIM_UDID" log stream --style compact --level debug --predicate "$PREDICATE" >"$RUNTIME_LOG" 2>&1 &
LOG_PID=$!
trap 'terminate_pid "$LOG_PID"' EXIT

# Capture app stdout/stderr (print!/eprintln! and uncaught panic text).
LAUNCH_EPOCH="$(date +%s)"
xcrun simctl launch --console "$SIM_UDID" "$BUNDLE_ID" >"$APP_STDOUT_LOG" 2>&1 &
APP_CONSOLE_PID=$!
echo "launch: started with --console (pid=$APP_CONSOLE_PID)"
trap 'terminate_pid "$LOG_PID"; terminate_pid "$APP_CONSOLE_PID"' EXIT

sleep "$WAIT_SECONDS"
APP_PID="$(grep -Eo "${BUNDLE_ID}: [0-9]+" "$APP_STDOUT_LOG" | tail -n 1 | awk '{print $2}' || true)"
if [ -n "${APP_PID:-}" ] && ps -p "$APP_PID" >/dev/null 2>&1; then
  echo "app pid appears alive after wait: $APP_PID, collecting hang sample..."
  sample "$APP_PID" 5 -file "$HANG_SAMPLE_LOG" >/dev/null 2>&1 || true
fi
terminate_pid "$LOG_PID"
wait "$LOG_PID" >/dev/null 2>&1 || true
terminate_pid "$APP_CONSOLE_PID"
wait "$APP_CONSOLE_PID" >/dev/null 2>&1 || true
trap - EXIT

echo ""
echo "== 5) summary logs =="
SOFT_TIMEOUT_KILL_PATTERN="simctl spawn $SIM_UDID log show" run_with_soft_timeout 30 xcrun simctl spawn "$SIM_UDID" log show --last 10m --style compact --predicate "$PREDICATE" >"$SUMMARY_LOG" 2>/dev/null || true
SOFT_TIMEOUT_KILL_PATTERN="simctl spawn $SIM_UDID log show" run_with_soft_timeout 30 xcrun simctl spawn "$SIM_UDID" log show --last 10m --style compact --predicate 'process == "iosApp"' >"$APP_PROCESS_LOG" 2>/dev/null || true

# Collect crash report from this run only (match launch time and pid when available).
LATEST_IPS="$(
python - <<'PY' "$HOME/Library/Logs/DiagnosticReports" "${APP_PID:-}" "$LAUNCH_EPOCH"
import json
import pathlib
import sys
import datetime

report_dir = pathlib.Path(sys.argv[1])
target_pid = int(sys.argv[2]) if sys.argv[2].isdigit() else None
launch_epoch = int(sys.argv[3])

def to_epoch(ts: str) -> int:
    # Format example: "2026-02-09 23:44:01.00 +0800"
    dt = datetime.datetime.strptime(ts, "%Y-%m-%d %H:%M:%S.%f %z")
    return int(dt.timestamp())

candidates = sorted(report_dir.glob("iosApp-*.ips"), key=lambda p: p.stat().st_mtime, reverse=True)
for path in candidates:
    try:
        lines = path.read_text(errors="ignore").splitlines()
        if len(lines) < 2:
            continue
        header = json.loads(lines[0])
        body = json.loads("\n".join(lines[1:]))
        ts = header.get("timestamp")
        pid = body.get("pid")
        if not ts or not isinstance(pid, int):
            continue
        if target_pid is not None and pid != target_pid:
            continue
        if to_epoch(ts) + 2 < launch_epoch:
            continue
        print(path)
        break
    except Exception:
        continue
PY
)"
if [ -n "${LATEST_IPS:-}" ] && [ -f "$LATEST_IPS" ]; then
  cp "$LATEST_IPS" "$CRASH_IPS_LOG"
  python - <<'PY' "$CRASH_IPS_LOG" "$CRASH_TOP_LOG" >/dev/null 2>&1 || true
import json, sys, pathlib
ips = pathlib.Path(sys.argv[1])
top = pathlib.Path(sys.argv[2])
lines = ips.read_text(errors="ignore").splitlines()
if len(lines) < 2:
    raise SystemExit(0)
obj = json.loads("\n".join(lines[1:]))
ft = obj.get("faultingThread")
threads = obj.get("threads", [])
images = obj.get("usedImages", [])
with top.open("w") as f:
    exc = obj.get("exception", {})
    f.write(f"exception: {exc.get('type')} {exc.get('signal')} {exc.get('subtype')}\n")
    f.write(f"faultingThread: {ft}\n")
    if isinstance(ft, int) and 0 <= ft < len(threads):
        t = threads[ft]
        f.write(f"threadName: {t.get('name','')}\n")
        f.write("topFrames:\n")
        for fr in t.get("frames", [])[:40]:
            idx = fr.get("imageIndex", -1)
            img = images[idx].get("name") if isinstance(idx, int) and 0 <= idx < len(images) else "?"
            sym = fr.get("symbol", "<unknown>")
            off = fr.get("imageOffset", 0)
            f.write(f"- [{img}] +0x{off:x} {sym}\n")
PY
fi

echo "done."
echo "runtime log: $RUNTIME_LOG"
echo "summary log: $SUMMARY_LOG"
echo "app stdout: $APP_STDOUT_LOG"
echo "app process: $APP_PROCESS_LOG"
echo "hang sample: $HANG_SAMPLE_LOG"
echo "crash ips: $CRASH_IPS_LOG"
echo "crash top: $CRASH_TOP_LOG"
