package om.netonstream.privchat.sdk.dto

/** 视频处理钩子（Contract v1.1） */
interface VideoProcessHook {
    fun process(op: MediaProcessOp, sourcePath: String, metaPath: String, outputPath: String): Result<Boolean>
}
