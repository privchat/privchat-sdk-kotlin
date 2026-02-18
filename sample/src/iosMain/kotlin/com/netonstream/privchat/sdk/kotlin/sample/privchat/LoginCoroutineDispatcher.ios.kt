package om.netonstream.privchat.sdk.kotlin.sample.privchat

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Kuikly 的渲染/属性更新要求运行在 context queue（主线程），否则会触发 assertContextQueue。
 */
actual val LoginCoroutineDispatcher: CoroutineDispatcher = Dispatchers.Main
