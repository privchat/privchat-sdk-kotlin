#pragma once

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

typedef struct RustBuffer
{
    int64_t capacity;
    int64_t len;
    uint8_t *_Nullable data;
} RustBuffer;

typedef struct RustBufferByReference
{
    int64_t capacity;
    int64_t len;
    uint8_t *_Nullable data;
} RustBufferByReference;

typedef struct ForeignBytes
{
    int32_t len;
    const uint8_t *_Nullable data;
} ForeignBytes;

typedef struct UniffiRustCallStatus {
  int8_t code;
  RustBuffer errorBuf;
} UniffiRustCallStatus;
typedef void (*UniffiRustFutureContinuationCallback)(int64_t, int8_t
    );

typedef void (*UniffiForeignFutureDroppedCallback)(int64_t
    );

typedef void (*UniffiCallbackInterfaceFree)(int64_t
    );

typedef int64_t (*UniffiCallbackInterfaceClone)(int64_t
    );

typedef struct UniffiForeignFutureDroppedCallbackStruct {
    int64_t handle;
    UniffiForeignFutureDroppedCallback free;
} UniffiForeignFutureDroppedCallbackStruct;

typedef struct UniffiForeignFutureResultU8 {
    int8_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureResultU8;

typedef void (*UniffiForeignFutureCompleteU8)(int64_t, UniffiForeignFutureResultU8
    );

typedef struct UniffiForeignFutureResultI8 {
    int8_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureResultI8;

typedef void (*UniffiForeignFutureCompleteI8)(int64_t, UniffiForeignFutureResultI8
    );

typedef struct UniffiForeignFutureResultU16 {
    int16_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureResultU16;

typedef void (*UniffiForeignFutureCompleteU16)(int64_t, UniffiForeignFutureResultU16
    );

typedef struct UniffiForeignFutureResultI16 {
    int16_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureResultI16;

typedef void (*UniffiForeignFutureCompleteI16)(int64_t, UniffiForeignFutureResultI16
    );

typedef struct UniffiForeignFutureResultU32 {
    int32_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureResultU32;

typedef void (*UniffiForeignFutureCompleteU32)(int64_t, UniffiForeignFutureResultU32
    );

typedef struct UniffiForeignFutureResultI32 {
    int32_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureResultI32;

typedef void (*UniffiForeignFutureCompleteI32)(int64_t, UniffiForeignFutureResultI32
    );

typedef struct UniffiForeignFutureResultU64 {
    int64_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureResultU64;

typedef void (*UniffiForeignFutureCompleteU64)(int64_t, UniffiForeignFutureResultU64
    );

typedef struct UniffiForeignFutureResultI64 {
    int64_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureResultI64;

typedef void (*UniffiForeignFutureCompleteI64)(int64_t, UniffiForeignFutureResultI64
    );

typedef struct UniffiForeignFutureResultF32 {
    float returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureResultF32;

typedef void (*UniffiForeignFutureCompleteF32)(int64_t, UniffiForeignFutureResultF32
    );

typedef struct UniffiForeignFutureResultF64 {
    double returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureResultF64;

typedef void (*UniffiForeignFutureCompleteF64)(int64_t, UniffiForeignFutureResultF64
    );

typedef struct UniffiForeignFutureResultRustBuffer {
    RustBuffer returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureResultRustBuffer;

typedef void (*UniffiForeignFutureCompleteRustBuffer)(int64_t, UniffiForeignFutureResultRustBuffer
    );

typedef struct UniffiForeignFutureResultVoid {
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureResultVoid;

typedef void (*UniffiForeignFutureCompleteVoid)(int64_t, UniffiForeignFutureResultVoid
    );
