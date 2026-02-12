

@file:Suppress(
    "NAME_SHADOWING",
    "INCOMPATIBLE_MATCHING",
    "UNCHECKED_CAST",
    "RemoveRedundantBackticks",
    "KotlinRedundantDiagnosticSuppress",
    "UnusedImport",
    "unused",
    "RemoveRedundantQualifierName",
    "UnnecessaryOptInAnnotation"
)
@file:OptIn(ExperimentalForeignApi::class, kotlin.time.ExperimentalTime::class)


package uniffi.privchat_sdk_ffi

// Common helper code.
//
// Ideally this would live in a separate .kt file where it can be unittested etc
// in isolation, and perhaps even published as a re-useable package.
//
// However, it's important that the details of how this helper code works (e.g. the
// way that different builtin types are passed across the FFI) exactly match what's
// expected by the Rust code on the other side of the interface. In practice right
// now that means coming from the exact some version of `uniffi` that was used to
// compile the Rust component. The easiest way to ensure this is to bundle the Kotlin
// helpers directly inline like we're doing here.

import uniffi.runtime.*

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.COpaquePointerVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValue
import kotlinx.cinterop.DoubleVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.FloatVar
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.LongVar
import kotlinx.cinterop.ShortVar
import kotlinx.cinterop.get
import kotlinx.cinterop.pointed
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.useContents
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.cValue
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.plus
import kotlinx.cinterop.ptr
import kotlinx.cinterop.readValue
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.usePinned
import kotlin.experimental.ExperimentalNativeApi
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.value
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.write
import kotlin.coroutines.resume
import platform.posix.memcpy
import kotlin.coroutines.resume
import kotlin.native.ref.createCleaner
import kotlinx.atomicfu.AtomicBoolean
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okio.utf8Size



// Contains loading, initialization code,
// and the FFI Function declarations.

// Define FFI callback types

















































































































































































































































































































































































































































































































































































































































internal interface UniffiLib {
    companion object {
        internal val INSTANCE: UniffiLib by lazy {
            UniffiLibInstance().also { lib ->
             }
        }
        
        // The Cleaner for the whole library
        internal val CLEANER: UniffiCleaner by lazy {
            UniffiCleaner.create()
        }
    }

    fun uniffi_privchat_sdk_ffi_fn_clone_privchatclient(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Pointer?
    fun uniffi_privchat_sdk_ffi_fn_free_privchatclient(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_constructor_privchatclient_new(`config`: RustBufferByValue,uniffiCallStatus: UniffiRustCallStatus, 
    ): Pointer?
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_accept_friend_request(`ptr`: Pointer?,`fromUserId`: Long,`message`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_detail_remote(`ptr`: Pointer?,`userId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_share_card_remote(`ptr`: Pointer?,`userId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_update_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_ack_outbound_files(`ptr`: Pointer?,`queueIndex`: Long,`messageIds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_ack_outbound_messages(`ptr`: Pointer?,`messageIds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_channel_members(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`memberUids`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_reaction(`ptr`: Pointer?,`serverMessageId`: Long,`channelId`: RustBufferByValue,`emoji`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_reaction_blocking(`ptr`: Pointer?,`serverMessageId`: Long,`channelId`: RustBufferByValue,`emoji`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_server(`ptr`: Pointer?,`endpoint`: RustBufferByValue,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_to_blacklist(`ptr`: Pointer?,`blockedUserId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_assets_dir(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_auth_logout_remote(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_auth_refresh_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_authenticate(`ptr`: Pointer?,`userId`: Long,`token`: RustBufferByValue,`deviceId`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_batch_get_presence(`ptr`: Pointer?,`userIds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_build(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_builder(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_create_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_list_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_subscribe_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_content_list_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_content_publish_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_notification_mode(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_send_queue_set_enabled(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`enabled`: Byte,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_tags(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_unread_stats(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_check_blacklist(`ptr`: Pointer?,`targetUserId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_check_friend(`ptr`: Pointer?,`friendId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_clear_local_state(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_clear_presence_cache(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_config(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_connect(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_connect_blocking(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_connection_state(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_connection_timeout(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_create_group(`ptr`: Pointer?,`name`: RustBufferByValue,`description`: RustBufferByValue,`memberIds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_create_local_message(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_data_dir(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_debug_mode(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Byte
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_blacklist_entry(`ptr`: Pointer?,`blockedUserId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_channel_member(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`memberUid`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_friend(`ptr`: Pointer?,`friendId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_disconnect(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_dm_peer_user_id(`ptr`: Pointer?,`channelId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_cache(`ptr`: Pointer?,`sourcePath`: RustBufferByValue,`fileName`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_message_dir(`ptr`: Pointer?,`sourcePath`: RustBufferByValue,`messageId`: Long,`fileName`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_path(`ptr`: Pointer?,`sourcePath`: RustBufferByValue,`targetPath`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_edit_message(`ptr`: Pointer?,`messageId`: Long,`content`: RustBufferByValue,`editedAt`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_edit_message_blocking(`ptr`: Pointer?,`messageId`: Long,`content`: RustBufferByValue,`editedAt`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_outbound_file(`ptr`: Pointer?,`messageId`: Long,`routeKey`: RustBufferByValue,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_outbound_message(`ptr`: Pointer?,`messageId`: Long,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_text(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`fromUid`: Long,`content`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_enter_background(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_enter_foreground(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_entity_sync_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_event_config(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_event_stream_cursor(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_events_since(`ptr`: Pointer?,`sequenceId`: Long,`limit`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_fetch_group_members_remote(`ptr`: Pointer?,`groupId`: Long,`page`: RustBufferByValue,`pageSize`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_fetch_presence(`ptr`: Pointer?,`userIds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_api_base_url(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_request_upload_token_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_upload_callback_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_all_unread_mention_counts(`ptr`: Pointer?,`userId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_all_user_settings(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_blacklist(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_by_id(`ptr`: Pointer?,`channelId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_extra(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_list_entries(`ptr`: Pointer?,`page`: Long,`pageSize`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_sync_state(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_unread_count(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channels(`ptr`: Pointer?,`limit`: Long,`offset`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_connection_state(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_connection_summary(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_device_push_status(`ptr`: Pointer?,`deviceId`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_earliest_id(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_friend_pending_requests(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_friends(`ptr`: Pointer?,`limit`: Long,`offset`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_by_id(`ptr`: Pointer?,`groupId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_info(`ptr`: Pointer?,`groupId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_members(`ptr`: Pointer?,`groupId`: Long,`limit`: Long,`offset`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_groups(`ptr`: Pointer?,`limit`: Long,`offset`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_message_by_id(`ptr`: Pointer?,`messageId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_message_extra(`ptr`: Pointer?,`messageId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_messages(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`limit`: Long,`offset`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_messages_remote(`ptr`: Pointer?,`channelId`: Long,`beforeServerMessageId`: RustBufferByValue,`limit`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_or_create_direct_channel(`ptr`: Pointer?,`peerUserId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_presence(`ptr`: Pointer?,`userId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_presence_stats(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_privacy_settings(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_profile(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_total_unread_count(`ptr`: Pointer?,`excludeMuted`: Byte,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_typing_stats(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_unread_mention_count(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`userId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_user_by_id(`ptr`: Pointer?,`userId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_user_setting(`ptr`: Pointer?,`key`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_add_members_remote(`ptr`: Pointer?,`groupId`: Long,`userIds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_approval_handle_remote(`ptr`: Pointer?,`approvalId`: Long,`approved`: Byte,`reason`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_approval_list_remote(`ptr`: Pointer?,`groupId`: Long,`page`: RustBufferByValue,`pageSize`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_get_settings_remote(`ptr`: Pointer?,`groupId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_leave_remote(`ptr`: Pointer?,`groupId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_mute_all_remote(`ptr`: Pointer?,`groupId`: Long,`enabled`: Byte,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_mute_member_remote(`ptr`: Pointer?,`groupId`: Long,`userId`: Long,`durationSeconds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_qrcode_generate_remote(`ptr`: Pointer?,`groupId`: Long,`expireSeconds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_qrcode_join_remote(`ptr`: Pointer?,`qrKey`: RustBufferByValue,`token`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_remove_member_remote(`ptr`: Pointer?,`groupId`: Long,`userId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_set_role_remote(`ptr`: Pointer?,`groupId`: Long,`userId`: Long,`role`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_transfer_owner_remote(`ptr`: Pointer?,`groupId`: Long,`targetUserId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_unmute_member_remote(`ptr`: Pointer?,`groupId`: Long,`userId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_update_settings_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_heartbeat_interval(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_hide_channel(`ptr`: Pointer?,`channelId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_http_client_config(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_image_send_max_edge(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Int
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_invite_to_group(`ptr`: Pointer?,`groupId`: Long,`memberIds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_bootstrap_completed(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_connected(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_event_read_by(`ptr`: Pointer?,`serverMessageId`: Long,`userId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_initialized(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_shutting_down(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_supervised_sync_running(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Byte
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_join_group_by_qrcode(`ptr`: Pointer?,`qrKey`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_kv_get(`ptr`: Pointer?,`key`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_kv_put(`ptr`: Pointer?,`key`: RustBufferByValue,`value`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_leave_channel(`ptr`: Pointer?,`channelId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_leave_group(`ptr`: Pointer?,`groupId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_blacklist_entries(`ptr`: Pointer?,`limit`: Long,`offset`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_channel_members(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`limit`: Long,`offset`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_channels(`ptr`: Pointer?,`limit`: Long,`offset`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_friends(`ptr`: Pointer?,`limit`: Long,`offset`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_group_members(`ptr`: Pointer?,`groupId`: Long,`limit`: Long,`offset`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_groups(`ptr`: Pointer?,`limit`: Long,`offset`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_local_accounts(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_message_reactions(`ptr`: Pointer?,`messageId`: Long,`limit`: Long,`offset`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_messages(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`limit`: Long,`offset`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_my_devices(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_pending_reminders(`ptr`: Pointer?,`uid`: Long,`limit`: Long,`offset`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_reactions(`ptr`: Pointer?,`serverMessageId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_unread_mention_message_ids(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`userId`: Long,`limit`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_users_by_ids(`ptr`: Pointer?,`userIds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_log_connection_state(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_login(`ptr`: Pointer?,`username`: RustBufferByValue,`password`: RustBufferByValue,`deviceId`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_logout(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_all_mentions_read(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`userId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_as_read(`ptr`: Pointer?,`channelId`: Long,`serverMessageId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_as_read_blocking(`ptr`: Pointer?,`channelId`: Long,`serverMessageId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_channel_read(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_fully_read_at(`ptr`: Pointer?,`channelId`: Long,`serverMessageId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_mention_read(`ptr`: Pointer?,`messageId`: Long,`userId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_message_sent(`ptr`: Pointer?,`messageId`: Long,`serverMessageId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_reminder_done(`ptr`: Pointer?,`reminderId`: Long,`done`: Byte,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_read_list(`ptr`: Pointer?,`serverMessageId`: Long,`channelId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_read_stats(`ptr`: Pointer?,`serverMessageId`: Long,`channelId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_unread_count_remote(`ptr`: Pointer?,`channelId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mute_channel(`ptr`: Pointer?,`channelId`: Long,`muted`: Byte,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_needs_sync(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_next_event(`ptr`: Pointer?,`timeoutMs`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_next_event_envelope(`ptr`: Pointer?,`timeoutMs`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_app_background(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_app_foreground(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_connection_state_changed(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_message_received(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_reaction_changed(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_typing_indicator(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_own_last_read(`ptr`: Pointer?,`channelId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_paginate_back(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`page`: Long,`pageSize`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_paginate_forward(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`page`: Long,`pageSize`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_peek_outbound_files(`ptr`: Pointer?,`queueIndex`: Long,`limit`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_peek_outbound_messages(`ptr`: Pointer?,`limit`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_pin_channel(`ptr`: Pointer?,`channelId`: Long,`pinned`: Byte,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_ping(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_generate(`ptr`: Pointer?,`qrType`: RustBufferByValue,`payload`: RustBufferByValue,`expireSeconds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_list(`ptr`: Pointer?,`includeRevoked`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_refresh(`ptr`: Pointer?,`qrType`: RustBufferByValue,`targetId`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_resolve(`ptr`: Pointer?,`qrKey`: RustBufferByValue,`token`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_revoke(`ptr`: Pointer?,`qrKey`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_queue_config(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_reaction_stats(`ptr`: Pointer?,`serverMessageId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_reactions(`ptr`: Pointer?,`serverMessageId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_reactions_batch(`ptr`: Pointer?,`serverMessageIds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_recall_message(`ptr`: Pointer?,`serverMessageId`: Long,`channelId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_recall_message_blocking(`ptr`: Pointer?,`serverMessageId`: Long,`channelId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_recent_events(`ptr`: Pointer?,`limit`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_record_mention(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_register(`ptr`: Pointer?,`username`: RustBufferByValue,`password`: RustBufferByValue,`deviceId`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_register_lifecycle_hook(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_reject_friend_request(`ptr`: Pointer?,`fromUserId`: Long,`message`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_channel_member(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`memberUid`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_from_blacklist(`ptr`: Pointer?,`blockedUserId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_group_member(`ptr`: Pointer?,`groupId`: Long,`userId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_reaction(`ptr`: Pointer?,`serverMessageId`: Long,`emoji`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_require_current_user_id(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_channel_id_by_server_message_id(`ptr`: Pointer?,`serverMessageId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_channel_type(`ptr`: Pointer?,`channelId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_local_message_id_by_server_message_id(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`serverMessageId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_retry_config(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_retry_message(`ptr`: Pointer?,`messageId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_rpc_call(`ptr`: Pointer?,`route`: RustBufferByValue,`bodyJson`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_run_bootstrap_sync(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_channel(`ptr`: Pointer?,`keyword`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_messages(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`keyword`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_user_by_qrcode(`ptr`: Pointer?,`qrKey`: RustBufferByValue,`token`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_users(`ptr`: Pointer?,`query`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_seen_by_for_event(`ptr`: Pointer?,`serverMessageId`: Long,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_attachment_bytes(`ptr`: Pointer?,`messageId`: Long,`routeKey`: RustBufferByValue,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_attachment_from_path(`ptr`: Pointer?,`messageId`: Long,`routeKey`: RustBufferByValue,`path`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_friend_request(`ptr`: Pointer?,`targetUserId`: Long,`message`: RustBufferByValue,`source`: RustBufferByValue,`sourceId`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_local_message_now(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`fromUid`: Long,`content`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_blocking(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`fromUid`: Long,`content`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_with_input(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_with_options(`ptr`: Pointer?,`input`: RustBufferByValue,`options`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_queue_set_enabled(`ptr`: Pointer?,`enabled`: Byte,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_typing(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`isTyping`: Byte,`actionType`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_server_config(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_servers(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_session_snapshot(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_favourite(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`enabled`: Byte,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_low_priority(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`enabled`: Byte,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_notification_mode(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`mode`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_current_uid(`ptr`: Pointer?,`uid`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_pinned(`ptr`: Pointer?,`messageId`: Long,`isPinned`: Byte,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_read(`ptr`: Pointer?,`messageId`: Long,`channelId`: Long,`channelType`: Int,`isRead`: Byte,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_revoke(`ptr`: Pointer?,`messageId`: Long,`revoked`: Byte,`revoker`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_network_hint(`ptr`: Pointer?,`hint`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_user_setting(`ptr`: Pointer?,`key`: RustBufferByValue,`value`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_video_process_hook(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_shutdown(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_shutdown_blocking(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_supervised_sync(`ptr`: Pointer?,`intervalSecs`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_transport_disconnect_listener(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_typing(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_typing_blocking(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sticker_package_detail_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sticker_package_list_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_stop_supervised_sync(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_stop_typing(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_storage(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_events(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Byte
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_network_status(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Byte
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_presence(`ptr`: Pointer?,`userIds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_all_channels(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_batch_get_channel_pts_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_channel(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_entities(`ptr`: Pointer?,`entityType`: RustBufferByValue,`scope`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_entities_in_background(`ptr`: Pointer?,`entityType`: RustBufferByValue,`scope`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_get_channel_pts_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_get_difference_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_messages(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_messages_in_background(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_submit_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_hours(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Int
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_local(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_minutes(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Int
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_seconds(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Int
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_to_client_endpoint(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_unsubscribe_presence(`ptr`: Pointer?,`userIds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_device_push_state(`ptr`: Pointer?,`deviceId`: RustBufferByValue,`apnsArmed`: Byte,`pushToken`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_message_status(`ptr`: Pointer?,`messageId`: Long,`status`: Int,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_privacy_settings(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_profile(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_blacklist_entry(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel_extra(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel_member(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_friend(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_group(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_group_member(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_message_reaction(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_reminder(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_user(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_id(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_generate(`ptr`: Pointer?,`expireSeconds`: RustBufferByValue,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_get(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_refresh(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_storage_paths(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_wipe_current_user_full(`ptr`: Pointer?,
    ): Long
    fun uniffi_privchat_sdk_ffi_fn_func_build_time(uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_func_git_sha(uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun uniffi_privchat_sdk_ffi_fn_func_sdk_version(uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun ffi_privchat_sdk_ffi_rustbuffer_alloc(`size`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun ffi_privchat_sdk_ffi_rustbuffer_from_bytes(`bytes`: ForeignBytesByValue,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun ffi_privchat_sdk_ffi_rustbuffer_free(`buf`: RustBufferByValue,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun ffi_privchat_sdk_ffi_rustbuffer_reserve(`buf`: RustBufferByValue,`additional`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun ffi_privchat_sdk_ffi_rust_future_poll_u8(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_cancel_u8(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_free_u8(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_complete_u8(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Byte
    fun ffi_privchat_sdk_ffi_rust_future_poll_i8(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_cancel_i8(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_free_i8(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_complete_i8(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Byte
    fun ffi_privchat_sdk_ffi_rust_future_poll_u16(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_cancel_u16(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_free_u16(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_complete_u16(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Short
    fun ffi_privchat_sdk_ffi_rust_future_poll_i16(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_cancel_i16(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_free_i16(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_complete_i16(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Short
    fun ffi_privchat_sdk_ffi_rust_future_poll_u32(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_cancel_u32(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_free_u32(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_complete_u32(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Int
    fun ffi_privchat_sdk_ffi_rust_future_poll_i32(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_cancel_i32(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_free_i32(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_complete_i32(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Int
    fun ffi_privchat_sdk_ffi_rust_future_poll_u64(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_cancel_u64(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_free_u64(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_complete_u64(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Long
    fun ffi_privchat_sdk_ffi_rust_future_poll_i64(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_cancel_i64(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_free_i64(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_complete_i64(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Long
    fun ffi_privchat_sdk_ffi_rust_future_poll_f32(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_cancel_f32(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_free_f32(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_complete_f32(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Float
    fun ffi_privchat_sdk_ffi_rust_future_poll_f64(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_cancel_f64(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_free_f64(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_complete_f64(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Double
    fun ffi_privchat_sdk_ffi_rust_future_poll_pointer(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_cancel_pointer(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_free_pointer(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_complete_pointer(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Pointer?
    fun ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
    fun ffi_privchat_sdk_ffi_rust_future_poll_void(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_cancel_void(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_free_void(`handle`: Long,
    ): Unit
    fun ffi_privchat_sdk_ffi_rust_future_complete_void(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
    fun uniffi_privchat_sdk_ffi_checksum_func_build_time(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_func_git_sha(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_func_sdk_version(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_accept_friend_request(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_account_user_detail_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_account_user_share_card_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_account_user_update_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_ack_outbound_files(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_ack_outbound_messages(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_channel_members(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_reaction(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_reaction_blocking(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_server(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_to_blacklist(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_assets_dir(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_auth_logout_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_auth_refresh_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_authenticate(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_batch_get_presence(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_build(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_builder(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_broadcast_create_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_broadcast_list_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_broadcast_subscribe_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_content_list_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_content_publish_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_notification_mode(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_send_queue_set_enabled(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_tags(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_unread_stats(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_check_blacklist(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_check_friend(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_clear_local_state(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_clear_presence_cache(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_config(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connect(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connect_blocking(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connection_state(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connection_timeout(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_create_group(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_create_local_message(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_data_dir(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_debug_mode(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_delete_blacklist_entry(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_delete_channel_member(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_delete_friend(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_disconnect(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_dm_peer_user_id(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_download_attachment_to_cache(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_download_attachment_to_message_dir(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_download_attachment_to_path(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_edit_message(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_edit_message_blocking(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enqueue_outbound_file(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enqueue_outbound_message(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enqueue_text(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enter_background(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enter_foreground(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_entity_sync_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_event_config(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_event_stream_cursor(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_events_since(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_fetch_group_members_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_fetch_presence(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_file_api_base_url(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_file_request_upload_token_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_file_upload_callback_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_all_unread_mention_counts(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_all_user_settings(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_blacklist(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_by_id(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_extra(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_list_entries(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_sync_state(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_unread_count(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channels(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_connection_state(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_connection_summary(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_device_push_status(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_earliest_id(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_friend_pending_requests(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_friends(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_group_by_id(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_group_info(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_group_members(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_groups(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_message_by_id(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_message_extra(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_messages(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_messages_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_or_create_direct_channel(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_presence(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_presence_stats(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_privacy_settings(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_profile(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_total_unread_count(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_typing_stats(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_unread_mention_count(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_user_by_id(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_user_setting(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_add_members_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_approval_handle_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_approval_list_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_get_settings_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_leave_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_mute_all_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_mute_member_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_qrcode_generate_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_qrcode_join_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_remove_member_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_set_role_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_transfer_owner_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_unmute_member_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_update_settings_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_heartbeat_interval(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_hide_channel(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_http_client_config(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_image_send_max_edge(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_invite_to_group(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_bootstrap_completed(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_connected(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_event_read_by(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_initialized(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_shutting_down(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_supervised_sync_running(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_join_group_by_qrcode(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_kv_get(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_kv_put(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_leave_channel(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_leave_group(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_blacklist_entries(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_channel_members(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_channels(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_friends(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_group_members(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_groups(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_local_accounts(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_message_reactions(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_messages(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_my_devices(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_pending_reminders(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_reactions(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_unread_mention_message_ids(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_users_by_ids(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_log_connection_state(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_login(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_logout(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_all_mentions_read(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_as_read(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_as_read_blocking(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_channel_read(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_fully_read_at(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_mention_read(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_message_sent(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_reminder_done(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_message_read_list(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_message_read_stats(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_message_unread_count_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mute_channel(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_needs_sync(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_next_event(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_next_event_envelope(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_app_background(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_app_foreground(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_connection_state_changed(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_message_received(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_reaction_changed(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_typing_indicator(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_own_last_read(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_paginate_back(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_paginate_forward(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_peek_outbound_files(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_peek_outbound_messages(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_pin_channel(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_ping(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_generate(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_list(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_refresh(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_resolve(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_revoke(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_queue_config(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reaction_stats(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reactions(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reactions_batch(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_recall_message(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_recall_message_blocking(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_recent_events(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_record_mention(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_register(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_register_lifecycle_hook(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reject_friend_request(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_channel_member(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_from_blacklist(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_group_member(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_reaction(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_require_current_user_id(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_resolve_channel_id_by_server_message_id(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_resolve_channel_type(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_resolve_local_message_id_by_server_message_id(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_retry_config(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_retry_message(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_rpc_call(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_run_bootstrap_sync(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_channel(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_messages(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_user_by_qrcode(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_users(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_seen_by_for_event(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_attachment_bytes(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_attachment_from_path(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_friend_request(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_local_message_now(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message_blocking(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message_with_input(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message_with_options(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_queue_set_enabled(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_typing(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_server_config(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_servers(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_session_snapshot(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_channel_favourite(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_channel_low_priority(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_channel_notification_mode(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_current_uid(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_message_pinned(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_message_read(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_message_revoke(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_network_hint(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_user_setting(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_video_process_hook(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_shutdown(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_shutdown_blocking(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_supervised_sync(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_transport_disconnect_listener(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_typing(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_typing_blocking(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sticker_package_detail_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sticker_package_list_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_stop_supervised_sync(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_stop_typing(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_storage(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_subscribe_events(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_subscribe_network_status(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_subscribe_presence(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_all_channels(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_batch_get_channel_pts_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_channel(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_entities(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_entities_in_background(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_get_channel_pts_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_get_difference_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_messages(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_messages_in_background(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_submit_remote(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_hours(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_local(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_minutes(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_seconds(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_to_client_endpoint(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_unsubscribe_presence(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_device_push_state(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_message_status(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_privacy_settings(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_profile(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_blacklist_entry(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_channel(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_channel_extra(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_channel_member(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_friend(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_group(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_group_member(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_message_reaction(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_reminder(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_user(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_id(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_qrcode_generate(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_qrcode_get(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_qrcode_refresh(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_storage_paths(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_wipe_current_user_full(
    ): Short
    fun uniffi_privchat_sdk_ffi_checksum_constructor_privchatclient_new(
    ): Short
    fun ffi_privchat_sdk_ffi_uniffi_contract_version(
    ): Int
    
}

internal class UniffiLibInstance: UniffiLib {
    override fun uniffi_privchat_sdk_ffi_fn_clone_privchatclient(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Pointer?
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_clone_privchatclient(`ptr`?.inner,uniffiCallStatus.reinterpret(), )?.let { Pointer(it) }as Pointer?
    
    override fun uniffi_privchat_sdk_ffi_fn_free_privchatclient(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_free_privchatclient(`ptr`?.inner,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_constructor_privchatclient_new(`config`: RustBufferByValue,uniffiCallStatus: UniffiRustCallStatus, 
    ): Pointer?
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_constructor_privchatclient_new(`config` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,uniffiCallStatus.reinterpret(), )?.let { Pointer(it) }as Pointer?
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_accept_friend_request(`ptr`: Pointer?,`fromUserId`: Long,`message`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_accept_friend_request(`ptr`?.inner,`fromUserId`,`message` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_detail_remote(`ptr`: Pointer?,`userId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_detail_remote(`ptr`?.inner,`userId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_share_card_remote(`ptr`: Pointer?,`userId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_share_card_remote(`ptr`?.inner,`userId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_update_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_update_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_ack_outbound_files(`ptr`: Pointer?,`queueIndex`: Long,`messageIds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_ack_outbound_files(`ptr`?.inner,`queueIndex`,`messageIds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_ack_outbound_messages(`ptr`: Pointer?,`messageIds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_ack_outbound_messages(`ptr`?.inner,`messageIds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_channel_members(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`memberUids`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_channel_members(`ptr`?.inner,`channelId`,`channelType`,`memberUids` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_reaction(`ptr`: Pointer?,`serverMessageId`: Long,`channelId`: RustBufferByValue,`emoji`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_reaction(`ptr`?.inner,`serverMessageId`,`channelId` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`emoji` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_reaction_blocking(`ptr`: Pointer?,`serverMessageId`: Long,`channelId`: RustBufferByValue,`emoji`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_reaction_blocking(`ptr`?.inner,`serverMessageId`,`channelId` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`emoji` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_server(`ptr`: Pointer?,`endpoint`: RustBufferByValue,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_server(`ptr`?.inner,`endpoint` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_to_blacklist(`ptr`: Pointer?,`blockedUserId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_to_blacklist(`ptr`?.inner,`blockedUserId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_assets_dir(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_assets_dir(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_auth_logout_remote(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_auth_logout_remote(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_auth_refresh_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_auth_refresh_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_authenticate(`ptr`: Pointer?,`userId`: Long,`token`: RustBufferByValue,`deviceId`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_authenticate(`ptr`?.inner,`userId`,`token` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`deviceId` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_batch_get_presence(`ptr`: Pointer?,`userIds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_batch_get_presence(`ptr`?.inner,`userIds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_build(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_build(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_builder(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_builder(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_create_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_create_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_list_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_list_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_subscribe_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_subscribe_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_content_list_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_content_list_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_content_publish_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_content_publish_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_notification_mode(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_notification_mode(`ptr`?.inner,`channelId`,`channelType`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_send_queue_set_enabled(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`enabled`: Byte,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_send_queue_set_enabled(`ptr`?.inner,`channelId`,`channelType`,`enabled`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_tags(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_tags(`ptr`?.inner,`channelId`,`channelType`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_unread_stats(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_unread_stats(`ptr`?.inner,`channelId`,`channelType`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_check_blacklist(`ptr`: Pointer?,`targetUserId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_check_blacklist(`ptr`?.inner,`targetUserId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_check_friend(`ptr`: Pointer?,`friendId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_check_friend(`ptr`?.inner,`friendId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_clear_local_state(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_clear_local_state(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_clear_presence_cache(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_clear_presence_cache(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_config(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_config(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_connect(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_connect(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_connect_blocking(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_connect_blocking(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_connection_state(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_connection_state(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_connection_timeout(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_connection_timeout(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_create_group(`ptr`: Pointer?,`name`: RustBufferByValue,`description`: RustBufferByValue,`memberIds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_create_group(`ptr`?.inner,`name` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`description` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`memberIds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_create_local_message(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_create_local_message(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_data_dir(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_data_dir(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_debug_mode(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Byte
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_debug_mode(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as Byte
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_blacklist_entry(`ptr`: Pointer?,`blockedUserId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_blacklist_entry(`ptr`?.inner,`blockedUserId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_channel_member(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`memberUid`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_channel_member(`ptr`?.inner,`channelId`,`channelType`,`memberUid`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_friend(`ptr`: Pointer?,`friendId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_friend(`ptr`?.inner,`friendId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_disconnect(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_disconnect(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_dm_peer_user_id(`ptr`: Pointer?,`channelId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_dm_peer_user_id(`ptr`?.inner,`channelId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_cache(`ptr`: Pointer?,`sourcePath`: RustBufferByValue,`fileName`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_cache(`ptr`?.inner,`sourcePath` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`fileName` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_message_dir(`ptr`: Pointer?,`sourcePath`: RustBufferByValue,`messageId`: Long,`fileName`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_message_dir(`ptr`?.inner,`sourcePath` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`messageId`,`fileName` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_path(`ptr`: Pointer?,`sourcePath`: RustBufferByValue,`targetPath`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_path(`ptr`?.inner,`sourcePath` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`targetPath` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_edit_message(`ptr`: Pointer?,`messageId`: Long,`content`: RustBufferByValue,`editedAt`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_edit_message(`ptr`?.inner,`messageId`,`content` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`editedAt`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_edit_message_blocking(`ptr`: Pointer?,`messageId`: Long,`content`: RustBufferByValue,`editedAt`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_edit_message_blocking(`ptr`?.inner,`messageId`,`content` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`editedAt`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_outbound_file(`ptr`: Pointer?,`messageId`: Long,`routeKey`: RustBufferByValue,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_outbound_file(`ptr`?.inner,`messageId`,`routeKey` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_outbound_message(`ptr`: Pointer?,`messageId`: Long,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_outbound_message(`ptr`?.inner,`messageId`,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_text(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`fromUid`: Long,`content`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_text(`ptr`?.inner,`channelId`,`channelType`,`fromUid`,`content` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_enter_background(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_enter_background(`ptr`?.inner,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_enter_foreground(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_enter_foreground(`ptr`?.inner,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_entity_sync_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_entity_sync_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_event_config(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_event_config(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_event_stream_cursor(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_event_stream_cursor(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_events_since(`ptr`: Pointer?,`sequenceId`: Long,`limit`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_events_since(`ptr`?.inner,`sequenceId`,`limit`,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_fetch_group_members_remote(`ptr`: Pointer?,`groupId`: Long,`page`: RustBufferByValue,`pageSize`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_fetch_group_members_remote(`ptr`?.inner,`groupId`,`page` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`pageSize` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_fetch_presence(`ptr`: Pointer?,`userIds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_fetch_presence(`ptr`?.inner,`userIds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_api_base_url(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_api_base_url(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_request_upload_token_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_request_upload_token_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_upload_callback_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_upload_callback_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_all_unread_mention_counts(`ptr`: Pointer?,`userId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_all_unread_mention_counts(`ptr`?.inner,`userId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_all_user_settings(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_all_user_settings(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_blacklist(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_blacklist(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_by_id(`ptr`: Pointer?,`channelId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_by_id(`ptr`?.inner,`channelId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_extra(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_extra(`ptr`?.inner,`channelId`,`channelType`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_list_entries(`ptr`: Pointer?,`page`: Long,`pageSize`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_list_entries(`ptr`?.inner,`page`,`pageSize`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_sync_state(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_sync_state(`ptr`?.inner,`channelId`,`channelType`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_unread_count(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_unread_count(`ptr`?.inner,`channelId`,`channelType`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channels(`ptr`: Pointer?,`limit`: Long,`offset`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channels(`ptr`?.inner,`limit`,`offset`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_connection_state(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_connection_state(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_connection_summary(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_connection_summary(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_device_push_status(`ptr`: Pointer?,`deviceId`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_device_push_status(`ptr`?.inner,`deviceId` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_earliest_id(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_earliest_id(`ptr`?.inner,`channelId`,`channelType`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_friend_pending_requests(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_friend_pending_requests(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_friends(`ptr`: Pointer?,`limit`: Long,`offset`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_friends(`ptr`?.inner,`limit`,`offset`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_by_id(`ptr`: Pointer?,`groupId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_by_id(`ptr`?.inner,`groupId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_info(`ptr`: Pointer?,`groupId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_info(`ptr`?.inner,`groupId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_members(`ptr`: Pointer?,`groupId`: Long,`limit`: Long,`offset`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_members(`ptr`?.inner,`groupId`,`limit`,`offset`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_groups(`ptr`: Pointer?,`limit`: Long,`offset`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_groups(`ptr`?.inner,`limit`,`offset`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_message_by_id(`ptr`: Pointer?,`messageId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_message_by_id(`ptr`?.inner,`messageId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_message_extra(`ptr`: Pointer?,`messageId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_message_extra(`ptr`?.inner,`messageId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_messages(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`limit`: Long,`offset`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_messages(`ptr`?.inner,`channelId`,`channelType`,`limit`,`offset`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_messages_remote(`ptr`: Pointer?,`channelId`: Long,`beforeServerMessageId`: RustBufferByValue,`limit`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_messages_remote(`ptr`?.inner,`channelId`,`beforeServerMessageId` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`limit` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_or_create_direct_channel(`ptr`: Pointer?,`peerUserId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_or_create_direct_channel(`ptr`?.inner,`peerUserId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_presence(`ptr`: Pointer?,`userId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_presence(`ptr`?.inner,`userId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_presence_stats(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_presence_stats(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_privacy_settings(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_privacy_settings(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_profile(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_profile(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_total_unread_count(`ptr`: Pointer?,`excludeMuted`: Byte,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_total_unread_count(`ptr`?.inner,`excludeMuted`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_typing_stats(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_typing_stats(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_unread_mention_count(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`userId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_unread_mention_count(`ptr`?.inner,`channelId`,`channelType`,`userId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_user_by_id(`ptr`: Pointer?,`userId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_user_by_id(`ptr`?.inner,`userId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_user_setting(`ptr`: Pointer?,`key`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_user_setting(`ptr`?.inner,`key` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_add_members_remote(`ptr`: Pointer?,`groupId`: Long,`userIds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_add_members_remote(`ptr`?.inner,`groupId`,`userIds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_approval_handle_remote(`ptr`: Pointer?,`approvalId`: Long,`approved`: Byte,`reason`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_approval_handle_remote(`ptr`?.inner,`approvalId`,`approved`,`reason` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_approval_list_remote(`ptr`: Pointer?,`groupId`: Long,`page`: RustBufferByValue,`pageSize`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_approval_list_remote(`ptr`?.inner,`groupId`,`page` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`pageSize` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_get_settings_remote(`ptr`: Pointer?,`groupId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_get_settings_remote(`ptr`?.inner,`groupId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_leave_remote(`ptr`: Pointer?,`groupId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_leave_remote(`ptr`?.inner,`groupId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_mute_all_remote(`ptr`: Pointer?,`groupId`: Long,`enabled`: Byte,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_mute_all_remote(`ptr`?.inner,`groupId`,`enabled`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_mute_member_remote(`ptr`: Pointer?,`groupId`: Long,`userId`: Long,`durationSeconds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_mute_member_remote(`ptr`?.inner,`groupId`,`userId`,`durationSeconds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_qrcode_generate_remote(`ptr`: Pointer?,`groupId`: Long,`expireSeconds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_qrcode_generate_remote(`ptr`?.inner,`groupId`,`expireSeconds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_qrcode_join_remote(`ptr`: Pointer?,`qrKey`: RustBufferByValue,`token`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_qrcode_join_remote(`ptr`?.inner,`qrKey` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`token` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_remove_member_remote(`ptr`: Pointer?,`groupId`: Long,`userId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_remove_member_remote(`ptr`?.inner,`groupId`,`userId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_set_role_remote(`ptr`: Pointer?,`groupId`: Long,`userId`: Long,`role`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_set_role_remote(`ptr`?.inner,`groupId`,`userId`,`role` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_transfer_owner_remote(`ptr`: Pointer?,`groupId`: Long,`targetUserId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_transfer_owner_remote(`ptr`?.inner,`groupId`,`targetUserId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_unmute_member_remote(`ptr`: Pointer?,`groupId`: Long,`userId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_unmute_member_remote(`ptr`?.inner,`groupId`,`userId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_update_settings_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_update_settings_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_heartbeat_interval(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_heartbeat_interval(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_hide_channel(`ptr`: Pointer?,`channelId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_hide_channel(`ptr`?.inner,`channelId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_http_client_config(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_http_client_config(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_image_send_max_edge(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Int
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_image_send_max_edge(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as Int
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_invite_to_group(`ptr`: Pointer?,`groupId`: Long,`memberIds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_invite_to_group(`ptr`?.inner,`groupId`,`memberIds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_bootstrap_completed(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_bootstrap_completed(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_connected(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_connected(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_event_read_by(`ptr`: Pointer?,`serverMessageId`: Long,`userId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_event_read_by(`ptr`?.inner,`serverMessageId`,`userId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_initialized(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_initialized(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_shutting_down(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_shutting_down(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_supervised_sync_running(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Byte
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_supervised_sync_running(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as Byte
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_join_group_by_qrcode(`ptr`: Pointer?,`qrKey`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_join_group_by_qrcode(`ptr`?.inner,`qrKey` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_kv_get(`ptr`: Pointer?,`key`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_kv_get(`ptr`?.inner,`key` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_kv_put(`ptr`: Pointer?,`key`: RustBufferByValue,`value`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_kv_put(`ptr`?.inner,`key` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`value` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_leave_channel(`ptr`: Pointer?,`channelId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_leave_channel(`ptr`?.inner,`channelId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_leave_group(`ptr`: Pointer?,`groupId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_leave_group(`ptr`?.inner,`groupId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_blacklist_entries(`ptr`: Pointer?,`limit`: Long,`offset`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_blacklist_entries(`ptr`?.inner,`limit`,`offset`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_channel_members(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`limit`: Long,`offset`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_channel_members(`ptr`?.inner,`channelId`,`channelType`,`limit`,`offset`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_channels(`ptr`: Pointer?,`limit`: Long,`offset`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_channels(`ptr`?.inner,`limit`,`offset`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_friends(`ptr`: Pointer?,`limit`: Long,`offset`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_friends(`ptr`?.inner,`limit`,`offset`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_group_members(`ptr`: Pointer?,`groupId`: Long,`limit`: Long,`offset`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_group_members(`ptr`?.inner,`groupId`,`limit`,`offset`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_groups(`ptr`: Pointer?,`limit`: Long,`offset`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_groups(`ptr`?.inner,`limit`,`offset`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_local_accounts(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_local_accounts(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_message_reactions(`ptr`: Pointer?,`messageId`: Long,`limit`: Long,`offset`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_message_reactions(`ptr`?.inner,`messageId`,`limit`,`offset`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_messages(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`limit`: Long,`offset`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_messages(`ptr`?.inner,`channelId`,`channelType`,`limit`,`offset`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_my_devices(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_my_devices(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_pending_reminders(`ptr`: Pointer?,`uid`: Long,`limit`: Long,`offset`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_pending_reminders(`ptr`?.inner,`uid`,`limit`,`offset`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_reactions(`ptr`: Pointer?,`serverMessageId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_reactions(`ptr`?.inner,`serverMessageId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_unread_mention_message_ids(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`userId`: Long,`limit`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_unread_mention_message_ids(`ptr`?.inner,`channelId`,`channelType`,`userId`,`limit`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_users_by_ids(`ptr`: Pointer?,`userIds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_users_by_ids(`ptr`?.inner,`userIds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_log_connection_state(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_log_connection_state(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_login(`ptr`: Pointer?,`username`: RustBufferByValue,`password`: RustBufferByValue,`deviceId`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_login(`ptr`?.inner,`username` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`password` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`deviceId` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_logout(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_logout(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_all_mentions_read(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`userId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_all_mentions_read(`ptr`?.inner,`channelId`,`channelType`,`userId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_as_read(`ptr`: Pointer?,`channelId`: Long,`serverMessageId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_as_read(`ptr`?.inner,`channelId`,`serverMessageId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_as_read_blocking(`ptr`: Pointer?,`channelId`: Long,`serverMessageId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_as_read_blocking(`ptr`?.inner,`channelId`,`serverMessageId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_channel_read(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_channel_read(`ptr`?.inner,`channelId`,`channelType`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_fully_read_at(`ptr`: Pointer?,`channelId`: Long,`serverMessageId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_fully_read_at(`ptr`?.inner,`channelId`,`serverMessageId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_mention_read(`ptr`: Pointer?,`messageId`: Long,`userId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_mention_read(`ptr`?.inner,`messageId`,`userId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_message_sent(`ptr`: Pointer?,`messageId`: Long,`serverMessageId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_message_sent(`ptr`?.inner,`messageId`,`serverMessageId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_reminder_done(`ptr`: Pointer?,`reminderId`: Long,`done`: Byte,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_reminder_done(`ptr`?.inner,`reminderId`,`done`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_read_list(`ptr`: Pointer?,`serverMessageId`: Long,`channelId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_read_list(`ptr`?.inner,`serverMessageId`,`channelId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_read_stats(`ptr`: Pointer?,`serverMessageId`: Long,`channelId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_read_stats(`ptr`?.inner,`serverMessageId`,`channelId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_unread_count_remote(`ptr`: Pointer?,`channelId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_unread_count_remote(`ptr`?.inner,`channelId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_mute_channel(`ptr`: Pointer?,`channelId`: Long,`muted`: Byte,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mute_channel(`ptr`?.inner,`channelId`,`muted`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_needs_sync(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_needs_sync(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_next_event(`ptr`: Pointer?,`timeoutMs`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_next_event(`ptr`?.inner,`timeoutMs`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_next_event_envelope(`ptr`: Pointer?,`timeoutMs`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_next_event_envelope(`ptr`?.inner,`timeoutMs`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_app_background(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_app_background(`ptr`?.inner,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_app_foreground(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_app_foreground(`ptr`?.inner,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_connection_state_changed(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_connection_state_changed(`ptr`?.inner,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_message_received(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_message_received(`ptr`?.inner,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_reaction_changed(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_reaction_changed(`ptr`?.inner,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_typing_indicator(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_typing_indicator(`ptr`?.inner,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_own_last_read(`ptr`: Pointer?,`channelId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_own_last_read(`ptr`?.inner,`channelId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_paginate_back(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`page`: Long,`pageSize`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_paginate_back(`ptr`?.inner,`channelId`,`channelType`,`page`,`pageSize`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_paginate_forward(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`page`: Long,`pageSize`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_paginate_forward(`ptr`?.inner,`channelId`,`channelType`,`page`,`pageSize`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_peek_outbound_files(`ptr`: Pointer?,`queueIndex`: Long,`limit`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_peek_outbound_files(`ptr`?.inner,`queueIndex`,`limit`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_peek_outbound_messages(`ptr`: Pointer?,`limit`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_peek_outbound_messages(`ptr`?.inner,`limit`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_pin_channel(`ptr`: Pointer?,`channelId`: Long,`pinned`: Byte,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_pin_channel(`ptr`?.inner,`channelId`,`pinned`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_ping(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_ping(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_generate(`ptr`: Pointer?,`qrType`: RustBufferByValue,`payload`: RustBufferByValue,`expireSeconds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_generate(`ptr`?.inner,`qrType` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`expireSeconds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_list(`ptr`: Pointer?,`includeRevoked`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_list(`ptr`?.inner,`includeRevoked` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_refresh(`ptr`: Pointer?,`qrType`: RustBufferByValue,`targetId`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_refresh(`ptr`?.inner,`qrType` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`targetId` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_resolve(`ptr`: Pointer?,`qrKey`: RustBufferByValue,`token`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_resolve(`ptr`?.inner,`qrKey` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`token` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_revoke(`ptr`: Pointer?,`qrKey`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_revoke(`ptr`?.inner,`qrKey` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_queue_config(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_queue_config(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_reaction_stats(`ptr`: Pointer?,`serverMessageId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_reaction_stats(`ptr`?.inner,`serverMessageId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_reactions(`ptr`: Pointer?,`serverMessageId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_reactions(`ptr`?.inner,`serverMessageId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_reactions_batch(`ptr`: Pointer?,`serverMessageIds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_reactions_batch(`ptr`?.inner,`serverMessageIds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_recall_message(`ptr`: Pointer?,`serverMessageId`: Long,`channelId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_recall_message(`ptr`?.inner,`serverMessageId`,`channelId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_recall_message_blocking(`ptr`: Pointer?,`serverMessageId`: Long,`channelId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_recall_message_blocking(`ptr`?.inner,`serverMessageId`,`channelId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_recent_events(`ptr`: Pointer?,`limit`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_recent_events(`ptr`?.inner,`limit`,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_record_mention(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_record_mention(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_register(`ptr`: Pointer?,`username`: RustBufferByValue,`password`: RustBufferByValue,`deviceId`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_register(`ptr`?.inner,`username` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`password` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`deviceId` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_register_lifecycle_hook(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_register_lifecycle_hook(`ptr`?.inner,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_reject_friend_request(`ptr`: Pointer?,`fromUserId`: Long,`message`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_reject_friend_request(`ptr`?.inner,`fromUserId`,`message` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_channel_member(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`memberUid`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_channel_member(`ptr`?.inner,`channelId`,`channelType`,`memberUid`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_from_blacklist(`ptr`: Pointer?,`blockedUserId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_from_blacklist(`ptr`?.inner,`blockedUserId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_group_member(`ptr`: Pointer?,`groupId`: Long,`userId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_group_member(`ptr`?.inner,`groupId`,`userId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_reaction(`ptr`: Pointer?,`serverMessageId`: Long,`emoji`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_reaction(`ptr`?.inner,`serverMessageId`,`emoji` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_require_current_user_id(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_require_current_user_id(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_channel_id_by_server_message_id(`ptr`: Pointer?,`serverMessageId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_channel_id_by_server_message_id(`ptr`?.inner,`serverMessageId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_channel_type(`ptr`: Pointer?,`channelId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_channel_type(`ptr`?.inner,`channelId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_local_message_id_by_server_message_id(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`serverMessageId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_local_message_id_by_server_message_id(`ptr`?.inner,`channelId`,`channelType`,`serverMessageId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_retry_config(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_retry_config(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_retry_message(`ptr`: Pointer?,`messageId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_retry_message(`ptr`?.inner,`messageId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_rpc_call(`ptr`: Pointer?,`route`: RustBufferByValue,`bodyJson`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_rpc_call(`ptr`?.inner,`route` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`bodyJson` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_run_bootstrap_sync(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_run_bootstrap_sync(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_channel(`ptr`: Pointer?,`keyword`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_channel(`ptr`?.inner,`keyword` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_messages(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`keyword`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_messages(`ptr`?.inner,`channelId`,`channelType`,`keyword` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_user_by_qrcode(`ptr`: Pointer?,`qrKey`: RustBufferByValue,`token`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_user_by_qrcode(`ptr`?.inner,`qrKey` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`token` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_users(`ptr`: Pointer?,`query`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_users(`ptr`?.inner,`query` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_seen_by_for_event(`ptr`: Pointer?,`serverMessageId`: Long,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_seen_by_for_event(`ptr`?.inner,`serverMessageId`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_attachment_bytes(`ptr`: Pointer?,`messageId`: Long,`routeKey`: RustBufferByValue,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_attachment_bytes(`ptr`?.inner,`messageId`,`routeKey` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_attachment_from_path(`ptr`: Pointer?,`messageId`: Long,`routeKey`: RustBufferByValue,`path`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_attachment_from_path(`ptr`?.inner,`messageId`,`routeKey` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`path` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_friend_request(`ptr`: Pointer?,`targetUserId`: Long,`message`: RustBufferByValue,`source`: RustBufferByValue,`sourceId`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_friend_request(`ptr`?.inner,`targetUserId`,`message` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`source` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`sourceId` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_local_message_now(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_local_message_now(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`fromUid`: Long,`content`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message(`ptr`?.inner,`channelId`,`channelType`,`fromUid`,`content` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_blocking(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`fromUid`: Long,`content`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_blocking(`ptr`?.inner,`channelId`,`channelType`,`fromUid`,`content` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_with_input(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_with_input(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_with_options(`ptr`: Pointer?,`input`: RustBufferByValue,`options`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_with_options(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`options` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_queue_set_enabled(`ptr`: Pointer?,`enabled`: Byte,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_queue_set_enabled(`ptr`?.inner,`enabled`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_typing(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`isTyping`: Byte,`actionType`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_typing(`ptr`?.inner,`channelId`,`channelType`,`isTyping`,`actionType` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_server_config(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_server_config(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_servers(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_servers(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_session_snapshot(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_session_snapshot(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_favourite(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`enabled`: Byte,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_favourite(`ptr`?.inner,`channelId`,`channelType`,`enabled`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_low_priority(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`enabled`: Byte,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_low_priority(`ptr`?.inner,`channelId`,`channelType`,`enabled`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_notification_mode(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,`mode`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_notification_mode(`ptr`?.inner,`channelId`,`channelType`,`mode`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_current_uid(`ptr`: Pointer?,`uid`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_current_uid(`ptr`?.inner,`uid` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_pinned(`ptr`: Pointer?,`messageId`: Long,`isPinned`: Byte,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_pinned(`ptr`?.inner,`messageId`,`isPinned`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_read(`ptr`: Pointer?,`messageId`: Long,`channelId`: Long,`channelType`: Int,`isRead`: Byte,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_read(`ptr`?.inner,`messageId`,`channelId`,`channelType`,`isRead`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_revoke(`ptr`: Pointer?,`messageId`: Long,`revoked`: Byte,`revoker`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_revoke(`ptr`?.inner,`messageId`,`revoked`,`revoker` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_network_hint(`ptr`: Pointer?,`hint`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_network_hint(`ptr`?.inner,`hint` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_user_setting(`ptr`: Pointer?,`key`: RustBufferByValue,`value`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_user_setting(`ptr`?.inner,`key` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`value` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_video_process_hook(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_video_process_hook(`ptr`?.inner,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_shutdown(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_shutdown(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_shutdown_blocking(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_shutdown_blocking(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_supervised_sync(`ptr`: Pointer?,`intervalSecs`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_supervised_sync(`ptr`?.inner,`intervalSecs`,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_transport_disconnect_listener(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_transport_disconnect_listener(`ptr`?.inner,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_typing(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_typing(`ptr`?.inner,`channelId`,`channelType`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_typing_blocking(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_typing_blocking(`ptr`?.inner,`channelId`,`channelType`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sticker_package_detail_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sticker_package_detail_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sticker_package_list_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sticker_package_list_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_stop_supervised_sync(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_stop_supervised_sync(`ptr`?.inner,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_stop_typing(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_stop_typing(`ptr`?.inner,`channelId`,`channelType`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_storage(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_storage(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_events(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Byte
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_events(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as Byte
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_network_status(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Byte
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_network_status(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as Byte
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_presence(`ptr`: Pointer?,`userIds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_presence(`ptr`?.inner,`userIds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_all_channels(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_all_channels(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_batch_get_channel_pts_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_batch_get_channel_pts_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_channel(`ptr`: Pointer?,`channelId`: Long,`channelType`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_channel(`ptr`?.inner,`channelId`,`channelType`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_entities(`ptr`: Pointer?,`entityType`: RustBufferByValue,`scope`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_entities(`ptr`?.inner,`entityType` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`scope` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_entities_in_background(`ptr`: Pointer?,`entityType`: RustBufferByValue,`scope`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_entities_in_background(`ptr`?.inner,`entityType` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`scope` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_get_channel_pts_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_get_channel_pts_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_get_difference_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_get_difference_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_messages(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_messages(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_messages_in_background(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_messages_in_background(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_submit_remote(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_submit_remote(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_hours(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Int
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_hours(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as Int
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_local(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_local(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_minutes(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Int
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_minutes(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as Int
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_seconds(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): Int
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_seconds(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as Int
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_to_client_endpoint(`ptr`: Pointer?,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_to_client_endpoint(`ptr`?.inner,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_unsubscribe_presence(`ptr`: Pointer?,`userIds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_unsubscribe_presence(`ptr`?.inner,`userIds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_device_push_state(`ptr`: Pointer?,`deviceId`: RustBufferByValue,`apnsArmed`: Byte,`pushToken`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_device_push_state(`ptr`?.inner,`deviceId` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`apnsArmed`,`pushToken` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_message_status(`ptr`: Pointer?,`messageId`: Long,`status`: Int,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_message_status(`ptr`?.inner,`messageId`,`status`,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_privacy_settings(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_privacy_settings(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_profile(`ptr`: Pointer?,`payload`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_profile(`ptr`?.inner,`payload` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_blacklist_entry(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_blacklist_entry(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel_extra(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel_extra(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel_member(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel_member(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_friend(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_friend(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_group(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_group(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_group_member(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_group_member(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_message_reaction(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_message_reaction(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_reminder(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_reminder(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_user(`ptr`: Pointer?,`input`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_user(`ptr`?.inner,`input` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_id(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_id(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_generate(`ptr`: Pointer?,`expireSeconds`: RustBufferByValue,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_generate(`ptr`?.inner,`expireSeconds` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_get(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_get(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_refresh(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_refresh(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_storage_paths(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_storage_paths(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_method_privchatclient_wipe_current_user_full(`ptr`: Pointer?,
    ): Long
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_method_privchatclient_wipe_current_user_full(`ptr`?.inner,)as Long
    
    override fun uniffi_privchat_sdk_ffi_fn_func_build_time(uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_func_build_time(uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_func_git_sha(uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_func_git_sha(uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun uniffi_privchat_sdk_ffi_fn_func_sdk_version(uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_fn_func_sdk_version(uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun ffi_privchat_sdk_ffi_rustbuffer_alloc(`size`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rustbuffer_alloc(`size`,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun ffi_privchat_sdk_ffi_rustbuffer_from_bytes(`bytes`: ForeignBytesByValue,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rustbuffer_from_bytes(`bytes` as CValue<privchat_sdk_ffi.cinterop.ForeignBytes>,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun ffi_privchat_sdk_ffi_rustbuffer_free(`buf`: RustBufferByValue,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rustbuffer_free(`buf` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,uniffiCallStatus.reinterpret(), )
    
    override fun ffi_privchat_sdk_ffi_rustbuffer_reserve(`buf`: RustBufferByValue,`additional`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rustbuffer_reserve(`buf` as CValue<privchat_sdk_ffi.cinterop.RustBuffer>,`additional`,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun ffi_privchat_sdk_ffi_rust_future_poll_u8(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_poll_u8(`handle`,`callback` as privchat_sdk_ffi.cinterop.UniffiRustFutureContinuationCallback?,`callbackData`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_cancel_u8(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_cancel_u8(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_free_u8(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_free_u8(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_complete_u8(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Byte
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_complete_u8(`handle`,uniffiCallStatus.reinterpret(), )as Byte
    
    override fun ffi_privchat_sdk_ffi_rust_future_poll_i8(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_poll_i8(`handle`,`callback` as privchat_sdk_ffi.cinterop.UniffiRustFutureContinuationCallback?,`callbackData`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_cancel_i8(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_cancel_i8(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_free_i8(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_free_i8(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_complete_i8(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Byte
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_complete_i8(`handle`,uniffiCallStatus.reinterpret(), )as Byte
    
    override fun ffi_privchat_sdk_ffi_rust_future_poll_u16(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_poll_u16(`handle`,`callback` as privchat_sdk_ffi.cinterop.UniffiRustFutureContinuationCallback?,`callbackData`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_cancel_u16(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_cancel_u16(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_free_u16(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_free_u16(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_complete_u16(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Short
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_complete_u16(`handle`,uniffiCallStatus.reinterpret(), )as Short
    
    override fun ffi_privchat_sdk_ffi_rust_future_poll_i16(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_poll_i16(`handle`,`callback` as privchat_sdk_ffi.cinterop.UniffiRustFutureContinuationCallback?,`callbackData`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_cancel_i16(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_cancel_i16(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_free_i16(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_free_i16(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_complete_i16(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Short
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_complete_i16(`handle`,uniffiCallStatus.reinterpret(), )as Short
    
    override fun ffi_privchat_sdk_ffi_rust_future_poll_u32(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_poll_u32(`handle`,`callback` as privchat_sdk_ffi.cinterop.UniffiRustFutureContinuationCallback?,`callbackData`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_cancel_u32(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_cancel_u32(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_free_u32(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_free_u32(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_complete_u32(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Int
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_complete_u32(`handle`,uniffiCallStatus.reinterpret(), )as Int
    
    override fun ffi_privchat_sdk_ffi_rust_future_poll_i32(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_poll_i32(`handle`,`callback` as privchat_sdk_ffi.cinterop.UniffiRustFutureContinuationCallback?,`callbackData`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_cancel_i32(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_cancel_i32(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_free_i32(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_free_i32(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_complete_i32(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Int
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_complete_i32(`handle`,uniffiCallStatus.reinterpret(), )as Int
    
    override fun ffi_privchat_sdk_ffi_rust_future_poll_u64(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_poll_u64(`handle`,`callback` as privchat_sdk_ffi.cinterop.UniffiRustFutureContinuationCallback?,`callbackData`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_cancel_u64(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_cancel_u64(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_free_u64(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_free_u64(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_complete_u64(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Long
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_complete_u64(`handle`,uniffiCallStatus.reinterpret(), )as Long
    
    override fun ffi_privchat_sdk_ffi_rust_future_poll_i64(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_poll_i64(`handle`,`callback` as privchat_sdk_ffi.cinterop.UniffiRustFutureContinuationCallback?,`callbackData`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_cancel_i64(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_cancel_i64(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_free_i64(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_free_i64(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_complete_i64(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Long
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_complete_i64(`handle`,uniffiCallStatus.reinterpret(), )as Long
    
    override fun ffi_privchat_sdk_ffi_rust_future_poll_f32(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_poll_f32(`handle`,`callback` as privchat_sdk_ffi.cinterop.UniffiRustFutureContinuationCallback?,`callbackData`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_cancel_f32(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_cancel_f32(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_free_f32(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_free_f32(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_complete_f32(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Float
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_complete_f32(`handle`,uniffiCallStatus.reinterpret(), )as Float
    
    override fun ffi_privchat_sdk_ffi_rust_future_poll_f64(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_poll_f64(`handle`,`callback` as privchat_sdk_ffi.cinterop.UniffiRustFutureContinuationCallback?,`callbackData`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_cancel_f64(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_cancel_f64(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_free_f64(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_free_f64(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_complete_f64(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Double
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_complete_f64(`handle`,uniffiCallStatus.reinterpret(), )as Double
    
    override fun ffi_privchat_sdk_ffi_rust_future_poll_pointer(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_poll_pointer(`handle`,`callback` as privchat_sdk_ffi.cinterop.UniffiRustFutureContinuationCallback?,`callbackData`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_cancel_pointer(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_cancel_pointer(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_free_pointer(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_free_pointer(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_complete_pointer(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Pointer?
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_complete_pointer(`handle`,uniffiCallStatus.reinterpret(), )?.let { Pointer(it) }as Pointer?
    
    override fun ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(`handle`,`callback` as privchat_sdk_ffi.cinterop.UniffiRustFutureContinuationCallback?,`callbackData`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): RustBufferByValue
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(`handle`,uniffiCallStatus.reinterpret(), )as RustBufferByValue
    
    override fun ffi_privchat_sdk_ffi_rust_future_poll_void(`handle`: Long,`callback`: Any,`callbackData`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_poll_void(`handle`,`callback` as privchat_sdk_ffi.cinterop.UniffiRustFutureContinuationCallback?,`callbackData`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_cancel_void(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_cancel_void(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_free_void(`handle`: Long,
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_free_void(`handle`,)
    
    override fun ffi_privchat_sdk_ffi_rust_future_complete_void(`handle`: Long,uniffiCallStatus: UniffiRustCallStatus, 
    ): Unit
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_rust_future_complete_void(`handle`,uniffiCallStatus.reinterpret(), )
    
    override fun uniffi_privchat_sdk_ffi_checksum_func_build_time(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_func_build_time()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_func_git_sha(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_func_git_sha()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_func_sdk_version(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_func_sdk_version()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_accept_friend_request(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_accept_friend_request()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_account_user_detail_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_account_user_detail_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_account_user_share_card_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_account_user_share_card_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_account_user_update_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_account_user_update_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_ack_outbound_files(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_ack_outbound_files()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_ack_outbound_messages(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_ack_outbound_messages()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_channel_members(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_channel_members()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_reaction(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_reaction()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_reaction_blocking(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_reaction_blocking()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_server(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_server()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_to_blacklist(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_to_blacklist()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_assets_dir(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_assets_dir()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_auth_logout_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_auth_logout_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_auth_refresh_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_auth_refresh_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_authenticate(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_authenticate()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_batch_get_presence(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_batch_get_presence()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_build(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_build()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_builder(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_builder()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_broadcast_create_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_broadcast_create_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_broadcast_list_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_broadcast_list_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_broadcast_subscribe_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_broadcast_subscribe_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_content_list_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_content_list_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_content_publish_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_content_publish_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_notification_mode(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_notification_mode()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_send_queue_set_enabled(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_send_queue_set_enabled()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_tags(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_tags()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_unread_stats(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_unread_stats()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_check_blacklist(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_check_blacklist()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_check_friend(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_check_friend()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_clear_local_state(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_clear_local_state()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_clear_presence_cache(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_clear_presence_cache()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_config(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_config()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connect(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connect()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connect_blocking(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connect_blocking()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connection_state(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connection_state()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connection_timeout(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connection_timeout()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_create_group(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_create_group()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_create_local_message(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_create_local_message()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_data_dir(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_data_dir()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_debug_mode(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_debug_mode()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_delete_blacklist_entry(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_delete_blacklist_entry()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_delete_channel_member(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_delete_channel_member()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_delete_friend(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_delete_friend()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_disconnect(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_disconnect()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_dm_peer_user_id(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_dm_peer_user_id()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_download_attachment_to_cache(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_download_attachment_to_cache()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_download_attachment_to_message_dir(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_download_attachment_to_message_dir()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_download_attachment_to_path(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_download_attachment_to_path()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_edit_message(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_edit_message()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_edit_message_blocking(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_edit_message_blocking()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enqueue_outbound_file(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enqueue_outbound_file()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enqueue_outbound_message(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enqueue_outbound_message()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enqueue_text(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enqueue_text()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enter_background(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enter_background()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enter_foreground(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enter_foreground()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_entity_sync_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_entity_sync_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_event_config(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_event_config()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_event_stream_cursor(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_event_stream_cursor()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_events_since(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_events_since()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_fetch_group_members_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_fetch_group_members_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_fetch_presence(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_fetch_presence()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_file_api_base_url(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_file_api_base_url()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_file_request_upload_token_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_file_request_upload_token_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_file_upload_callback_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_file_upload_callback_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_all_unread_mention_counts(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_all_unread_mention_counts()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_all_user_settings(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_all_user_settings()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_blacklist(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_blacklist()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_by_id(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_by_id()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_extra(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_extra()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_list_entries(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_list_entries()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_sync_state(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_sync_state()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_unread_count(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_unread_count()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channels(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channels()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_connection_state(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_connection_state()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_connection_summary(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_connection_summary()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_device_push_status(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_device_push_status()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_earliest_id(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_earliest_id()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_friend_pending_requests(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_friend_pending_requests()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_friends(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_friends()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_group_by_id(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_group_by_id()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_group_info(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_group_info()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_group_members(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_group_members()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_groups(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_groups()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_message_by_id(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_message_by_id()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_message_extra(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_message_extra()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_messages(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_messages()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_messages_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_messages_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_or_create_direct_channel(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_or_create_direct_channel()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_presence(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_presence()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_presence_stats(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_presence_stats()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_privacy_settings(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_privacy_settings()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_profile(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_profile()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_total_unread_count(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_total_unread_count()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_typing_stats(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_typing_stats()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_unread_mention_count(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_unread_mention_count()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_user_by_id(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_user_by_id()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_user_setting(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_user_setting()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_add_members_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_add_members_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_approval_handle_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_approval_handle_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_approval_list_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_approval_list_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_get_settings_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_get_settings_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_leave_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_leave_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_mute_all_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_mute_all_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_mute_member_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_mute_member_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_qrcode_generate_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_qrcode_generate_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_qrcode_join_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_qrcode_join_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_remove_member_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_remove_member_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_set_role_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_set_role_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_transfer_owner_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_transfer_owner_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_unmute_member_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_unmute_member_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_update_settings_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_update_settings_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_heartbeat_interval(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_heartbeat_interval()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_hide_channel(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_hide_channel()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_http_client_config(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_http_client_config()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_image_send_max_edge(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_image_send_max_edge()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_invite_to_group(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_invite_to_group()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_bootstrap_completed(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_bootstrap_completed()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_connected(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_connected()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_event_read_by(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_event_read_by()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_initialized(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_initialized()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_shutting_down(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_shutting_down()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_supervised_sync_running(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_supervised_sync_running()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_join_group_by_qrcode(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_join_group_by_qrcode()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_kv_get(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_kv_get()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_kv_put(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_kv_put()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_leave_channel(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_leave_channel()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_leave_group(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_leave_group()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_blacklist_entries(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_blacklist_entries()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_channel_members(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_channel_members()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_channels(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_channels()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_friends(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_friends()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_group_members(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_group_members()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_groups(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_groups()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_local_accounts(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_local_accounts()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_message_reactions(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_message_reactions()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_messages(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_messages()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_my_devices(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_my_devices()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_pending_reminders(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_pending_reminders()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_reactions(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_reactions()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_unread_mention_message_ids(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_unread_mention_message_ids()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_users_by_ids(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_users_by_ids()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_log_connection_state(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_log_connection_state()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_login(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_login()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_logout(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_logout()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_all_mentions_read(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_all_mentions_read()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_as_read(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_as_read()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_as_read_blocking(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_as_read_blocking()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_channel_read(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_channel_read()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_fully_read_at(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_fully_read_at()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_mention_read(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_mention_read()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_message_sent(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_message_sent()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_reminder_done(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_reminder_done()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_message_read_list(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_message_read_list()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_message_read_stats(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_message_read_stats()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_message_unread_count_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_message_unread_count_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mute_channel(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mute_channel()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_needs_sync(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_needs_sync()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_next_event(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_next_event()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_next_event_envelope(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_next_event_envelope()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_app_background(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_app_background()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_app_foreground(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_app_foreground()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_connection_state_changed(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_connection_state_changed()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_message_received(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_message_received()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_reaction_changed(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_reaction_changed()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_typing_indicator(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_typing_indicator()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_own_last_read(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_own_last_read()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_paginate_back(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_paginate_back()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_paginate_forward(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_paginate_forward()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_peek_outbound_files(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_peek_outbound_files()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_peek_outbound_messages(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_peek_outbound_messages()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_pin_channel(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_pin_channel()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_ping(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_ping()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_generate(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_generate()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_list(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_list()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_refresh(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_refresh()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_resolve(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_resolve()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_revoke(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_revoke()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_queue_config(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_queue_config()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reaction_stats(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reaction_stats()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reactions(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reactions()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reactions_batch(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reactions_batch()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_recall_message(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_recall_message()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_recall_message_blocking(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_recall_message_blocking()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_recent_events(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_recent_events()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_record_mention(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_record_mention()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_register(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_register()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_register_lifecycle_hook(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_register_lifecycle_hook()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reject_friend_request(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reject_friend_request()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_channel_member(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_channel_member()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_from_blacklist(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_from_blacklist()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_group_member(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_group_member()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_reaction(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_reaction()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_require_current_user_id(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_require_current_user_id()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_resolve_channel_id_by_server_message_id(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_resolve_channel_id_by_server_message_id()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_resolve_channel_type(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_resolve_channel_type()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_resolve_local_message_id_by_server_message_id(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_resolve_local_message_id_by_server_message_id()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_retry_config(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_retry_config()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_retry_message(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_retry_message()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_rpc_call(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_rpc_call()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_run_bootstrap_sync(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_run_bootstrap_sync()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_channel(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_channel()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_messages(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_messages()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_user_by_qrcode(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_user_by_qrcode()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_users(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_users()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_seen_by_for_event(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_seen_by_for_event()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_attachment_bytes(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_attachment_bytes()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_attachment_from_path(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_attachment_from_path()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_friend_request(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_friend_request()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_local_message_now(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_local_message_now()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message_blocking(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message_blocking()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message_with_input(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message_with_input()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message_with_options(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message_with_options()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_queue_set_enabled(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_queue_set_enabled()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_typing(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_typing()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_server_config(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_server_config()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_servers(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_servers()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_session_snapshot(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_session_snapshot()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_channel_favourite(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_channel_favourite()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_channel_low_priority(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_channel_low_priority()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_channel_notification_mode(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_channel_notification_mode()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_current_uid(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_current_uid()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_message_pinned(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_message_pinned()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_message_read(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_message_read()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_message_revoke(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_message_revoke()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_network_hint(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_network_hint()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_user_setting(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_user_setting()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_video_process_hook(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_video_process_hook()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_shutdown(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_shutdown()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_shutdown_blocking(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_shutdown_blocking()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_supervised_sync(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_supervised_sync()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_transport_disconnect_listener(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_transport_disconnect_listener()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_typing(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_typing()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_typing_blocking(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_typing_blocking()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sticker_package_detail_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sticker_package_detail_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sticker_package_list_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sticker_package_list_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_stop_supervised_sync(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_stop_supervised_sync()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_stop_typing(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_stop_typing()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_storage(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_storage()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_subscribe_events(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_subscribe_events()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_subscribe_network_status(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_subscribe_network_status()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_subscribe_presence(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_subscribe_presence()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_all_channels(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_all_channels()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_batch_get_channel_pts_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_batch_get_channel_pts_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_channel(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_channel()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_entities(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_entities()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_entities_in_background(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_entities_in_background()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_get_channel_pts_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_get_channel_pts_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_get_difference_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_get_difference_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_messages(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_messages()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_messages_in_background(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_messages_in_background()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_submit_remote(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_submit_remote()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_hours(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_hours()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_local(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_local()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_minutes(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_minutes()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_seconds(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_seconds()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_to_client_endpoint(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_to_client_endpoint()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_unsubscribe_presence(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_unsubscribe_presence()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_device_push_state(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_device_push_state()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_message_status(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_message_status()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_privacy_settings(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_privacy_settings()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_profile(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_profile()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_blacklist_entry(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_blacklist_entry()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_channel(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_channel()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_channel_extra(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_channel_extra()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_channel_member(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_channel_member()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_friend(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_friend()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_group(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_group()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_group_member(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_group_member()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_message_reaction(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_message_reaction()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_reminder(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_reminder()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_user(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_user()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_id(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_id()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_qrcode_generate(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_qrcode_generate()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_qrcode_get(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_qrcode_get()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_qrcode_refresh(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_qrcode_refresh()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_storage_paths(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_storage_paths()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_method_privchatclient_wipe_current_user_full(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_method_privchatclient_wipe_current_user_full()as Short
    
    override fun uniffi_privchat_sdk_ffi_checksum_constructor_privchatclient_new(
    ): Short
        = privchat_sdk_ffi.cinterop.uniffi_privchat_sdk_ffi_checksum_constructor_privchatclient_new()as Short
    
    override fun ffi_privchat_sdk_ffi_uniffi_contract_version(
    ): Int
        = privchat_sdk_ffi.cinterop.ffi_privchat_sdk_ffi_uniffi_contract_version()as Int
    
    
}

// Public interface members begin here.



public object FfiConverterUByte: FfiConverter<UByte, Byte> {
    override fun lift(value: Byte): UByte {
        return value.toUByte()
    }

    override fun read(buf: ByteBuffer): UByte {
        return lift(buf.get())
    }

    override fun lower(value: UByte): Byte {
        return value.toByte()
    }

    override fun allocationSize(value: UByte) = 1UL

    override fun write(value: UByte, buf: ByteBuffer) {
        buf.put(value.toByte())
    }
}


public object FfiConverterUShort: FfiConverter<UShort, Short> {
    override fun lift(value: Short): UShort {
        return value.toUShort()
    }

    override fun read(buf: ByteBuffer): UShort {
        return lift(buf.getShort())
    }

    override fun lower(value: UShort): Short {
        return value.toShort()
    }

    override fun allocationSize(value: UShort) = 2UL

    override fun write(value: UShort, buf: ByteBuffer) {
        buf.putShort(value.toShort())
    }
}


public object FfiConverterShort: FfiConverter<Short, Short> {
    override fun lift(value: Short): Short {
        return value
    }

    override fun read(buf: ByteBuffer): Short {
        return buf.getShort()
    }

    override fun lower(value: Short): Short {
        return value
    }

    override fun allocationSize(value: Short) = 2UL

    override fun write(value: Short, buf: ByteBuffer) {
        buf.putShort(value)
    }
}


public object FfiConverterUInt: FfiConverter<UInt, Int> {
    override fun lift(value: Int): UInt {
        return value.toUInt()
    }

    override fun read(buf: ByteBuffer): UInt {
        return lift(buf.getInt())
    }

    override fun lower(value: UInt): Int {
        return value.toInt()
    }

    override fun allocationSize(value: UInt) = 4UL

    override fun write(value: UInt, buf: ByteBuffer) {
        buf.putInt(value.toInt())
    }
}


public object FfiConverterInt: FfiConverter<Int, Int> {
    override fun lift(value: Int): Int {
        return value
    }

    override fun read(buf: ByteBuffer): Int {
        return buf.getInt()
    }

    override fun lower(value: Int): Int {
        return value
    }

    override fun allocationSize(value: Int) = 4UL

    override fun write(value: Int, buf: ByteBuffer) {
        buf.putInt(value)
    }
}


public object FfiConverterULong: FfiConverter<ULong, Long> {
    override fun lift(value: Long): ULong {
        return value.toULong()
    }

    override fun read(buf: ByteBuffer): ULong {
        return lift(buf.getLong())
    }

    override fun lower(value: ULong): Long {
        return value.toLong()
    }

    override fun allocationSize(value: ULong) = 8UL

    override fun write(value: ULong, buf: ByteBuffer) {
        buf.putLong(value.toLong())
    }
}


public object FfiConverterLong: FfiConverter<Long, Long> {
    override fun lift(value: Long): Long {
        return value
    }

    override fun read(buf: ByteBuffer): Long {
        return buf.getLong()
    }

    override fun lower(value: Long): Long {
        return value
    }

    override fun allocationSize(value: Long) = 8UL

    override fun write(value: Long, buf: ByteBuffer) {
        buf.putLong(value)
    }
}


public object FfiConverterBoolean: FfiConverter<Boolean, Byte> {
    override fun lift(value: Byte): Boolean {
        return value.toInt() != 0
    }

    override fun read(buf: ByteBuffer): Boolean {
        return lift(buf.get())
    }

    override fun lower(value: Boolean): Byte {
        return if (value) 1.toByte() else 0.toByte()
    }

    override fun allocationSize(value: Boolean) = 1UL

    override fun write(value: Boolean, buf: ByteBuffer) {
        buf.put(lower(value))
    }
}




public object FfiConverterString: FfiConverter<String, RustBufferByValue> {
    // Note: we don't inherit from FfiConverterRustBuffer, because we use a
    // special encoding when lowering/lifting.  We can use `RustBuffer.len` to
    // store our length and avoid writing it out to the buffer.
    override fun lift(value: RustBufferByValue): String {
        try {
            require(value.len <= Int.MAX_VALUE) {
        val length = value.len
        "cannot handle RustBuffer longer than Int.MAX_VALUE bytes: length is $length"
    }
            val byteArr =  value.asByteBuffer()!!.get(value.len.toInt())
            return byteArr.decodeToString()
        } finally {
            RustBufferHelper.free(value)
        }
    }

    override fun read(buf: ByteBuffer): String {
        val len = buf.getInt()
        val byteArr = buf.get(len)
        return byteArr.decodeToString()
    }

    override fun lower(value: String): RustBufferByValue {
        return RustBufferHelper.allocValue(value.utf8Size().toULong()).apply { 
            asByteBuffer()!!.writeUtf8(value)
        }
    }

    // We aren't sure exactly how many bytes our string will be once it's UTF-8
    // encoded.  Allocate 3 bytes per UTF-16 code unit which will always be
    // enough.
    override fun allocationSize(value: String): ULong {
        val sizeForLength = 4UL
        val sizeForString = value.length.toULong() * 3UL
        return sizeForLength + sizeForString
    }

    override fun write(value: String, buf: ByteBuffer) {
        buf.putInt(value.utf8Size().toInt())
        buf.writeUtf8(value)
    }
}


public object FfiConverterByteArray: FfiConverterRustBuffer<ByteArray> {
    override fun read(buf: ByteBuffer): ByteArray {
        val len = buf.getInt()
        val byteArr = buf.get(len)
        return byteArr
    }
    override fun allocationSize(value: ByteArray): ULong {
        return 4UL + value.size.toULong()
    }
    override fun write(value: ByteArray, buf: ByteBuffer) {
        buf.putInt(value.size)
        buf.put(value)
    }
}



actual open class PrivchatClient: Disposable, PrivchatClientInterface {

    actual constructor(pointer: Pointer) {
        this.pointer = pointer
        this.cleanable = UniffiLib.CLEANER.register(this, UniffiCleanAction(pointer))
    }

    /**
     * This constructor can be used to instantiate a fake object. Only used for tests. Any
     * attempt to actually use an object constructed this way will fail as there is no
     * connected Rust object.
     */
    @Suppress("UNUSED_PARAMETER")
    actual constructor(noPointer: NoPointer) {
        this.pointer = null
        this.cleanable = UniffiLib.CLEANER.register(this, UniffiCleanAction(pointer))
    }
    actual constructor(`config`: PrivchatConfig) :
        this(
    uniffiRustCallWithError(PrivchatFfiExceptionErrorHandler) { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_constructor_privchatclient_new(
        FfiConverterTypePrivchatConfig.lower(`config`),_status)!!
}
    )

    protected val pointer: Pointer?
    protected val cleanable: UniffiCleaner.Cleanable

    private val wasDestroyed: kotlinx.atomicfu.AtomicBoolean = kotlinx.atomicfu.atomic(false)
    private val callCounter: kotlinx.atomicfu.AtomicLong = kotlinx.atomicfu.atomic(1L)

    private val lock = kotlinx.atomicfu.locks.ReentrantLock()

    private fun <T> synchronized(block: () -> T): T {
        lock.lock()
        try {
            return block()
        } finally {
            lock.unlock()
        }
    }

    actual override fun destroy() {
        // Only allow a single call to this method.
        // TODO: maybe we should log a warning if called more than once?
        if (this.wasDestroyed.compareAndSet(false, true)) {
            // This decrement always matches the initial count of 1 given at creation time.
            if (this.callCounter.decrementAndGet() == 0L) {
                cleanable.clean()
            }
        }
    }

    actual override fun close() {
        synchronized { this.destroy() }
    }

    internal actual inline fun <R> callWithPointer(block: (ptr: Pointer) -> R): R {
        // Check and increment the call counter, to keep the object alive.
        // This needs a compare-and-set retry loop in case of concurrent updates.
        do {
            val c = this.callCounter.value
            if (c == 0L) {
                throw IllegalStateException("${this::class::simpleName} object has already been destroyed")
            }
            if (c == Long.MAX_VALUE) {
                throw IllegalStateException("${this::class::simpleName} call counter would overflow")
            }
        } while (! this.callCounter.compareAndSet(c, c + 1L))
        // Now we can safely do the method call without the pointer being freed concurrently.
        try {
            return block(this.uniffiClonePointer())
        } finally {
            // This decrement always matches the increment we performed above.
            if (this.callCounter.decrementAndGet() == 0L) {
                cleanable.clean()
            }
        }
    }

    // Use a static inner class instead of a closure so as not to accidentally
    // capture `this` as part of the cleanable's action.
    private class UniffiCleanAction(private val pointer: Pointer?) : Runnable {
        override fun run() {
            pointer?.let { ptr ->
                uniffiRustCall { status ->
                    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_free_privchatclient(ptr, status)!!
                }
            }
        }
    }

    actual fun uniffiClonePointer(): Pointer {
        return uniffiRustCall() { status ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_clone_privchatclient(pointer!!, status)!!
        }
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `acceptFriendRequest`(`fromUserId`: kotlin.ULong, `message`: kotlin.String?) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_accept_friend_request(
                thisPtr,
                FfiConverterULong.lower(`fromUserId`),FfiConverterOptionalString.lower(`message`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `accountUserDetailRemote`(`userId`: kotlin.ULong) : AccountUserDetailView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_detail_remote(
                thisPtr,
                FfiConverterULong.lower(`userId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeAccountUserDetailView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `accountUserShareCardRemote`(`userId`: kotlin.ULong) : AccountUserShareCardView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_share_card_remote(
                thisPtr,
                FfiConverterULong.lower(`userId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeAccountUserShareCardView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `accountUserUpdateRemote`(`payload`: AccountUserUpdateInput) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_update_remote(
                thisPtr,
                FfiConverterTypeAccountUserUpdateInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `ackOutboundFiles`(`queueIndex`: kotlin.ULong, `messageIds`: List<kotlin.ULong>) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_ack_outbound_files(
                thisPtr,
                FfiConverterULong.lower(`queueIndex`),FfiConverterSequenceULong.lower(`messageIds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `ackOutboundMessages`(`messageIds`: List<kotlin.ULong>) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_ack_outbound_messages(
                thisPtr,
                FfiConverterSequenceULong.lower(`messageIds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `addChannelMembers`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `memberUids`: List<kotlin.ULong>) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_channel_members(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterSequenceULong.lower(`memberUids`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `addReaction`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong?, `emoji`: kotlin.String) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_reaction(
                thisPtr,
                FfiConverterULong.lower(`serverMessageId`),FfiConverterOptionalULong.lower(`channelId`),FfiConverterString.lower(`emoji`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `addReactionBlocking`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong?, `emoji`: kotlin.String) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_reaction_blocking(
                thisPtr,
                FfiConverterULong.lower(`serverMessageId`),FfiConverterOptionalULong.lower(`channelId`),FfiConverterString.lower(`emoji`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class)actual override fun `addServer`(`endpoint`: ServerEndpoint)
        = 
    callWithPointer {
    uniffiRustCallWithError(PrivchatFfiExceptionErrorHandler) { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_server(
        it, FfiConverterTypeServerEndpoint.lower(`endpoint`),_status)!!
}
    }
    
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `addToBlacklist`(`blockedUserId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_to_blacklist(
                thisPtr,
                FfiConverterULong.lower(`blockedUserId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `assetsDir`() : kotlin.String {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_assets_dir(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterString.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `authLogoutRemote`() : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_auth_logout_remote(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `authRefreshRemote`(`payload`: AuthRefreshInput) : LoginResult {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_auth_refresh_remote(
                thisPtr,
                FfiConverterTypeAuthRefreshInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeLoginResult.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `authenticate`(`userId`: kotlin.ULong, `token`: kotlin.String, `deviceId`: kotlin.String) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_authenticate(
                thisPtr,
                FfiConverterULong.lower(`userId`),FfiConverterString.lower(`token`),FfiConverterString.lower(`deviceId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `batchGetPresence`(`userIds`: List<kotlin.ULong>) : List<PresenceStatus> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_batch_get_presence(
                thisPtr,
                FfiConverterSequenceULong.lower(`userIds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypePresenceStatus.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `build`(): kotlin.String {
            return FfiConverterString.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_build(
        it, _status)!!
}
    }
    )
    }
    

    actual override fun `builder`(): kotlin.String {
            return FfiConverterString.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_builder(
        it, _status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `channelBroadcastCreateRemote`(`payload`: ChannelBroadcastCreateInput) : ChannelBroadcastCreateView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_create_remote(
                thisPtr,
                FfiConverterTypeChannelBroadcastCreateInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeChannelBroadcastCreateView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `channelBroadcastListRemote`(`payload`: ChannelBroadcastListInput) : ChannelBroadcastListView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_list_remote(
                thisPtr,
                FfiConverterTypeChannelBroadcastListInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeChannelBroadcastListView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `channelBroadcastSubscribeRemote`(`payload`: ChannelBroadcastSubscribeInput) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_subscribe_remote(
                thisPtr,
                FfiConverterTypeChannelBroadcastSubscribeInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `channelContentListRemote`(`payload`: ChannelContentListInput) : ChannelContentListView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_content_list_remote(
                thisPtr,
                FfiConverterTypeChannelContentListInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeChannelContentListView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `channelContentPublishRemote`(`payload`: ChannelContentPublishInput) : ChannelContentPublishView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_content_publish_remote(
                thisPtr,
                FfiConverterTypeChannelContentPublishInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeChannelContentPublishView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `channelNotificationMode`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : kotlin.Int {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_notification_mode(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i32(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i32(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i32(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i32(future) },
        // lift function
        { FfiConverterInt.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `channelSendQueueSetEnabled`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `enabled`: kotlin.Boolean) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_send_queue_set_enabled(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterBoolean.lower(`enabled`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `channelTags`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : List<kotlin.String> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_tags(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceString.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `channelUnreadStats`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : kotlin.Int {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_unread_stats(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i32(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i32(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i32(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i32(future) },
        // lift function
        { FfiConverterInt.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `checkBlacklist`(`targetUserId`: kotlin.ULong) : BlacklistCheckResult {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_check_blacklist(
                thisPtr,
                FfiConverterULong.lower(`targetUserId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeBlacklistCheckResult.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `checkFriend`(`friendId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_check_friend(
                thisPtr,
                FfiConverterULong.lower(`friendId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `clearLocalState`() {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_clear_local_state(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `clearPresenceCache`() {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_clear_presence_cache(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `config`(): PrivchatConfig {
            return FfiConverterTypePrivchatConfig.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_config(
        it, _status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `connect`() {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_connect(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `connectBlocking`() {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_connect_blocking(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `connectionState`() : ConnectionState {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_connection_state(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeConnectionState.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `connectionTimeout`(): kotlin.ULong {
            return FfiConverterULong.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_connection_timeout(
        it, _status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `createGroup`(`name`: kotlin.String, `description`: kotlin.String?, `memberIds`: List<kotlin.ULong>?) : GroupCreateResult {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_create_group(
                thisPtr,
                FfiConverterString.lower(`name`),FfiConverterOptionalString.lower(`description`),FfiConverterOptionalSequenceULong.lower(`memberIds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeGroupCreateResult.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `createLocalMessage`(`input`: NewMessage) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_create_local_message(
                thisPtr,
                FfiConverterTypeNewMessage.lower(`input`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `dataDir`() : kotlin.String {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_data_dir(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterString.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `debugMode`(): kotlin.Boolean {
            return FfiConverterBoolean.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_debug_mode(
        it, _status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `deleteBlacklistEntry`(`blockedUserId`: kotlin.ULong) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_blacklist_entry(
                thisPtr,
                FfiConverterULong.lower(`blockedUserId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `deleteChannelMember`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `memberUid`: kotlin.ULong) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_channel_member(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterULong.lower(`memberUid`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `deleteFriend`(`friendId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_friend(
                thisPtr,
                FfiConverterULong.lower(`friendId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `disconnect`() {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_disconnect(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `dmPeerUserId`(`channelId`: kotlin.ULong) : kotlin.ULong? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_dm_peer_user_id(
                thisPtr,
                FfiConverterULong.lower(`channelId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `downloadAttachmentToCache`(`sourcePath`: kotlin.String, `fileName`: kotlin.String) : kotlin.String {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_cache(
                thisPtr,
                FfiConverterString.lower(`sourcePath`),FfiConverterString.lower(`fileName`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterString.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `downloadAttachmentToMessageDir`(`sourcePath`: kotlin.String, `messageId`: kotlin.ULong, `fileName`: kotlin.String) : kotlin.String {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_message_dir(
                thisPtr,
                FfiConverterString.lower(`sourcePath`),FfiConverterULong.lower(`messageId`),FfiConverterString.lower(`fileName`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterString.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `downloadAttachmentToPath`(`sourcePath`: kotlin.String, `targetPath`: kotlin.String) : kotlin.String {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_path(
                thisPtr,
                FfiConverterString.lower(`sourcePath`),FfiConverterString.lower(`targetPath`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterString.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `editMessage`(`messageId`: kotlin.ULong, `content`: kotlin.String, `editedAt`: kotlin.Int) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_edit_message(
                thisPtr,
                FfiConverterULong.lower(`messageId`),FfiConverterString.lower(`content`),FfiConverterInt.lower(`editedAt`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `editMessageBlocking`(`messageId`: kotlin.ULong, `content`: kotlin.String, `editedAt`: kotlin.Int) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_edit_message_blocking(
                thisPtr,
                FfiConverterULong.lower(`messageId`),FfiConverterString.lower(`content`),FfiConverterInt.lower(`editedAt`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `enqueueOutboundFile`(`messageId`: kotlin.ULong, `routeKey`: kotlin.String, `payload`: kotlin.ByteArray) : FileQueueRef {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_outbound_file(
                thisPtr,
                FfiConverterULong.lower(`messageId`),FfiConverterString.lower(`routeKey`),FfiConverterByteArray.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeFileQueueRef.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `enqueueOutboundMessage`(`messageId`: kotlin.ULong, `payload`: kotlin.ByteArray) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_outbound_message(
                thisPtr,
                FfiConverterULong.lower(`messageId`),FfiConverterByteArray.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `enqueueText`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `fromUid`: kotlin.ULong, `content`: kotlin.String) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_text(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterULong.lower(`fromUid`),FfiConverterString.lower(`content`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `enterBackground`()
        = 
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_enter_background(
        it, _status)!!
}
    }
    
    

    actual override fun `enterForeground`()
        = 
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_enter_foreground(
        it, _status)!!
}
    }
    
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `entitySyncRemote`(`payload`: SyncEntitiesInput) : SyncEntitiesView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_entity_sync_remote(
                thisPtr,
                FfiConverterTypeSyncEntitiesInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeSyncEntitiesView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `eventConfig`(): EventConfigView {
            return FfiConverterTypeEventConfigView.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_event_config(
        it, _status)!!
}
    }
    )
    }
    

    actual override fun `eventStreamCursor`(): kotlin.ULong {
            return FfiConverterULong.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_event_stream_cursor(
        it, _status)!!
}
    }
    )
    }
    

    actual override fun `eventsSince`(`sequenceId`: kotlin.ULong, `limit`: kotlin.ULong): List<SequencedSdkEvent> {
            return FfiConverterSequenceTypeSequencedSdkEvent.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_events_since(
        it, FfiConverterULong.lower(`sequenceId`),FfiConverterULong.lower(`limit`),_status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `fetchGroupMembersRemote`(`groupId`: kotlin.ULong, `page`: kotlin.UInt?, `pageSize`: kotlin.UInt?) : GroupMemberRemoteList {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_fetch_group_members_remote(
                thisPtr,
                FfiConverterULong.lower(`groupId`),FfiConverterOptionalUInt.lower(`page`),FfiConverterOptionalUInt.lower(`pageSize`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeGroupMemberRemoteList.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `fetchPresence`(`userIds`: List<kotlin.ULong>) : List<PresenceStatus> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_fetch_presence(
                thisPtr,
                FfiConverterSequenceULong.lower(`userIds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypePresenceStatus.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `fileApiBaseUrl`(): kotlin.String {
            return FfiConverterString.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_api_base_url(
        it, _status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `fileRequestUploadTokenRemote`(`payload`: FileRequestUploadTokenInput) : FileRequestUploadTokenView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_request_upload_token_remote(
                thisPtr,
                FfiConverterTypeFileRequestUploadTokenInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeFileRequestUploadTokenView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `fileUploadCallbackRemote`(`payload`: FileUploadCallbackInput) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_upload_callback_remote(
                thisPtr,
                FfiConverterTypeFileUploadCallbackInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getAllUnreadMentionCounts`(`userId`: kotlin.ULong) : List<UnreadMentionCount> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_all_unread_mention_counts(
                thisPtr,
                FfiConverterULong.lower(`userId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeUnreadMentionCount.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getAllUserSettings`() : UserSettingsView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_all_user_settings(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeUserSettingsView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getBlacklist`() : List<StoredBlacklistEntry> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_blacklist(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredBlacklistEntry.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getChannelById`(`channelId`: kotlin.ULong) : StoredChannel? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_by_id(
                thisPtr,
                FfiConverterULong.lower(`channelId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalTypeStoredChannel.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getChannelExtra`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : StoredChannelExtra? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_extra(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalTypeStoredChannelExtra.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getChannelListEntries`(`page`: kotlin.ULong, `pageSize`: kotlin.ULong) : List<StoredChannel> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_list_entries(
                thisPtr,
                FfiConverterULong.lower(`page`),FfiConverterULong.lower(`pageSize`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredChannel.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getChannelSyncState`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : ChannelSyncState {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_sync_state(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeChannelSyncState.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getChannelUnreadCount`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : kotlin.Int {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_unread_count(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i32(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i32(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i32(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i32(future) },
        // lift function
        { FfiConverterInt.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getChannels`(`limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredChannel> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channels(
                thisPtr,
                FfiConverterULong.lower(`limit`),FfiConverterULong.lower(`offset`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredChannel.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getConnectionState`() : ConnectionState {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_connection_state(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeConnectionState.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getConnectionSummary`() : ConnectionSummary {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_connection_summary(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeConnectionSummary.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getDevicePushStatus`(`deviceId`: kotlin.String?) : DevicePushStatusView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_device_push_status(
                thisPtr,
                FfiConverterOptionalString.lower(`deviceId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeDevicePushStatusView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getEarliestId`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : kotlin.ULong? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_earliest_id(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getFriendPendingRequests`() : List<FriendPendingEntry> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_friend_pending_requests(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeFriendPendingEntry.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getFriends`(`limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredFriend> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_friends(
                thisPtr,
                FfiConverterULong.lower(`limit`),FfiConverterULong.lower(`offset`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredFriend.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getGroupById`(`groupId`: kotlin.ULong) : StoredGroup? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_by_id(
                thisPtr,
                FfiConverterULong.lower(`groupId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalTypeStoredGroup.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getGroupInfo`(`groupId`: kotlin.ULong) : GroupInfoView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_info(
                thisPtr,
                FfiConverterULong.lower(`groupId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeGroupInfoView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getGroupMembers`(`groupId`: kotlin.ULong, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredGroupMember> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_members(
                thisPtr,
                FfiConverterULong.lower(`groupId`),FfiConverterULong.lower(`limit`),FfiConverterULong.lower(`offset`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredGroupMember.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getGroups`(`limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredGroup> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_groups(
                thisPtr,
                FfiConverterULong.lower(`limit`),FfiConverterULong.lower(`offset`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredGroup.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getMessageById`(`messageId`: kotlin.ULong) : StoredMessage? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_message_by_id(
                thisPtr,
                FfiConverterULong.lower(`messageId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalTypeStoredMessage.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getMessageExtra`(`messageId`: kotlin.ULong) : StoredMessageExtra? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_message_extra(
                thisPtr,
                FfiConverterULong.lower(`messageId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalTypeStoredMessageExtra.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getMessages`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredMessage> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_messages(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterULong.lower(`limit`),FfiConverterULong.lower(`offset`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredMessage.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getMessagesRemote`(`channelId`: kotlin.ULong, `beforeServerMessageId`: kotlin.ULong?, `limit`: kotlin.UInt?) : MessageHistoryView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_messages_remote(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterOptionalULong.lower(`beforeServerMessageId`),FfiConverterOptionalUInt.lower(`limit`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeMessageHistoryView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getOrCreateDirectChannel`(`peerUserId`: kotlin.ULong) : DirectChannelResult {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_or_create_direct_channel(
                thisPtr,
                FfiConverterULong.lower(`peerUserId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeDirectChannelResult.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getPresence`(`userId`: kotlin.ULong) : PresenceStatus? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_presence(
                thisPtr,
                FfiConverterULong.lower(`userId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalTypePresenceStatus.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getPresenceStats`() : PresenceStatsView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_presence_stats(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypePresenceStatsView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getPrivacySettings`() : PrivacySettingsView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_privacy_settings(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypePrivacySettingsView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getProfile`() : ProfileView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_profile(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeProfileView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getTotalUnreadCount`(`excludeMuted`: kotlin.Boolean) : kotlin.Int {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_total_unread_count(
                thisPtr,
                FfiConverterBoolean.lower(`excludeMuted`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i32(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i32(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i32(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i32(future) },
        // lift function
        { FfiConverterInt.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getTypingStats`() : TypingStatsView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_typing_stats(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeTypingStatsView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getUnreadMentionCount`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `userId`: kotlin.ULong) : kotlin.Int {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_unread_mention_count(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterULong.lower(`userId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i32(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i32(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i32(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i32(future) },
        // lift function
        { FfiConverterInt.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getUserById`(`userId`: kotlin.ULong) : StoredUser? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_user_by_id(
                thisPtr,
                FfiConverterULong.lower(`userId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalTypeStoredUser.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `getUserSetting`(`key`: kotlin.String) : kotlin.String? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_user_setting(
                thisPtr,
                FfiConverterString.lower(`key`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalString.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `groupAddMembersRemote`(`groupId`: kotlin.ULong, `userIds`: List<kotlin.ULong>) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_add_members_remote(
                thisPtr,
                FfiConverterULong.lower(`groupId`),FfiConverterSequenceULong.lower(`userIds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `groupApprovalHandleRemote`(`approvalId`: kotlin.ULong, `approved`: kotlin.Boolean, `reason`: kotlin.String?) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_approval_handle_remote(
                thisPtr,
                FfiConverterULong.lower(`approvalId`),FfiConverterBoolean.lower(`approved`),FfiConverterOptionalString.lower(`reason`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `groupApprovalListRemote`(`groupId`: kotlin.ULong, `page`: kotlin.UInt?, `pageSize`: kotlin.UInt?) : GroupApprovalListView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_approval_list_remote(
                thisPtr,
                FfiConverterULong.lower(`groupId`),FfiConverterOptionalUInt.lower(`page`),FfiConverterOptionalUInt.lower(`pageSize`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeGroupApprovalListView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `groupGetSettingsRemote`(`groupId`: kotlin.ULong) : GroupSettingsView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_get_settings_remote(
                thisPtr,
                FfiConverterULong.lower(`groupId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeGroupSettingsView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `groupLeaveRemote`(`groupId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_leave_remote(
                thisPtr,
                FfiConverterULong.lower(`groupId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `groupMuteAllRemote`(`groupId`: kotlin.ULong, `enabled`: kotlin.Boolean) : GroupMuteAllView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_mute_all_remote(
                thisPtr,
                FfiConverterULong.lower(`groupId`),FfiConverterBoolean.lower(`enabled`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeGroupMuteAllView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `groupMuteMemberRemote`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong, `durationSeconds`: kotlin.ULong?) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_mute_member_remote(
                thisPtr,
                FfiConverterULong.lower(`groupId`),FfiConverterULong.lower(`userId`),FfiConverterOptionalULong.lower(`durationSeconds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `groupQrcodeGenerateRemote`(`groupId`: kotlin.ULong, `expireSeconds`: kotlin.ULong?) : GroupQrCodeGenerateView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_qrcode_generate_remote(
                thisPtr,
                FfiConverterULong.lower(`groupId`),FfiConverterOptionalULong.lower(`expireSeconds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeGroupQrCodeGenerateView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `groupQrcodeJoinRemote`(`qrKey`: kotlin.String, `token`: kotlin.String?) : GroupQrCodeJoinResult {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_qrcode_join_remote(
                thisPtr,
                FfiConverterString.lower(`qrKey`),FfiConverterOptionalString.lower(`token`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeGroupQrCodeJoinResult.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `groupRemoveMemberRemote`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_remove_member_remote(
                thisPtr,
                FfiConverterULong.lower(`groupId`),FfiConverterULong.lower(`userId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `groupSetRoleRemote`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong, `role`: kotlin.String) : GroupRoleSetView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_set_role_remote(
                thisPtr,
                FfiConverterULong.lower(`groupId`),FfiConverterULong.lower(`userId`),FfiConverterString.lower(`role`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeGroupRoleSetView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `groupTransferOwnerRemote`(`groupId`: kotlin.ULong, `targetUserId`: kotlin.ULong) : GroupTransferOwnerView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_transfer_owner_remote(
                thisPtr,
                FfiConverterULong.lower(`groupId`),FfiConverterULong.lower(`targetUserId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeGroupTransferOwnerView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `groupUnmuteMemberRemote`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_unmute_member_remote(
                thisPtr,
                FfiConverterULong.lower(`groupId`),FfiConverterULong.lower(`userId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `groupUpdateSettingsRemote`(`payload`: GroupSettingsUpdateInput) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_update_settings_remote(
                thisPtr,
                FfiConverterTypeGroupSettingsUpdateInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `heartbeatInterval`(): kotlin.ULong {
            return FfiConverterULong.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_heartbeat_interval(
        it, _status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `hideChannel`(`channelId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_hide_channel(
                thisPtr,
                FfiConverterULong.lower(`channelId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `httpClientConfig`(): HttpClientConfigView {
            return FfiConverterTypeHttpClientConfigView.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_http_client_config(
        it, _status)!!
}
    }
    )
    }
    

    actual override fun `imageSendMaxEdge`(): kotlin.UInt {
            return FfiConverterUInt.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_image_send_max_edge(
        it, _status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `inviteToGroup`(`groupId`: kotlin.ULong, `memberIds`: List<kotlin.ULong>) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_invite_to_group(
                thisPtr,
                FfiConverterULong.lower(`groupId`),FfiConverterSequenceULong.lower(`memberIds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `isBootstrapCompleted`() : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_bootstrap_completed(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `isConnected`() : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_connected(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `isEventReadBy`(`serverMessageId`: kotlin.ULong, `userId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_event_read_by(
                thisPtr,
                FfiConverterULong.lower(`serverMessageId`),FfiConverterULong.lower(`userId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `isInitialized`() : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_initialized(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `isShuttingDown`() : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_shutting_down(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `isSupervisedSyncRunning`(): kotlin.Boolean {
            return FfiConverterBoolean.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_supervised_sync_running(
        it, _status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `joinGroupByQrcode`(`qrKey`: kotlin.String) : GroupQrCodeJoinResult {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_join_group_by_qrcode(
                thisPtr,
                FfiConverterString.lower(`qrKey`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeGroupQrCodeJoinResult.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `kvGet`(`key`: kotlin.String) : kotlin.ByteArray? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_kv_get(
                thisPtr,
                FfiConverterString.lower(`key`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalByteArray.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `kvPut`(`key`: kotlin.String, `value`: kotlin.ByteArray) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_kv_put(
                thisPtr,
                FfiConverterString.lower(`key`),FfiConverterByteArray.lower(`value`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `leaveChannel`(`channelId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_leave_channel(
                thisPtr,
                FfiConverterULong.lower(`channelId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `leaveGroup`(`groupId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_leave_group(
                thisPtr,
                FfiConverterULong.lower(`groupId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `listBlacklistEntries`(`limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredBlacklistEntry> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_blacklist_entries(
                thisPtr,
                FfiConverterULong.lower(`limit`),FfiConverterULong.lower(`offset`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredBlacklistEntry.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `listChannelMembers`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredChannelMember> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_channel_members(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterULong.lower(`limit`),FfiConverterULong.lower(`offset`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredChannelMember.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `listChannels`(`limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredChannel> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_channels(
                thisPtr,
                FfiConverterULong.lower(`limit`),FfiConverterULong.lower(`offset`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredChannel.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `listFriends`(`limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredFriend> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_friends(
                thisPtr,
                FfiConverterULong.lower(`limit`),FfiConverterULong.lower(`offset`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredFriend.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `listGroupMembers`(`groupId`: kotlin.ULong, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredGroupMember> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_group_members(
                thisPtr,
                FfiConverterULong.lower(`groupId`),FfiConverterULong.lower(`limit`),FfiConverterULong.lower(`offset`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredGroupMember.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `listGroups`(`limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredGroup> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_groups(
                thisPtr,
                FfiConverterULong.lower(`limit`),FfiConverterULong.lower(`offset`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredGroup.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `listLocalAccounts`() : List<LocalAccountSummary> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_local_accounts(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeLocalAccountSummary.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `listMessageReactions`(`messageId`: kotlin.ULong, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredMessageReaction> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_message_reactions(
                thisPtr,
                FfiConverterULong.lower(`messageId`),FfiConverterULong.lower(`limit`),FfiConverterULong.lower(`offset`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredMessageReaction.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `listMessages`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredMessage> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_messages(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterULong.lower(`limit`),FfiConverterULong.lower(`offset`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredMessage.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `listMyDevices`() : List<DeviceInfoView> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_my_devices(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeDeviceInfoView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `listPendingReminders`(`uid`: kotlin.ULong, `limit`: kotlin.ULong, `offset`: kotlin.ULong) : List<StoredReminder> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_pending_reminders(
                thisPtr,
                FfiConverterULong.lower(`uid`),FfiConverterULong.lower(`limit`),FfiConverterULong.lower(`offset`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredReminder.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `listReactions`(`serverMessageId`: kotlin.ULong) : MessageReactionListView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_reactions(
                thisPtr,
                FfiConverterULong.lower(`serverMessageId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeMessageReactionListView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `listUnreadMentionMessageIds`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `userId`: kotlin.ULong, `limit`: kotlin.ULong) : List<kotlin.ULong> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_unread_mention_message_ids(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterULong.lower(`userId`),FfiConverterULong.lower(`limit`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `listUsersByIds`(`userIds`: List<kotlin.ULong>) : List<StoredUser> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_users_by_ids(
                thisPtr,
                FfiConverterSequenceULong.lower(`userIds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredUser.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `logConnectionState`() : ConnectionSummary {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_log_connection_state(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeConnectionSummary.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `login`(`username`: kotlin.String, `password`: kotlin.String, `deviceId`: kotlin.String) : LoginResult {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_login(
                thisPtr,
                FfiConverterString.lower(`username`),FfiConverterString.lower(`password`),FfiConverterString.lower(`deviceId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeLoginResult.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `logout`() {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_logout(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `markAllMentionsRead`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `userId`: kotlin.ULong) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_all_mentions_read(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterULong.lower(`userId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `markAsRead`(`channelId`: kotlin.ULong, `serverMessageId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_as_read(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterULong.lower(`serverMessageId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `markAsReadBlocking`(`channelId`: kotlin.ULong, `serverMessageId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_as_read_blocking(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterULong.lower(`serverMessageId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `markChannelRead`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_channel_read(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `markFullyReadAt`(`channelId`: kotlin.ULong, `serverMessageId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_fully_read_at(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterULong.lower(`serverMessageId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `markMentionRead`(`messageId`: kotlin.ULong, `userId`: kotlin.ULong) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_mention_read(
                thisPtr,
                FfiConverterULong.lower(`messageId`),FfiConverterULong.lower(`userId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `markMessageSent`(`messageId`: kotlin.ULong, `serverMessageId`: kotlin.ULong) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_message_sent(
                thisPtr,
                FfiConverterULong.lower(`messageId`),FfiConverterULong.lower(`serverMessageId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `markReminderDone`(`reminderId`: kotlin.ULong, `done`: kotlin.Boolean) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_reminder_done(
                thisPtr,
                FfiConverterULong.lower(`reminderId`),FfiConverterBoolean.lower(`done`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `messageReadList`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong) : MessageReadListView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_read_list(
                thisPtr,
                FfiConverterULong.lower(`serverMessageId`),FfiConverterULong.lower(`channelId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeMessageReadListView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `messageReadStats`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong) : MessageReadStatsView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_read_stats(
                thisPtr,
                FfiConverterULong.lower(`serverMessageId`),FfiConverterULong.lower(`channelId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeMessageReadStatsView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `messageUnreadCountRemote`(`channelId`: kotlin.ULong) : MessageUnreadCountView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_unread_count_remote(
                thisPtr,
                FfiConverterULong.lower(`channelId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeMessageUnreadCountView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `muteChannel`(`channelId`: kotlin.ULong, `muted`: kotlin.Boolean) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_mute_channel(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterBoolean.lower(`muted`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `needsSync`() : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_needs_sync(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `nextEvent`(`timeoutMs`: kotlin.ULong) : SdkEvent? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_next_event(
                thisPtr,
                FfiConverterULong.lower(`timeoutMs`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalTypeSdkEvent.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `nextEventEnvelope`(`timeoutMs`: kotlin.ULong) : SequencedSdkEvent? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_next_event_envelope(
                thisPtr,
                FfiConverterULong.lower(`timeoutMs`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalTypeSequencedSdkEvent.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `onAppBackground`()
        = 
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_app_background(
        it, _status)!!
}
    }
    
    

    actual override fun `onAppForeground`()
        = 
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_app_foreground(
        it, _status)!!
}
    }
    
    

    actual override fun `onConnectionStateChanged`()
        = 
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_connection_state_changed(
        it, _status)!!
}
    }
    
    

    actual override fun `onMessageReceived`()
        = 
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_message_received(
        it, _status)!!
}
    }
    
    

    actual override fun `onReactionChanged`()
        = 
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_reaction_changed(
        it, _status)!!
}
    }
    
    

    actual override fun `onTypingIndicator`()
        = 
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_typing_indicator(
        it, _status)!!
}
    }
    
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `ownLastRead`(`channelId`: kotlin.ULong) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_own_last_read(
                thisPtr,
                FfiConverterULong.lower(`channelId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `paginateBack`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `page`: kotlin.ULong, `pageSize`: kotlin.ULong) : List<StoredMessage> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_paginate_back(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterULong.lower(`page`),FfiConverterULong.lower(`pageSize`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredMessage.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `paginateForward`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `page`: kotlin.ULong, `pageSize`: kotlin.ULong) : List<StoredMessage> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_paginate_forward(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterULong.lower(`page`),FfiConverterULong.lower(`pageSize`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredMessage.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `peekOutboundFiles`(`queueIndex`: kotlin.ULong, `limit`: kotlin.ULong) : List<QueueMessage> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_peek_outbound_files(
                thisPtr,
                FfiConverterULong.lower(`queueIndex`),FfiConverterULong.lower(`limit`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeQueueMessage.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `peekOutboundMessages`(`limit`: kotlin.ULong) : List<QueueMessage> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_peek_outbound_messages(
                thisPtr,
                FfiConverterULong.lower(`limit`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeQueueMessage.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `pinChannel`(`channelId`: kotlin.ULong, `pinned`: kotlin.Boolean) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_pin_channel(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterBoolean.lower(`pinned`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `ping`() {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_ping(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `qrcodeGenerate`(`qrType`: kotlin.String, `payload`: kotlin.String, `expireSeconds`: kotlin.ULong?) : QrCodeGenerateView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_generate(
                thisPtr,
                FfiConverterString.lower(`qrType`),FfiConverterString.lower(`payload`),FfiConverterOptionalULong.lower(`expireSeconds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeQrCodeGenerateView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `qrcodeList`(`includeRevoked`: kotlin.Boolean?) : QrCodeListView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_list(
                thisPtr,
                FfiConverterOptionalBoolean.lower(`includeRevoked`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeQrCodeListView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `qrcodeRefresh`(`qrType`: kotlin.String, `targetId`: kotlin.String) : QrCodeRefreshView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_refresh(
                thisPtr,
                FfiConverterString.lower(`qrType`),FfiConverterString.lower(`targetId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeQrCodeRefreshView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `qrcodeResolve`(`qrKey`: kotlin.String, `token`: kotlin.String?) : QrCodeResolveView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_resolve(
                thisPtr,
                FfiConverterString.lower(`qrKey`),FfiConverterOptionalString.lower(`token`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeQrCodeResolveView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `qrcodeRevoke`(`qrKey`: kotlin.String) : QrCodeRevokeView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_revoke(
                thisPtr,
                FfiConverterString.lower(`qrKey`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeQrCodeRevokeView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `queueConfig`(): QueueConfigView {
            return FfiConverterTypeQueueConfigView.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_queue_config(
        it, _status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `reactionStats`(`serverMessageId`: kotlin.ULong) : MessageReactionStatsView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_reaction_stats(
                thisPtr,
                FfiConverterULong.lower(`serverMessageId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeMessageReactionStatsView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `reactions`(`serverMessageId`: kotlin.ULong) : MessageReactionListView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_reactions(
                thisPtr,
                FfiConverterULong.lower(`serverMessageId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeMessageReactionListView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `reactionsBatch`(`serverMessageIds`: List<kotlin.ULong>) : ReactionsBatchView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_reactions_batch(
                thisPtr,
                FfiConverterSequenceULong.lower(`serverMessageIds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeReactionsBatchView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `recallMessage`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_recall_message(
                thisPtr,
                FfiConverterULong.lower(`serverMessageId`),FfiConverterULong.lower(`channelId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `recallMessageBlocking`(`serverMessageId`: kotlin.ULong, `channelId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_recall_message_blocking(
                thisPtr,
                FfiConverterULong.lower(`serverMessageId`),FfiConverterULong.lower(`channelId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `recentEvents`(`limit`: kotlin.ULong): List<SequencedSdkEvent> {
            return FfiConverterSequenceTypeSequencedSdkEvent.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_recent_events(
        it, FfiConverterULong.lower(`limit`),_status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `recordMention`(`input`: MentionInput) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_record_mention(
                thisPtr,
                FfiConverterTypeMentionInput.lower(`input`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `register`(`username`: kotlin.String, `password`: kotlin.String, `deviceId`: kotlin.String) : LoginResult {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_register(
                thisPtr,
                FfiConverterString.lower(`username`),FfiConverterString.lower(`password`),FfiConverterString.lower(`deviceId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeLoginResult.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `registerLifecycleHook`()
        = 
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_register_lifecycle_hook(
        it, _status)!!
}
    }
    
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `rejectFriendRequest`(`fromUserId`: kotlin.ULong, `message`: kotlin.String?) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_reject_friend_request(
                thisPtr,
                FfiConverterULong.lower(`fromUserId`),FfiConverterOptionalString.lower(`message`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `removeChannelMember`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `memberUid`: kotlin.ULong) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_channel_member(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterULong.lower(`memberUid`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `removeFromBlacklist`(`blockedUserId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_from_blacklist(
                thisPtr,
                FfiConverterULong.lower(`blockedUserId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `removeGroupMember`(`groupId`: kotlin.ULong, `userId`: kotlin.ULong) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_group_member(
                thisPtr,
                FfiConverterULong.lower(`groupId`),FfiConverterULong.lower(`userId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `removeReaction`(`serverMessageId`: kotlin.ULong, `emoji`: kotlin.String) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_reaction(
                thisPtr,
                FfiConverterULong.lower(`serverMessageId`),FfiConverterString.lower(`emoji`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `requireCurrentUserId`() : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_require_current_user_id(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `resolveChannelIdByServerMessageId`(`serverMessageId`: kotlin.ULong) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_channel_id_by_server_message_id(
                thisPtr,
                FfiConverterULong.lower(`serverMessageId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `resolveChannelType`(`channelId`: kotlin.ULong) : kotlin.Int {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_channel_type(
                thisPtr,
                FfiConverterULong.lower(`channelId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i32(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i32(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i32(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i32(future) },
        // lift function
        { FfiConverterInt.lift(it!!) },
        // Error FFI converter
        UniffiNullRustCallStatusErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `resolveLocalMessageIdByServerMessageId`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `serverMessageId`: kotlin.ULong) : kotlin.ULong? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_local_message_id_by_server_message_id(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterULong.lower(`serverMessageId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `retryConfig`(): RetryConfigView {
            return FfiConverterTypeRetryConfigView.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_retry_config(
        it, _status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `retryMessage`(`messageId`: kotlin.ULong) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_retry_message(
                thisPtr,
                FfiConverterULong.lower(`messageId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `rpcCall`(`route`: kotlin.String, `bodyJson`: kotlin.String) : kotlin.String {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_rpc_call(
                thisPtr,
                FfiConverterString.lower(`route`),FfiConverterString.lower(`bodyJson`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterString.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `runBootstrapSync`() {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_run_bootstrap_sync(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `searchChannel`(`keyword`: kotlin.String) : List<StoredChannel> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_channel(
                thisPtr,
                FfiConverterString.lower(`keyword`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredChannel.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `searchMessages`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `keyword`: kotlin.String) : List<StoredMessage> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_messages(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterString.lower(`keyword`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeStoredMessage.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `searchUserByQrcode`(`qrKey`: kotlin.String, `token`: kotlin.String?) : AccountSearchResultView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_user_by_qrcode(
                thisPtr,
                FfiConverterString.lower(`qrKey`),FfiConverterOptionalString.lower(`token`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeAccountSearchResultView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `searchUsers`(`query`: kotlin.String) : List<SearchUserEntry> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_users(
                thisPtr,
                FfiConverterString.lower(`query`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeSearchUserEntry.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `seenByForEvent`(`serverMessageId`: kotlin.ULong) : List<SeenByEntry> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_seen_by_for_event(
                thisPtr,
                FfiConverterULong.lower(`serverMessageId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypeSeenByEntry.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `sendAttachmentBytes`(`messageId`: kotlin.ULong, `routeKey`: kotlin.String, `payload`: kotlin.ByteArray) : FileQueueRef {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_attachment_bytes(
                thisPtr,
                FfiConverterULong.lower(`messageId`),FfiConverterString.lower(`routeKey`),FfiConverterByteArray.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeFileQueueRef.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `sendAttachmentFromPath`(`messageId`: kotlin.ULong, `routeKey`: kotlin.String, `path`: kotlin.String) : FileQueueRef {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_attachment_from_path(
                thisPtr,
                FfiConverterULong.lower(`messageId`),FfiConverterString.lower(`routeKey`),FfiConverterString.lower(`path`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeFileQueueRef.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `sendFriendRequest`(`targetUserId`: kotlin.ULong, `message`: kotlin.String?, `source`: kotlin.String?, `sourceId`: kotlin.String?) : FriendRequestResult {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_friend_request(
                thisPtr,
                FfiConverterULong.lower(`targetUserId`),FfiConverterOptionalString.lower(`message`),FfiConverterOptionalString.lower(`source`),FfiConverterOptionalString.lower(`sourceId`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeFriendRequestResult.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `sendLocalMessageNow`(`input`: NewMessage) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_local_message_now(
                thisPtr,
                FfiConverterTypeNewMessage.lower(`input`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `sendMessage`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `fromUid`: kotlin.ULong, `content`: kotlin.String) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterULong.lower(`fromUid`),FfiConverterString.lower(`content`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `sendMessageBlocking`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `fromUid`: kotlin.ULong, `content`: kotlin.String) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_blocking(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterULong.lower(`fromUid`),FfiConverterString.lower(`content`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `sendMessageWithInput`(`input`: NewMessage) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_with_input(
                thisPtr,
                FfiConverterTypeNewMessage.lower(`input`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `sendMessageWithOptions`(`input`: NewMessage, `options`: SendMessageOptionsInput) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_with_options(
                thisPtr,
                FfiConverterTypeNewMessage.lower(`input`),FfiConverterTypeSendMessageOptionsInput.lower(`options`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `sendQueueSetEnabled`(`enabled`: kotlin.Boolean) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_queue_set_enabled(
                thisPtr,
                FfiConverterBoolean.lower(`enabled`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `sendTyping`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `isTyping`: kotlin.Boolean, `actionType`: TypingActionType) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_typing(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterBoolean.lower(`isTyping`),FfiConverterTypeTypingActionType.lower(`actionType`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `serverConfig`(): PrivchatConfig {
            return FfiConverterTypePrivchatConfig.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_server_config(
        it, _status)!!
}
    }
    )
    }
    

    actual override fun `servers`(): List<ServerEndpoint> {
            return FfiConverterSequenceTypeServerEndpoint.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_servers(
        it, _status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `sessionSnapshot`() : SessionSnapshot? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_session_snapshot(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalTypeSessionSnapshot.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `setChannelFavourite`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `enabled`: kotlin.Boolean) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_favourite(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterBoolean.lower(`enabled`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `setChannelLowPriority`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `enabled`: kotlin.Boolean) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_low_priority(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterBoolean.lower(`enabled`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `setChannelNotificationMode`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int, `mode`: kotlin.Int) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_notification_mode(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterInt.lower(`mode`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `setCurrentUid`(`uid`: kotlin.String) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_current_uid(
                thisPtr,
                FfiConverterString.lower(`uid`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `setMessagePinned`(`messageId`: kotlin.ULong, `isPinned`: kotlin.Boolean) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_pinned(
                thisPtr,
                FfiConverterULong.lower(`messageId`),FfiConverterBoolean.lower(`isPinned`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `setMessageRead`(`messageId`: kotlin.ULong, `channelId`: kotlin.ULong, `channelType`: kotlin.Int, `isRead`: kotlin.Boolean) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_read(
                thisPtr,
                FfiConverterULong.lower(`messageId`),FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),FfiConverterBoolean.lower(`isRead`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `setMessageRevoke`(`messageId`: kotlin.ULong, `revoked`: kotlin.Boolean, `revoker`: kotlin.ULong?) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_revoke(
                thisPtr,
                FfiConverterULong.lower(`messageId`),FfiConverterBoolean.lower(`revoked`),FfiConverterOptionalULong.lower(`revoker`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `setNetworkHint`(`hint`: NetworkHint) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_network_hint(
                thisPtr,
                FfiConverterTypeNetworkHint.lower(`hint`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `setUserSetting`(`key`: kotlin.String, `value`: kotlin.String) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_user_setting(
                thisPtr,
                FfiConverterString.lower(`key`),FfiConverterString.lower(`value`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `setVideoProcessHook`()
        = 
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_video_process_hook(
        it, _status)!!
}
    }
    
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `shutdown`() {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_shutdown(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `shutdownBlocking`() {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_shutdown_blocking(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class)actual override fun `startSupervisedSync`(`intervalSecs`: kotlin.ULong)
        = 
    callWithPointer {
    uniffiRustCallWithError(PrivchatFfiExceptionErrorHandler) { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_supervised_sync(
        it, FfiConverterULong.lower(`intervalSecs`),_status)!!
}
    }
    
    

    actual override fun `startTransportDisconnectListener`()
        = 
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_transport_disconnect_listener(
        it, _status)!!
}
    }
    
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `startTyping`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_typing(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `startTypingBlocking`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_typing_blocking(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `stickerPackageDetailRemote`(`payload`: StickerPackageDetailInput) : StickerPackageDetailView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sticker_package_detail_remote(
                thisPtr,
                FfiConverterTypeStickerPackageDetailInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeStickerPackageDetailView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `stickerPackageListRemote`(`payload`: StickerPackageListInput) : StickerPackageListView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sticker_package_list_remote(
                thisPtr,
                FfiConverterTypeStickerPackageListInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeStickerPackageListView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `stopSupervisedSync`()
        = 
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_stop_supervised_sync(
        it, _status)!!
}
    }
    
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `stopTyping`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_stop_typing(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `storage`() : UserStoragePaths {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_storage(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeUserStoragePaths.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `subscribeEvents`(): kotlin.Boolean {
            return FfiConverterBoolean.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_events(
        it, _status)!!
}
    }
    )
    }
    

    actual override fun `subscribeNetworkStatus`(): kotlin.Boolean {
            return FfiConverterBoolean.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_network_status(
        it, _status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `subscribePresence`(`userIds`: List<kotlin.ULong>) : List<PresenceStatus> {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_presence(
                thisPtr,
                FfiConverterSequenceULong.lower(`userIds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterSequenceTypePresenceStatus.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `syncAllChannels`() : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_all_channels(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `syncBatchGetChannelPtsRemote`(`payload`: BatchGetChannelPtsInput) : BatchGetChannelPtsView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_batch_get_channel_pts_remote(
                thisPtr,
                FfiConverterTypeBatchGetChannelPtsInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeBatchGetChannelPtsView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `syncChannel`(`channelId`: kotlin.ULong, `channelType`: kotlin.Int) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_channel(
                thisPtr,
                FfiConverterULong.lower(`channelId`),FfiConverterInt.lower(`channelType`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `syncEntities`(`entityType`: kotlin.String, `scope`: kotlin.String?) : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_entities(
                thisPtr,
                FfiConverterString.lower(`entityType`),FfiConverterOptionalString.lower(`scope`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `syncEntitiesInBackground`(`entityType`: kotlin.String, `scope`: kotlin.String?) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_entities_in_background(
                thisPtr,
                FfiConverterString.lower(`entityType`),FfiConverterOptionalString.lower(`scope`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `syncGetChannelPtsRemote`(`payload`: GetChannelPtsInput) : ChannelPtsView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_get_channel_pts_remote(
                thisPtr,
                FfiConverterTypeGetChannelPtsInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeChannelPtsView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `syncGetDifferenceRemote`(`payload`: GetDifferenceInput) : GetDifferenceView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_get_difference_remote(
                thisPtr,
                FfiConverterTypeGetDifferenceInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeGetDifferenceView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `syncMessages`() : kotlin.ULong {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_messages(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_u64(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_u64(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_u64(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_u64(future) },
        // lift function
        { FfiConverterULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `syncMessagesInBackground`() {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_messages_in_background(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `syncSubmitRemote`(`payload`: SyncSubmitInput) : SyncSubmitView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_submit_remote(
                thisPtr,
                FfiConverterTypeSyncSubmitInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeSyncSubmitView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    actual override fun `timezoneHours`(): kotlin.Int {
            return FfiConverterInt.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_hours(
        it, _status)!!
}
    }
    )
    }
    

    actual override fun `timezoneLocal`(): kotlin.String {
            return FfiConverterString.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_local(
        it, _status)!!
}
    }
    )
    }
    

    actual override fun `timezoneMinutes`(): kotlin.Int {
            return FfiConverterInt.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_minutes(
        it, _status)!!
}
    }
    )
    }
    

    actual override fun `timezoneSeconds`(): kotlin.Int {
            return FfiConverterInt.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_seconds(
        it, _status)!!
}
    }
    )
    }
    

    actual override fun `toClientEndpoint`(): kotlin.String? {
            return FfiConverterOptionalString.lift(
    callWithPointer {
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_to_client_endpoint(
        it, _status)!!
}
    }
    )
    }
    

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `unsubscribePresence`(`userIds`: List<kotlin.ULong>) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_unsubscribe_presence(
                thisPtr,
                FfiConverterSequenceULong.lower(`userIds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `updateDevicePushState`(`deviceId`: kotlin.String, `apnsArmed`: kotlin.Boolean, `pushToken`: kotlin.String?) : DevicePushUpdateView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_device_push_state(
                thisPtr,
                FfiConverterString.lower(`deviceId`),FfiConverterBoolean.lower(`apnsArmed`),FfiConverterOptionalString.lower(`pushToken`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeDevicePushUpdateView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `updateMessageStatus`(`messageId`: kotlin.ULong, `status`: kotlin.Int) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_message_status(
                thisPtr,
                FfiConverterULong.lower(`messageId`),FfiConverterInt.lower(`status`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `updatePrivacySettings`(`payload`: AccountPrivacyUpdateInput) : kotlin.Boolean {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_privacy_settings(
                thisPtr,
                FfiConverterTypeAccountPrivacyUpdateInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_i8(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_i8(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_i8(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_i8(future) },
        // lift function
        { FfiConverterBoolean.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `updateProfile`(`payload`: ProfileUpdateInput) : ProfileView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_profile(
                thisPtr,
                FfiConverterTypeProfileUpdateInput.lower(`payload`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeProfileView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `upsertBlacklistEntry`(`input`: UpsertBlacklistInput) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_blacklist_entry(
                thisPtr,
                FfiConverterTypeUpsertBlacklistInput.lower(`input`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `upsertChannel`(`input`: UpsertChannelInput) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel(
                thisPtr,
                FfiConverterTypeUpsertChannelInput.lower(`input`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `upsertChannelExtra`(`input`: UpsertChannelExtraInput) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel_extra(
                thisPtr,
                FfiConverterTypeUpsertChannelExtraInput.lower(`input`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `upsertChannelMember`(`input`: UpsertChannelMemberInput) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel_member(
                thisPtr,
                FfiConverterTypeUpsertChannelMemberInput.lower(`input`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `upsertFriend`(`input`: UpsertFriendInput) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_friend(
                thisPtr,
                FfiConverterTypeUpsertFriendInput.lower(`input`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `upsertGroup`(`input`: UpsertGroupInput) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_group(
                thisPtr,
                FfiConverterTypeUpsertGroupInput.lower(`input`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `upsertGroupMember`(`input`: UpsertGroupMemberInput) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_group_member(
                thisPtr,
                FfiConverterTypeUpsertGroupMemberInput.lower(`input`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `upsertMessageReaction`(`input`: UpsertMessageReactionInput) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_message_reaction(
                thisPtr,
                FfiConverterTypeUpsertMessageReactionInput.lower(`input`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `upsertReminder`(`input`: UpsertReminderInput) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_reminder(
                thisPtr,
                FfiConverterTypeUpsertReminderInput.lower(`input`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `upsertUser`(`input`: UpsertUserInput) {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_user(
                thisPtr,
                FfiConverterTypeUpsertUserInput.lower(`input`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `userId`() : kotlin.ULong? {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_id(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterOptionalULong.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `userQrcodeGenerate`(`expireSeconds`: kotlin.ULong?) : UserQrCodeGenerateView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_generate(
                thisPtr,
                FfiConverterOptionalULong.lower(`expireSeconds`),
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeUserQrCodeGenerateView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `userQrcodeGet`() : UserQrCodeGetView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_get(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeUserQrCodeGetView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `userQrcodeRefresh`() : QrCodeRefreshView {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_refresh(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeQrCodeRefreshView.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `userStoragePaths`() : UserStoragePaths {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_storage_paths(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(future) },
        // lift function
        { FfiConverterTypeUserStoragePaths.lift(it!!) },
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    
    @Throws(PrivchatFfiException::class,kotlin.coroutines.cancellation.CancellationException::class)
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    actual override suspend fun `wipeCurrentUserFull`() {
        return uniffiRustCallAsync(
        callWithPointer { thisPtr ->
            UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_method_privchatclient_wipe_current_user_full(
                thisPtr,
                
            )!!
        },
        { future, callback, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_poll_void(future, callback, continuation)!! },
        { future, continuation -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_complete_void(future, continuation) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_free_void(future) },
        { future -> UniffiLib.INSTANCE.ffi_privchat_sdk_ffi_rust_future_cancel_void(future) },
        // lift function
        { Unit },
        
        // Error FFI converter
        PrivchatFfiExceptionErrorHandler,
    )
    }

    

    
    
    actual companion object
    
}





public object FfiConverterTypePrivchatClient: FfiConverter<

PrivchatClient

, Pointer> {

    override fun lower(value:

PrivchatClient

): Pointer {
        val obj = value as PrivchatClient
        return obj.uniffiClonePointer()
        }


    override fun lift(value: Pointer):

PrivchatClient

{
        return PrivchatClient(value)
    }

    override fun read(buf: ByteBuffer):

PrivchatClient

{
        // The Rust code always writes pointers as 8 bytes, and will
        // fail to compile if they don't fit.
        return lift(buf.getLong().toPointer())
    }

    override fun allocationSize(value:

PrivchatClient

) = 8UL

    override fun write(value:

PrivchatClient

, buf: ByteBuffer) {
        // The Rust code always expects pointers written as 8 bytes,
        // and will fail to compile if they don't fit.
        buf.putLong(getPointerNativeValue(lower(value)))
    }
}
    


// The cleaner interface for Object finalization code to run.
// This is the entry point to any implementation that we're using.
//
// The cleaner registers objects and returns cleanables, so now we are
// defining a `UniffiCleaner` with a `UniffiClenaer.Cleanable` to abstract the
// different implmentations available at compile time.
interface UniffiCleaner {
    interface Cleanable {
        fun clean()
    }

    fun register(value: Any, cleanUpTask: Runnable): UniffiCleaner.Cleanable

    companion object
}

private class NativeCleaner : UniffiCleaner {
    override fun register(value: Any, cleanUpTask: Runnable): UniffiCleaner.Cleanable =
        UniffiNativeCleanable(OnceRunnable(cleanUpTask))
}

private class UniffiNativeCleanable(val cleanUpTask: Runnable) : UniffiCleaner.Cleanable {
    @OptIn(ExperimentalNativeApi::class)
    private val cleaner = createCleaner(cleanUpTask) {
        it.run()
    }
    
    override fun clean() {
        cleanUpTask.run()
    }
}

private class OnceRunnable(val task: Runnable): Runnable {
    private val didRun: AtomicBoolean = atomic(false)

    override fun run() {
        if (didRun.compareAndSet(false, true)) {
            task.run()
        }
    }
}

private fun UniffiCleaner.Companion.create(): UniffiCleaner =
    NativeCleaner()




object FfiConverterTypeAccountPrivacyUpdateInput: FfiConverterRustBuffer<AccountPrivacyUpdateInput> {
    override fun read(buf: ByteBuffer): AccountPrivacyUpdateInput {
        return AccountPrivacyUpdateInput(
            FfiConverterOptionalBoolean.read(buf),
            FfiConverterOptionalBoolean.read(buf),
            FfiConverterOptionalBoolean.read(buf),
            FfiConverterOptionalBoolean.read(buf),
            FfiConverterOptionalBoolean.read(buf),
            FfiConverterOptionalBoolean.read(buf),
            FfiConverterOptionalBoolean.read(buf),
        )
    }

    override fun allocationSize(value: AccountPrivacyUpdateInput) = (
            FfiConverterOptionalBoolean.allocationSize(value.`allowAddByGroup`) +
            FfiConverterOptionalBoolean.allocationSize(value.`allowSearchByPhone`) +
            FfiConverterOptionalBoolean.allocationSize(value.`allowSearchByUsername`) +
            FfiConverterOptionalBoolean.allocationSize(value.`allowSearchByEmail`) +
            FfiConverterOptionalBoolean.allocationSize(value.`allowSearchByQrcode`) +
            FfiConverterOptionalBoolean.allocationSize(value.`allowViewByNonFriend`) +
            FfiConverterOptionalBoolean.allocationSize(value.`allowReceiveMessageFromNonFriend`)
    )

    override fun write(value: AccountPrivacyUpdateInput, buf: ByteBuffer) {
            FfiConverterOptionalBoolean.write(value.`allowAddByGroup`, buf)
            FfiConverterOptionalBoolean.write(value.`allowSearchByPhone`, buf)
            FfiConverterOptionalBoolean.write(value.`allowSearchByUsername`, buf)
            FfiConverterOptionalBoolean.write(value.`allowSearchByEmail`, buf)
            FfiConverterOptionalBoolean.write(value.`allowSearchByQrcode`, buf)
            FfiConverterOptionalBoolean.write(value.`allowViewByNonFriend`, buf)
            FfiConverterOptionalBoolean.write(value.`allowReceiveMessageFromNonFriend`, buf)
    }
}




object FfiConverterTypeAccountSearchResultView: FfiConverterRustBuffer<AccountSearchResultView> {
    override fun read(buf: ByteBuffer): AccountSearchResultView {
        return AccountSearchResultView(
            FfiConverterSequenceTypeSearchUserEntry.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: AccountSearchResultView) = (
            FfiConverterSequenceTypeSearchUserEntry.allocationSize(value.`users`) +
            FfiConverterULong.allocationSize(value.`total`) +
            FfiConverterString.allocationSize(value.`query`)
    )

    override fun write(value: AccountSearchResultView, buf: ByteBuffer) {
            FfiConverterSequenceTypeSearchUserEntry.write(value.`users`, buf)
            FfiConverterULong.write(value.`total`, buf)
            FfiConverterString.write(value.`query`, buf)
    }
}




object FfiConverterTypeAccountUserDetailView: FfiConverterRustBuffer<AccountUserDetailView> {
    override fun read(buf: ByteBuffer): AccountUserDetailView {
        return AccountUserDetailView(
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterShort.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: AccountUserDetailView) = (
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterString.allocationSize(value.`username`) +
            FfiConverterString.allocationSize(value.`nickname`) +
            FfiConverterOptionalString.allocationSize(value.`avatarUrl`) +
            FfiConverterOptionalString.allocationSize(value.`phone`) +
            FfiConverterOptionalString.allocationSize(value.`email`) +
            FfiConverterShort.allocationSize(value.`userType`) +
            FfiConverterBoolean.allocationSize(value.`isFriend`) +
            FfiConverterBoolean.allocationSize(value.`canSendMessage`) +
            FfiConverterString.allocationSize(value.`sourceType`) +
            FfiConverterString.allocationSize(value.`sourceId`)
    )

    override fun write(value: AccountUserDetailView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterString.write(value.`username`, buf)
            FfiConverterString.write(value.`nickname`, buf)
            FfiConverterOptionalString.write(value.`avatarUrl`, buf)
            FfiConverterOptionalString.write(value.`phone`, buf)
            FfiConverterOptionalString.write(value.`email`, buf)
            FfiConverterShort.write(value.`userType`, buf)
            FfiConverterBoolean.write(value.`isFriend`, buf)
            FfiConverterBoolean.write(value.`canSendMessage`, buf)
            FfiConverterString.write(value.`sourceType`, buf)
            FfiConverterString.write(value.`sourceId`, buf)
    }
}




object FfiConverterTypeAccountUserShareCardView: FfiConverterRustBuffer<AccountUserShareCardView> {
    override fun read(buf: ByteBuffer): AccountUserShareCardView {
        return AccountUserShareCardView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: AccountUserShareCardView) = (
            FfiConverterString.allocationSize(value.`shareKey`) +
            FfiConverterString.allocationSize(value.`shareUrl`) +
            FfiConverterOptionalString.allocationSize(value.`expireAt`)
    )

    override fun write(value: AccountUserShareCardView, buf: ByteBuffer) {
            FfiConverterString.write(value.`shareKey`, buf)
            FfiConverterString.write(value.`shareUrl`, buf)
            FfiConverterOptionalString.write(value.`expireAt`, buf)
    }
}




object FfiConverterTypeAccountUserUpdateInput: FfiConverterRustBuffer<AccountUserUpdateInput> {
    override fun read(buf: ByteBuffer): AccountUserUpdateInput {
        return AccountUserUpdateInput(
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: AccountUserUpdateInput) = (
            FfiConverterOptionalString.allocationSize(value.`displayName`) +
            FfiConverterOptionalString.allocationSize(value.`avatarUrl`) +
            FfiConverterOptionalString.allocationSize(value.`bio`)
    )

    override fun write(value: AccountUserUpdateInput, buf: ByteBuffer) {
            FfiConverterOptionalString.write(value.`displayName`, buf)
            FfiConverterOptionalString.write(value.`avatarUrl`, buf)
            FfiConverterOptionalString.write(value.`bio`, buf)
    }
}




object FfiConverterTypeAuthRefreshInput: FfiConverterRustBuffer<AuthRefreshInput> {
    override fun read(buf: ByteBuffer): AuthRefreshInput {
        return AuthRefreshInput(
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: AuthRefreshInput) = (
            FfiConverterString.allocationSize(value.`refreshToken`) +
            FfiConverterOptionalString.allocationSize(value.`deviceId`)
    )

    override fun write(value: AuthRefreshInput, buf: ByteBuffer) {
            FfiConverterString.write(value.`refreshToken`, buf)
            FfiConverterOptionalString.write(value.`deviceId`, buf)
    }
}




object FfiConverterTypeBatchGetChannelPtsInput: FfiConverterRustBuffer<BatchGetChannelPtsInput> {
    override fun read(buf: ByteBuffer): BatchGetChannelPtsInput {
        return BatchGetChannelPtsInput(
            FfiConverterSequenceTypeGetChannelPtsInput.read(buf),
        )
    }

    override fun allocationSize(value: BatchGetChannelPtsInput) = (
            FfiConverterSequenceTypeGetChannelPtsInput.allocationSize(value.`channels`)
    )

    override fun write(value: BatchGetChannelPtsInput, buf: ByteBuffer) {
            FfiConverterSequenceTypeGetChannelPtsInput.write(value.`channels`, buf)
    }
}




object FfiConverterTypeBatchGetChannelPtsView: FfiConverterRustBuffer<BatchGetChannelPtsView> {
    override fun read(buf: ByteBuffer): BatchGetChannelPtsView {
        return BatchGetChannelPtsView(
            FfiConverterSequenceTypeChannelPtsView.read(buf),
        )
    }

    override fun allocationSize(value: BatchGetChannelPtsView) = (
            FfiConverterSequenceTypeChannelPtsView.allocationSize(value.`channels`)
    )

    override fun write(value: BatchGetChannelPtsView, buf: ByteBuffer) {
            FfiConverterSequenceTypeChannelPtsView.write(value.`channels`, buf)
    }
}




object FfiConverterTypeBlacklistCheckResult: FfiConverterRustBuffer<BlacklistCheckResult> {
    override fun read(buf: ByteBuffer): BlacklistCheckResult {
        return BlacklistCheckResult(
            FfiConverterBoolean.read(buf),
        )
    }

    override fun allocationSize(value: BlacklistCheckResult) = (
            FfiConverterBoolean.allocationSize(value.`isBlocked`)
    )

    override fun write(value: BlacklistCheckResult, buf: ByteBuffer) {
            FfiConverterBoolean.write(value.`isBlocked`, buf)
    }
}




object FfiConverterTypeChannelBroadcastCreateInput: FfiConverterRustBuffer<ChannelBroadcastCreateInput> {
    override fun read(buf: ByteBuffer): ChannelBroadcastCreateInput {
        return ChannelBroadcastCreateInput(
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: ChannelBroadcastCreateInput) = (
            FfiConverterString.allocationSize(value.`name`) +
            FfiConverterOptionalString.allocationSize(value.`description`) +
            FfiConverterOptionalString.allocationSize(value.`avatarUrl`)
    )

    override fun write(value: ChannelBroadcastCreateInput, buf: ByteBuffer) {
            FfiConverterString.write(value.`name`, buf)
            FfiConverterOptionalString.write(value.`description`, buf)
            FfiConverterOptionalString.write(value.`avatarUrl`, buf)
    }
}




object FfiConverterTypeChannelBroadcastCreateView: FfiConverterRustBuffer<ChannelBroadcastCreateView> {
    override fun read(buf: ByteBuffer): ChannelBroadcastCreateView {
        return ChannelBroadcastCreateView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: ChannelBroadcastCreateView) = (
            FfiConverterString.allocationSize(value.`status`) +
            FfiConverterString.allocationSize(value.`action`) +
            FfiConverterString.allocationSize(value.`timestamp`)
    )

    override fun write(value: ChannelBroadcastCreateView, buf: ByteBuffer) {
            FfiConverterString.write(value.`status`, buf)
            FfiConverterString.write(value.`action`, buf)
            FfiConverterString.write(value.`timestamp`, buf)
    }
}




object FfiConverterTypeChannelBroadcastListInput: FfiConverterRustBuffer<ChannelBroadcastListInput> {
    override fun read(buf: ByteBuffer): ChannelBroadcastListInput {
        return ChannelBroadcastListInput(
            FfiConverterOptionalUInt.read(buf),
            FfiConverterOptionalUInt.read(buf),
        )
    }

    override fun allocationSize(value: ChannelBroadcastListInput) = (
            FfiConverterOptionalUInt.allocationSize(value.`page`) +
            FfiConverterOptionalUInt.allocationSize(value.`pageSize`)
    )

    override fun write(value: ChannelBroadcastListInput, buf: ByteBuffer) {
            FfiConverterOptionalUInt.write(value.`page`, buf)
            FfiConverterOptionalUInt.write(value.`pageSize`, buf)
    }
}




object FfiConverterTypeChannelBroadcastListView: FfiConverterRustBuffer<ChannelBroadcastListView> {
    override fun read(buf: ByteBuffer): ChannelBroadcastListView {
        return ChannelBroadcastListView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: ChannelBroadcastListView) = (
            FfiConverterString.allocationSize(value.`status`) +
            FfiConverterString.allocationSize(value.`action`) +
            FfiConverterString.allocationSize(value.`timestamp`)
    )

    override fun write(value: ChannelBroadcastListView, buf: ByteBuffer) {
            FfiConverterString.write(value.`status`, buf)
            FfiConverterString.write(value.`action`, buf)
            FfiConverterString.write(value.`timestamp`, buf)
    }
}




object FfiConverterTypeChannelBroadcastSubscribeInput: FfiConverterRustBuffer<ChannelBroadcastSubscribeInput> {
    override fun read(buf: ByteBuffer): ChannelBroadcastSubscribeInput {
        return ChannelBroadcastSubscribeInput(
            FfiConverterULong.read(buf),
        )
    }

    override fun allocationSize(value: ChannelBroadcastSubscribeInput) = (
            FfiConverterULong.allocationSize(value.`channelId`)
    )

    override fun write(value: ChannelBroadcastSubscribeInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
    }
}




object FfiConverterTypeChannelContentListInput: FfiConverterRustBuffer<ChannelContentListInput> {
    override fun read(buf: ByteBuffer): ChannelContentListInput {
        return ChannelContentListInput(
            FfiConverterULong.read(buf),
            FfiConverterOptionalUInt.read(buf),
            FfiConverterOptionalUInt.read(buf),
        )
    }

    override fun allocationSize(value: ChannelContentListInput) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterOptionalUInt.allocationSize(value.`page`) +
            FfiConverterOptionalUInt.allocationSize(value.`pageSize`)
    )

    override fun write(value: ChannelContentListInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterOptionalUInt.write(value.`page`, buf)
            FfiConverterOptionalUInt.write(value.`pageSize`, buf)
    }
}




object FfiConverterTypeChannelContentListView: FfiConverterRustBuffer<ChannelContentListView> {
    override fun read(buf: ByteBuffer): ChannelContentListView {
        return ChannelContentListView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: ChannelContentListView) = (
            FfiConverterString.allocationSize(value.`status`) +
            FfiConverterString.allocationSize(value.`action`) +
            FfiConverterString.allocationSize(value.`timestamp`)
    )

    override fun write(value: ChannelContentListView, buf: ByteBuffer) {
            FfiConverterString.write(value.`status`, buf)
            FfiConverterString.write(value.`action`, buf)
            FfiConverterString.write(value.`timestamp`, buf)
    }
}




object FfiConverterTypeChannelContentPublishInput: FfiConverterRustBuffer<ChannelContentPublishInput> {
    override fun read(buf: ByteBuffer): ChannelContentPublishInput {
        return ChannelContentPublishInput(
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: ChannelContentPublishInput) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterString.allocationSize(value.`content`) +
            FfiConverterOptionalString.allocationSize(value.`title`) +
            FfiConverterOptionalString.allocationSize(value.`contentType`)
    )

    override fun write(value: ChannelContentPublishInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterString.write(value.`content`, buf)
            FfiConverterOptionalString.write(value.`title`, buf)
            FfiConverterOptionalString.write(value.`contentType`, buf)
    }
}




object FfiConverterTypeChannelContentPublishView: FfiConverterRustBuffer<ChannelContentPublishView> {
    override fun read(buf: ByteBuffer): ChannelContentPublishView {
        return ChannelContentPublishView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: ChannelContentPublishView) = (
            FfiConverterString.allocationSize(value.`status`) +
            FfiConverterString.allocationSize(value.`action`) +
            FfiConverterString.allocationSize(value.`timestamp`)
    )

    override fun write(value: ChannelContentPublishView, buf: ByteBuffer) {
            FfiConverterString.write(value.`status`, buf)
            FfiConverterString.write(value.`action`, buf)
            FfiConverterString.write(value.`timestamp`, buf)
    }
}




object FfiConverterTypeChannelPtsView: FfiConverterRustBuffer<ChannelPtsView> {
    override fun read(buf: ByteBuffer): ChannelPtsView {
        return ChannelPtsView(
            FfiConverterULong.read(buf),
            FfiConverterUByte.read(buf),
            FfiConverterULong.read(buf),
        )
    }

    override fun allocationSize(value: ChannelPtsView) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterUByte.allocationSize(value.`channelType`) +
            FfiConverterULong.allocationSize(value.`currentPts`)
    )

    override fun write(value: ChannelPtsView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterUByte.write(value.`channelType`, buf)
            FfiConverterULong.write(value.`currentPts`, buf)
    }
}




object FfiConverterTypeChannelSyncState: FfiConverterRustBuffer<ChannelSyncState> {
    override fun read(buf: ByteBuffer): ChannelSyncState {
        return ChannelSyncState(
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterInt.read(buf),
        )
    }

    override fun allocationSize(value: ChannelSyncState) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterInt.allocationSize(value.`unread`)
    )

    override fun write(value: ChannelSyncState, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterInt.write(value.`unread`, buf)
    }
}




object FfiConverterTypeConnectionSummary: FfiConverterRustBuffer<ConnectionSummary> {
    override fun read(buf: ByteBuffer): ConnectionSummary {
        return ConnectionSummary(
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
        )
    }

    override fun allocationSize(value: ConnectionSummary) = (
            FfiConverterString.allocationSize(value.`state`) +
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterBoolean.allocationSize(value.`bootstrapCompleted`) +
            FfiConverterBoolean.allocationSize(value.`appInBackground`) +
            FfiConverterBoolean.allocationSize(value.`supervisedSyncRunning`) +
            FfiConverterBoolean.allocationSize(value.`sendQueueEnabled`) +
            FfiConverterULong.allocationSize(value.`eventPollCount`) +
            FfiConverterBoolean.allocationSize(value.`lifecycleHookRegistered`) +
            FfiConverterBoolean.allocationSize(value.`transportDisconnectListenerStarted`) +
            FfiConverterBoolean.allocationSize(value.`onConnectionStateChangedRegistered`) +
            FfiConverterBoolean.allocationSize(value.`onMessageReceivedRegistered`) +
            FfiConverterBoolean.allocationSize(value.`onReactionChangedRegistered`) +
            FfiConverterBoolean.allocationSize(value.`onTypingIndicatorRegistered`) +
            FfiConverterBoolean.allocationSize(value.`videoProcessHookRegistered`)
    )

    override fun write(value: ConnectionSummary, buf: ByteBuffer) {
            FfiConverterString.write(value.`state`, buf)
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterBoolean.write(value.`bootstrapCompleted`, buf)
            FfiConverterBoolean.write(value.`appInBackground`, buf)
            FfiConverterBoolean.write(value.`supervisedSyncRunning`, buf)
            FfiConverterBoolean.write(value.`sendQueueEnabled`, buf)
            FfiConverterULong.write(value.`eventPollCount`, buf)
            FfiConverterBoolean.write(value.`lifecycleHookRegistered`, buf)
            FfiConverterBoolean.write(value.`transportDisconnectListenerStarted`, buf)
            FfiConverterBoolean.write(value.`onConnectionStateChangedRegistered`, buf)
            FfiConverterBoolean.write(value.`onMessageReceivedRegistered`, buf)
            FfiConverterBoolean.write(value.`onReactionChangedRegistered`, buf)
            FfiConverterBoolean.write(value.`onTypingIndicatorRegistered`, buf)
            FfiConverterBoolean.write(value.`videoProcessHookRegistered`, buf)
    }
}




object FfiConverterTypeDeviceInfoView: FfiConverterRustBuffer<DeviceInfoView> {
    override fun read(buf: ByteBuffer): DeviceInfoView {
        return DeviceInfoView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
        )
    }

    override fun allocationSize(value: DeviceInfoView) = (
            FfiConverterString.allocationSize(value.`deviceId`) +
            FfiConverterString.allocationSize(value.`deviceName`) +
            FfiConverterBoolean.allocationSize(value.`isCurrent`) +
            FfiConverterBoolean.allocationSize(value.`appInBackground`)
    )

    override fun write(value: DeviceInfoView, buf: ByteBuffer) {
            FfiConverterString.write(value.`deviceId`, buf)
            FfiConverterString.write(value.`deviceName`, buf)
            FfiConverterBoolean.write(value.`isCurrent`, buf)
            FfiConverterBoolean.write(value.`appInBackground`, buf)
    }
}




object FfiConverterTypeDevicePushInfoView: FfiConverterRustBuffer<DevicePushInfoView> {
    override fun read(buf: ByteBuffer): DevicePushInfoView {
        return DevicePushInfoView(
            FfiConverterString.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: DevicePushInfoView) = (
            FfiConverterString.allocationSize(value.`deviceId`) +
            FfiConverterBoolean.allocationSize(value.`apnsArmed`) +
            FfiConverterBoolean.allocationSize(value.`connected`) +
            FfiConverterString.allocationSize(value.`platform`) +
            FfiConverterString.allocationSize(value.`vendor`)
    )

    override fun write(value: DevicePushInfoView, buf: ByteBuffer) {
            FfiConverterString.write(value.`deviceId`, buf)
            FfiConverterBoolean.write(value.`apnsArmed`, buf)
            FfiConverterBoolean.write(value.`connected`, buf)
            FfiConverterString.write(value.`platform`, buf)
            FfiConverterString.write(value.`vendor`, buf)
    }
}




object FfiConverterTypeDevicePushStatusView: FfiConverterRustBuffer<DevicePushStatusView> {
    override fun read(buf: ByteBuffer): DevicePushStatusView {
        return DevicePushStatusView(
            FfiConverterSequenceTypeDevicePushInfoView.read(buf),
            FfiConverterBoolean.read(buf),
        )
    }

    override fun allocationSize(value: DevicePushStatusView) = (
            FfiConverterSequenceTypeDevicePushInfoView.allocationSize(value.`devices`) +
            FfiConverterBoolean.allocationSize(value.`userPushEnabled`)
    )

    override fun write(value: DevicePushStatusView, buf: ByteBuffer) {
            FfiConverterSequenceTypeDevicePushInfoView.write(value.`devices`, buf)
            FfiConverterBoolean.write(value.`userPushEnabled`, buf)
    }
}




object FfiConverterTypeDevicePushUpdateView: FfiConverterRustBuffer<DevicePushUpdateView> {
    override fun read(buf: ByteBuffer): DevicePushUpdateView {
        return DevicePushUpdateView(
            FfiConverterString.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
        )
    }

    override fun allocationSize(value: DevicePushUpdateView) = (
            FfiConverterString.allocationSize(value.`deviceId`) +
            FfiConverterBoolean.allocationSize(value.`apnsArmed`) +
            FfiConverterBoolean.allocationSize(value.`userPushEnabled`)
    )

    override fun write(value: DevicePushUpdateView, buf: ByteBuffer) {
            FfiConverterString.write(value.`deviceId`, buf)
            FfiConverterBoolean.write(value.`apnsArmed`, buf)
            FfiConverterBoolean.write(value.`userPushEnabled`, buf)
    }
}




object FfiConverterTypeDirectChannelResult: FfiConverterRustBuffer<DirectChannelResult> {
    override fun read(buf: ByteBuffer): DirectChannelResult {
        return DirectChannelResult(
            FfiConverterULong.read(buf),
            FfiConverterBoolean.read(buf),
        )
    }

    override fun allocationSize(value: DirectChannelResult) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterBoolean.allocationSize(value.`created`)
    )

    override fun write(value: DirectChannelResult, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterBoolean.write(value.`created`, buf)
    }
}




object FfiConverterTypeEventConfigView: FfiConverterRustBuffer<EventConfigView> {
    override fun read(buf: ByteBuffer): EventConfigView {
        return EventConfigView(
            FfiConverterUInt.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
        )
    }

    override fun allocationSize(value: EventConfigView) = (
            FfiConverterUInt.allocationSize(value.`broadcastCapacity`) +
            FfiConverterString.allocationSize(value.`pollingApi`) +
            FfiConverterString.allocationSize(value.`pollingEnvelopeApi`) +
            FfiConverterULong.allocationSize(value.`eventPollCount`) +
            FfiConverterULong.allocationSize(value.`sequenceCursor`) +
            FfiConverterString.allocationSize(value.`replayApi`) +
            FfiConverterULong.allocationSize(value.`historyLimit`)
    )

    override fun write(value: EventConfigView, buf: ByteBuffer) {
            FfiConverterUInt.write(value.`broadcastCapacity`, buf)
            FfiConverterString.write(value.`pollingApi`, buf)
            FfiConverterString.write(value.`pollingEnvelopeApi`, buf)
            FfiConverterULong.write(value.`eventPollCount`, buf)
            FfiConverterULong.write(value.`sequenceCursor`, buf)
            FfiConverterString.write(value.`replayApi`, buf)
            FfiConverterULong.write(value.`historyLimit`, buf)
    }
}




object FfiConverterTypeFileQueueRef: FfiConverterRustBuffer<FileQueueRef> {
    override fun read(buf: ByteBuffer): FileQueueRef {
        return FileQueueRef(
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
        )
    }

    override fun allocationSize(value: FileQueueRef) = (
            FfiConverterULong.allocationSize(value.`queueIndex`) +
            FfiConverterULong.allocationSize(value.`messageId`)
    )

    override fun write(value: FileQueueRef, buf: ByteBuffer) {
            FfiConverterULong.write(value.`queueIndex`, buf)
            FfiConverterULong.write(value.`messageId`, buf)
    }
}




object FfiConverterTypeFileRequestUploadTokenInput: FfiConverterRustBuffer<FileRequestUploadTokenInput> {
    override fun read(buf: ByteBuffer): FileRequestUploadTokenInput {
        return FileRequestUploadTokenInput(
            FfiConverterOptionalString.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: FileRequestUploadTokenInput) = (
            FfiConverterOptionalString.allocationSize(value.`filename`) +
            FfiConverterLong.allocationSize(value.`fileSize`) +
            FfiConverterString.allocationSize(value.`mimeType`) +
            FfiConverterString.allocationSize(value.`fileType`) +
            FfiConverterString.allocationSize(value.`businessType`)
    )

    override fun write(value: FileRequestUploadTokenInput, buf: ByteBuffer) {
            FfiConverterOptionalString.write(value.`filename`, buf)
            FfiConverterLong.write(value.`fileSize`, buf)
            FfiConverterString.write(value.`mimeType`, buf)
            FfiConverterString.write(value.`fileType`, buf)
            FfiConverterString.write(value.`businessType`, buf)
    }
}




object FfiConverterTypeFileRequestUploadTokenView: FfiConverterRustBuffer<FileRequestUploadTokenView> {
    override fun read(buf: ByteBuffer): FileRequestUploadTokenView {
        return FileRequestUploadTokenView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: FileRequestUploadTokenView) = (
            FfiConverterString.allocationSize(value.`token`) +
            FfiConverterString.allocationSize(value.`uploadUrl`) +
            FfiConverterString.allocationSize(value.`fileId`)
    )

    override fun write(value: FileRequestUploadTokenView, buf: ByteBuffer) {
            FfiConverterString.write(value.`token`, buf)
            FfiConverterString.write(value.`uploadUrl`, buf)
            FfiConverterString.write(value.`fileId`, buf)
    }
}




object FfiConverterTypeFileUploadCallbackInput: FfiConverterRustBuffer<FileUploadCallbackInput> {
    override fun read(buf: ByteBuffer): FileUploadCallbackInput {
        return FileUploadCallbackInput(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: FileUploadCallbackInput) = (
            FfiConverterString.allocationSize(value.`fileId`) +
            FfiConverterString.allocationSize(value.`status`)
    )

    override fun write(value: FileUploadCallbackInput, buf: ByteBuffer) {
            FfiConverterString.write(value.`fileId`, buf)
            FfiConverterString.write(value.`status`, buf)
    }
}




object FfiConverterTypeFriendPendingEntry: FfiConverterRustBuffer<FriendPendingEntry> {
    override fun read(buf: ByteBuffer): FriendPendingEntry {
        return FriendPendingEntry(
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: FriendPendingEntry) = (
            FfiConverterULong.allocationSize(value.`fromUserId`) +
            FfiConverterOptionalString.allocationSize(value.`message`) +
            FfiConverterString.allocationSize(value.`createdAt`)
    )

    override fun write(value: FriendPendingEntry, buf: ByteBuffer) {
            FfiConverterULong.write(value.`fromUserId`, buf)
            FfiConverterOptionalString.write(value.`message`, buf)
            FfiConverterString.write(value.`createdAt`, buf)
    }
}




object FfiConverterTypeFriendRequestResult: FfiConverterRustBuffer<FriendRequestResult> {
    override fun read(buf: ByteBuffer): FriendRequestResult {
        return FriendRequestResult(
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: FriendRequestResult) = (
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterString.allocationSize(value.`username`) +
            FfiConverterString.allocationSize(value.`status`) +
            FfiConverterString.allocationSize(value.`addedAt`) +
            FfiConverterOptionalString.allocationSize(value.`message`)
    )

    override fun write(value: FriendRequestResult, buf: ByteBuffer) {
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterString.write(value.`username`, buf)
            FfiConverterString.write(value.`status`, buf)
            FfiConverterString.write(value.`addedAt`, buf)
            FfiConverterOptionalString.write(value.`message`, buf)
    }
}




object FfiConverterTypeGetChannelPtsInput: FfiConverterRustBuffer<GetChannelPtsInput> {
    override fun read(buf: ByteBuffer): GetChannelPtsInput {
        return GetChannelPtsInput(
            FfiConverterULong.read(buf),
            FfiConverterUByte.read(buf),
        )
    }

    override fun allocationSize(value: GetChannelPtsInput) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterUByte.allocationSize(value.`channelType`)
    )

    override fun write(value: GetChannelPtsInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterUByte.write(value.`channelType`, buf)
    }
}




object FfiConverterTypeGetDifferenceCommitView: FfiConverterRustBuffer<GetDifferenceCommitView> {
    override fun read(buf: ByteBuffer): GetDifferenceCommitView {
        return GetDifferenceCommitView(
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterOptionalULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterUByte.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: GetDifferenceCommitView) = (
            FfiConverterULong.allocationSize(value.`pts`) +
            FfiConverterULong.allocationSize(value.`serverMsgId`) +
            FfiConverterOptionalULong.allocationSize(value.`localMessageId`) +
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterUByte.allocationSize(value.`channelType`) +
            FfiConverterString.allocationSize(value.`messageType`) +
            FfiConverterString.allocationSize(value.`contentJson`) +
            FfiConverterLong.allocationSize(value.`serverTimestamp`) +
            FfiConverterULong.allocationSize(value.`senderId`) +
            FfiConverterOptionalString.allocationSize(value.`senderInfoJson`)
    )

    override fun write(value: GetDifferenceCommitView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`pts`, buf)
            FfiConverterULong.write(value.`serverMsgId`, buf)
            FfiConverterOptionalULong.write(value.`localMessageId`, buf)
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterUByte.write(value.`channelType`, buf)
            FfiConverterString.write(value.`messageType`, buf)
            FfiConverterString.write(value.`contentJson`, buf)
            FfiConverterLong.write(value.`serverTimestamp`, buf)
            FfiConverterULong.write(value.`senderId`, buf)
            FfiConverterOptionalString.write(value.`senderInfoJson`, buf)
    }
}




object FfiConverterTypeGetDifferenceInput: FfiConverterRustBuffer<GetDifferenceInput> {
    override fun read(buf: ByteBuffer): GetDifferenceInput {
        return GetDifferenceInput(
            FfiConverterULong.read(buf),
            FfiConverterUByte.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterOptionalUInt.read(buf),
        )
    }

    override fun allocationSize(value: GetDifferenceInput) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterUByte.allocationSize(value.`channelType`) +
            FfiConverterULong.allocationSize(value.`lastPts`) +
            FfiConverterOptionalUInt.allocationSize(value.`limit`)
    )

    override fun write(value: GetDifferenceInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterUByte.write(value.`channelType`, buf)
            FfiConverterULong.write(value.`lastPts`, buf)
            FfiConverterOptionalUInt.write(value.`limit`, buf)
    }
}




object FfiConverterTypeGetDifferenceView: FfiConverterRustBuffer<GetDifferenceView> {
    override fun read(buf: ByteBuffer): GetDifferenceView {
        return GetDifferenceView(
            FfiConverterSequenceTypeGetDifferenceCommitView.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterBoolean.read(buf),
        )
    }

    override fun allocationSize(value: GetDifferenceView) = (
            FfiConverterSequenceTypeGetDifferenceCommitView.allocationSize(value.`commits`) +
            FfiConverterULong.allocationSize(value.`currentPts`) +
            FfiConverterBoolean.allocationSize(value.`hasMore`)
    )

    override fun write(value: GetDifferenceView, buf: ByteBuffer) {
            FfiConverterSequenceTypeGetDifferenceCommitView.write(value.`commits`, buf)
            FfiConverterULong.write(value.`currentPts`, buf)
            FfiConverterBoolean.write(value.`hasMore`, buf)
    }
}




object FfiConverterTypeGroupApprovalItemView: FfiConverterRustBuffer<GroupApprovalItemView> {
    override fun read(buf: ByteBuffer): GroupApprovalItemView {
        return GroupApprovalItemView(
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: GroupApprovalItemView) = (
            FfiConverterString.allocationSize(value.`requestId`) +
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterOptionalString.allocationSize(value.`inviterId`) +
            FfiConverterOptionalString.allocationSize(value.`qrCodeId`) +
            FfiConverterOptionalString.allocationSize(value.`message`) +
            FfiConverterString.allocationSize(value.`createdAt`) +
            FfiConverterOptionalString.allocationSize(value.`expiresAt`)
    )

    override fun write(value: GroupApprovalItemView, buf: ByteBuffer) {
            FfiConverterString.write(value.`requestId`, buf)
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterOptionalString.write(value.`inviterId`, buf)
            FfiConverterOptionalString.write(value.`qrCodeId`, buf)
            FfiConverterOptionalString.write(value.`message`, buf)
            FfiConverterString.write(value.`createdAt`, buf)
            FfiConverterOptionalString.write(value.`expiresAt`, buf)
    }
}




object FfiConverterTypeGroupApprovalListView: FfiConverterRustBuffer<GroupApprovalListView> {
    override fun read(buf: ByteBuffer): GroupApprovalListView {
        return GroupApprovalListView(
            FfiConverterString.read(buf),
            FfiConverterSequenceTypeGroupApprovalItemView.read(buf),
            FfiConverterULong.read(buf),
        )
    }

    override fun allocationSize(value: GroupApprovalListView) = (
            FfiConverterString.allocationSize(value.`groupId`) +
            FfiConverterSequenceTypeGroupApprovalItemView.allocationSize(value.`approvals`) +
            FfiConverterULong.allocationSize(value.`total`)
    )

    override fun write(value: GroupApprovalListView, buf: ByteBuffer) {
            FfiConverterString.write(value.`groupId`, buf)
            FfiConverterSequenceTypeGroupApprovalItemView.write(value.`approvals`, buf)
            FfiConverterULong.write(value.`total`, buf)
    }
}




object FfiConverterTypeGroupCreateResult: FfiConverterRustBuffer<GroupCreateResult> {
    override fun read(buf: ByteBuffer): GroupCreateResult {
        return GroupCreateResult(
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterUInt.read(buf),
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
        )
    }

    override fun allocationSize(value: GroupCreateResult) = (
            FfiConverterULong.allocationSize(value.`groupId`) +
            FfiConverterString.allocationSize(value.`name`) +
            FfiConverterOptionalString.allocationSize(value.`description`) +
            FfiConverterUInt.allocationSize(value.`memberCount`) +
            FfiConverterString.allocationSize(value.`createdAt`) +
            FfiConverterULong.allocationSize(value.`creatorId`)
    )

    override fun write(value: GroupCreateResult, buf: ByteBuffer) {
            FfiConverterULong.write(value.`groupId`, buf)
            FfiConverterString.write(value.`name`, buf)
            FfiConverterOptionalString.write(value.`description`, buf)
            FfiConverterUInt.write(value.`memberCount`, buf)
            FfiConverterString.write(value.`createdAt`, buf)
            FfiConverterULong.write(value.`creatorId`, buf)
    }
}




object FfiConverterTypeGroupInfoView: FfiConverterRustBuffer<GroupInfoView> {
    override fun read(buf: ByteBuffer): GroupInfoView {
        return GroupInfoView(
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterUInt.read(buf),
            FfiConverterOptionalUInt.read(buf),
            FfiConverterOptionalBoolean.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: GroupInfoView) = (
            FfiConverterULong.allocationSize(value.`groupId`) +
            FfiConverterString.allocationSize(value.`name`) +
            FfiConverterOptionalString.allocationSize(value.`description`) +
            FfiConverterOptionalString.allocationSize(value.`avatarUrl`) +
            FfiConverterULong.allocationSize(value.`ownerId`) +
            FfiConverterString.allocationSize(value.`createdAt`) +
            FfiConverterString.allocationSize(value.`updatedAt`) +
            FfiConverterUInt.allocationSize(value.`memberCount`) +
            FfiConverterOptionalUInt.allocationSize(value.`messageCount`) +
            FfiConverterOptionalBoolean.allocationSize(value.`isArchived`) +
            FfiConverterOptionalString.allocationSize(value.`tagsJson`) +
            FfiConverterOptionalString.allocationSize(value.`customFieldsJson`)
    )

    override fun write(value: GroupInfoView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`groupId`, buf)
            FfiConverterString.write(value.`name`, buf)
            FfiConverterOptionalString.write(value.`description`, buf)
            FfiConverterOptionalString.write(value.`avatarUrl`, buf)
            FfiConverterULong.write(value.`ownerId`, buf)
            FfiConverterString.write(value.`createdAt`, buf)
            FfiConverterString.write(value.`updatedAt`, buf)
            FfiConverterUInt.write(value.`memberCount`, buf)
            FfiConverterOptionalUInt.write(value.`messageCount`, buf)
            FfiConverterOptionalBoolean.write(value.`isArchived`, buf)
            FfiConverterOptionalString.write(value.`tagsJson`, buf)
            FfiConverterOptionalString.write(value.`customFieldsJson`, buf)
    }
}




object FfiConverterTypeGroupMemberRemoteEntry: FfiConverterRustBuffer<GroupMemberRemoteEntry> {
    override fun read(buf: ByteBuffer): GroupMemberRemoteEntry {
        return GroupMemberRemoteEntry(
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: GroupMemberRemoteEntry) = (
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterInt.allocationSize(value.`role`) +
            FfiConverterInt.allocationSize(value.`status`) +
            FfiConverterOptionalString.allocationSize(value.`alias`) +
            FfiConverterBoolean.allocationSize(value.`isMuted`) +
            FfiConverterLong.allocationSize(value.`joinedAt`) +
            FfiConverterLong.allocationSize(value.`updatedAt`) +
            FfiConverterString.allocationSize(value.`rawJson`)
    )

    override fun write(value: GroupMemberRemoteEntry, buf: ByteBuffer) {
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterInt.write(value.`role`, buf)
            FfiConverterInt.write(value.`status`, buf)
            FfiConverterOptionalString.write(value.`alias`, buf)
            FfiConverterBoolean.write(value.`isMuted`, buf)
            FfiConverterLong.write(value.`joinedAt`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
            FfiConverterString.write(value.`rawJson`, buf)
    }
}




object FfiConverterTypeGroupMemberRemoteList: FfiConverterRustBuffer<GroupMemberRemoteList> {
    override fun read(buf: ByteBuffer): GroupMemberRemoteList {
        return GroupMemberRemoteList(
            FfiConverterSequenceTypeGroupMemberRemoteEntry.read(buf),
            FfiConverterULong.read(buf),
        )
    }

    override fun allocationSize(value: GroupMemberRemoteList) = (
            FfiConverterSequenceTypeGroupMemberRemoteEntry.allocationSize(value.`members`) +
            FfiConverterULong.allocationSize(value.`total`)
    )

    override fun write(value: GroupMemberRemoteList, buf: ByteBuffer) {
            FfiConverterSequenceTypeGroupMemberRemoteEntry.write(value.`members`, buf)
            FfiConverterULong.write(value.`total`, buf)
    }
}




object FfiConverterTypeGroupMuteAllView: FfiConverterRustBuffer<GroupMuteAllView> {
    override fun read(buf: ByteBuffer): GroupMuteAllView {
        return GroupMuteAllView(
            FfiConverterBoolean.read(buf),
            FfiConverterString.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: GroupMuteAllView) = (
            FfiConverterBoolean.allocationSize(value.`success`) +
            FfiConverterString.allocationSize(value.`groupId`) +
            FfiConverterBoolean.allocationSize(value.`allMuted`) +
            FfiConverterString.allocationSize(value.`message`) +
            FfiConverterString.allocationSize(value.`operatorId`) +
            FfiConverterString.allocationSize(value.`updatedAt`)
    )

    override fun write(value: GroupMuteAllView, buf: ByteBuffer) {
            FfiConverterBoolean.write(value.`success`, buf)
            FfiConverterString.write(value.`groupId`, buf)
            FfiConverterBoolean.write(value.`allMuted`, buf)
            FfiConverterString.write(value.`message`, buf)
            FfiConverterString.write(value.`operatorId`, buf)
            FfiConverterString.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypeGroupQrCodeGenerateView: FfiConverterRustBuffer<GroupQrCodeGenerateView> {
    override fun read(buf: ByteBuffer): GroupQrCodeGenerateView {
        return GroupQrCodeGenerateView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: GroupQrCodeGenerateView) = (
            FfiConverterString.allocationSize(value.`qrKey`) +
            FfiConverterString.allocationSize(value.`qrCode`) +
            FfiConverterOptionalString.allocationSize(value.`expireAt`) +
            FfiConverterULong.allocationSize(value.`groupId`) +
            FfiConverterString.allocationSize(value.`createdAt`)
    )

    override fun write(value: GroupQrCodeGenerateView, buf: ByteBuffer) {
            FfiConverterString.write(value.`qrKey`, buf)
            FfiConverterString.write(value.`qrCode`, buf)
            FfiConverterOptionalString.write(value.`expireAt`, buf)
            FfiConverterULong.write(value.`groupId`, buf)
            FfiConverterString.write(value.`createdAt`, buf)
    }
}




object FfiConverterTypeGroupQrCodeJoinResult: FfiConverterRustBuffer<GroupQrCodeJoinResult> {
    override fun read(buf: ByteBuffer): GroupQrCodeJoinResult {
        return GroupQrCodeJoinResult(
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalULong.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: GroupQrCodeJoinResult) = (
            FfiConverterString.allocationSize(value.`status`) +
            FfiConverterULong.allocationSize(value.`groupId`) +
            FfiConverterOptionalString.allocationSize(value.`requestId`) +
            FfiConverterOptionalString.allocationSize(value.`message`) +
            FfiConverterOptionalString.allocationSize(value.`expiresAt`) +
            FfiConverterOptionalULong.allocationSize(value.`userId`) +
            FfiConverterOptionalString.allocationSize(value.`joinedAt`)
    )

    override fun write(value: GroupQrCodeJoinResult, buf: ByteBuffer) {
            FfiConverterString.write(value.`status`, buf)
            FfiConverterULong.write(value.`groupId`, buf)
            FfiConverterOptionalString.write(value.`requestId`, buf)
            FfiConverterOptionalString.write(value.`message`, buf)
            FfiConverterOptionalString.write(value.`expiresAt`, buf)
            FfiConverterOptionalULong.write(value.`userId`, buf)
            FfiConverterOptionalString.write(value.`joinedAt`, buf)
    }
}




object FfiConverterTypeGroupRoleSetView: FfiConverterRustBuffer<GroupRoleSetView> {
    override fun read(buf: ByteBuffer): GroupRoleSetView {
        return GroupRoleSetView(
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: GroupRoleSetView) = (
            FfiConverterULong.allocationSize(value.`groupId`) +
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterString.allocationSize(value.`role`) +
            FfiConverterOptionalString.allocationSize(value.`updatedAt`)
    )

    override fun write(value: GroupRoleSetView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`groupId`, buf)
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterString.write(value.`role`, buf)
            FfiConverterOptionalString.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypeGroupSettingsUpdateInput: FfiConverterRustBuffer<GroupSettingsUpdateInput> {
    override fun read(buf: ByteBuffer): GroupSettingsUpdateInput {
        return GroupSettingsUpdateInput(
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: GroupSettingsUpdateInput) = (
            FfiConverterULong.allocationSize(value.`groupId`) +
            FfiConverterOptionalString.allocationSize(value.`name`) +
            FfiConverterOptionalString.allocationSize(value.`description`) +
            FfiConverterOptionalString.allocationSize(value.`avatarUrl`)
    )

    override fun write(value: GroupSettingsUpdateInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`groupId`, buf)
            FfiConverterOptionalString.write(value.`name`, buf)
            FfiConverterOptionalString.write(value.`description`, buf)
            FfiConverterOptionalString.write(value.`avatarUrl`, buf)
    }
}




object FfiConverterTypeGroupSettingsView: FfiConverterRustBuffer<GroupSettingsView> {
    override fun read(buf: ByteBuffer): GroupSettingsView {
        return GroupSettingsView(
            FfiConverterULong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: GroupSettingsView) = (
            FfiConverterULong.allocationSize(value.`groupId`) +
            FfiConverterBoolean.allocationSize(value.`joinNeedApproval`) +
            FfiConverterBoolean.allocationSize(value.`memberCanInvite`) +
            FfiConverterBoolean.allocationSize(value.`allMuted`) +
            FfiConverterULong.allocationSize(value.`maxMembers`) +
            FfiConverterOptionalString.allocationSize(value.`announcement`) +
            FfiConverterOptionalString.allocationSize(value.`description`) +
            FfiConverterString.allocationSize(value.`createdAt`) +
            FfiConverterString.allocationSize(value.`updatedAt`)
    )

    override fun write(value: GroupSettingsView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`groupId`, buf)
            FfiConverterBoolean.write(value.`joinNeedApproval`, buf)
            FfiConverterBoolean.write(value.`memberCanInvite`, buf)
            FfiConverterBoolean.write(value.`allMuted`, buf)
            FfiConverterULong.write(value.`maxMembers`, buf)
            FfiConverterOptionalString.write(value.`announcement`, buf)
            FfiConverterOptionalString.write(value.`description`, buf)
            FfiConverterString.write(value.`createdAt`, buf)
            FfiConverterString.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypeGroupTransferOwnerView: FfiConverterRustBuffer<GroupTransferOwnerView> {
    override fun read(buf: ByteBuffer): GroupTransferOwnerView {
        return GroupTransferOwnerView(
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: GroupTransferOwnerView) = (
            FfiConverterULong.allocationSize(value.`groupId`) +
            FfiConverterULong.allocationSize(value.`newOwnerId`) +
            FfiConverterOptionalString.allocationSize(value.`transferredAt`)
    )

    override fun write(value: GroupTransferOwnerView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`groupId`, buf)
            FfiConverterULong.write(value.`newOwnerId`, buf)
            FfiConverterOptionalString.write(value.`transferredAt`, buf)
    }
}




object FfiConverterTypeHttpClientConfigView: FfiConverterRustBuffer<HttpClientConfigView> {
    override fun read(buf: ByteBuffer): HttpClientConfigView {
        return HttpClientConfigView(
            FfiConverterULong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: HttpClientConfigView) = (
            FfiConverterULong.allocationSize(value.`connectionTimeoutSecs`) +
            FfiConverterBoolean.allocationSize(value.`tls`) +
            FfiConverterString.allocationSize(value.`scheme`)
    )

    override fun write(value: HttpClientConfigView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`connectionTimeoutSecs`, buf)
            FfiConverterBoolean.write(value.`tls`, buf)
            FfiConverterString.write(value.`scheme`, buf)
    }
}




object FfiConverterTypeLocalAccountSummary: FfiConverterRustBuffer<LocalAccountSummary> {
    override fun read(buf: ByteBuffer): LocalAccountSummary {
        return LocalAccountSummary(
            FfiConverterString.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterBoolean.read(buf),
        )
    }

    override fun allocationSize(value: LocalAccountSummary) = (
            FfiConverterString.allocationSize(value.`uid`) +
            FfiConverterLong.allocationSize(value.`createdAt`) +
            FfiConverterLong.allocationSize(value.`lastLoginAt`) +
            FfiConverterBoolean.allocationSize(value.`isActive`)
    )

    override fun write(value: LocalAccountSummary, buf: ByteBuffer) {
            FfiConverterString.write(value.`uid`, buf)
            FfiConverterLong.write(value.`createdAt`, buf)
            FfiConverterLong.write(value.`lastLoginAt`, buf)
            FfiConverterBoolean.write(value.`isActive`, buf)
    }
}




object FfiConverterTypeLoginResult: FfiConverterRustBuffer<LoginResult> {
    override fun read(buf: ByteBuffer): LoginResult {
        return LoginResult(
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: LoginResult) = (
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterString.allocationSize(value.`token`) +
            FfiConverterString.allocationSize(value.`deviceId`) +
            FfiConverterOptionalString.allocationSize(value.`refreshToken`) +
            FfiConverterString.allocationSize(value.`expiresAt`)
    )

    override fun write(value: LoginResult, buf: ByteBuffer) {
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterString.write(value.`token`, buf)
            FfiConverterString.write(value.`deviceId`, buf)
            FfiConverterOptionalString.write(value.`refreshToken`, buf)
            FfiConverterString.write(value.`expiresAt`, buf)
    }
}




object FfiConverterTypeMentionInput: FfiConverterRustBuffer<MentionInput> {
    override fun read(buf: ByteBuffer): MentionInput {
        return MentionInput(
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: MentionInput) = (
            FfiConverterULong.allocationSize(value.`messageId`) +
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterULong.allocationSize(value.`mentionedUserId`) +
            FfiConverterULong.allocationSize(value.`senderId`) +
            FfiConverterBoolean.allocationSize(value.`isMentionAll`) +
            FfiConverterLong.allocationSize(value.`createdAt`)
    )

    override fun write(value: MentionInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`messageId`, buf)
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterULong.write(value.`mentionedUserId`, buf)
            FfiConverterULong.write(value.`senderId`, buf)
            FfiConverterBoolean.write(value.`isMentionAll`, buf)
            FfiConverterLong.write(value.`createdAt`, buf)
    }
}




object FfiConverterTypeMessageHistoryItemView: FfiConverterRustBuffer<MessageHistoryItemView> {
    override fun read(buf: ByteBuffer): MessageHistoryItemView {
        return MessageHistoryItemView(
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalULong.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterOptionalLong.read(buf),
            FfiConverterOptionalULong.read(buf),
        )
    }

    override fun allocationSize(value: MessageHistoryItemView) = (
            FfiConverterULong.allocationSize(value.`messageId`) +
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterULong.allocationSize(value.`senderId`) +
            FfiConverterString.allocationSize(value.`content`) +
            FfiConverterString.allocationSize(value.`messageType`) +
            FfiConverterString.allocationSize(value.`timestamp`) +
            FfiConverterOptionalULong.allocationSize(value.`replyToMessageId`) +
            FfiConverterOptionalString.allocationSize(value.`metadataJson`) +
            FfiConverterBoolean.allocationSize(value.`revoked`) +
            FfiConverterOptionalLong.allocationSize(value.`revokedAt`) +
            FfiConverterOptionalULong.allocationSize(value.`revokedBy`)
    )

    override fun write(value: MessageHistoryItemView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`messageId`, buf)
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterULong.write(value.`senderId`, buf)
            FfiConverterString.write(value.`content`, buf)
            FfiConverterString.write(value.`messageType`, buf)
            FfiConverterString.write(value.`timestamp`, buf)
            FfiConverterOptionalULong.write(value.`replyToMessageId`, buf)
            FfiConverterOptionalString.write(value.`metadataJson`, buf)
            FfiConverterBoolean.write(value.`revoked`, buf)
            FfiConverterOptionalLong.write(value.`revokedAt`, buf)
            FfiConverterOptionalULong.write(value.`revokedBy`, buf)
    }
}




object FfiConverterTypeMessageHistoryView: FfiConverterRustBuffer<MessageHistoryView> {
    override fun read(buf: ByteBuffer): MessageHistoryView {
        return MessageHistoryView(
            FfiConverterSequenceTypeMessageHistoryItemView.read(buf),
            FfiConverterBoolean.read(buf),
        )
    }

    override fun allocationSize(value: MessageHistoryView) = (
            FfiConverterSequenceTypeMessageHistoryItemView.allocationSize(value.`messages`) +
            FfiConverterBoolean.allocationSize(value.`hasMore`)
    )

    override fun write(value: MessageHistoryView, buf: ByteBuffer) {
            FfiConverterSequenceTypeMessageHistoryItemView.write(value.`messages`, buf)
            FfiConverterBoolean.write(value.`hasMore`, buf)
    }
}




object FfiConverterTypeMessageReactionEmojiUsersView: FfiConverterRustBuffer<MessageReactionEmojiUsersView> {
    override fun read(buf: ByteBuffer): MessageReactionEmojiUsersView {
        return MessageReactionEmojiUsersView(
            FfiConverterString.read(buf),
            FfiConverterSequenceULong.read(buf),
        )
    }

    override fun allocationSize(value: MessageReactionEmojiUsersView) = (
            FfiConverterString.allocationSize(value.`emoji`) +
            FfiConverterSequenceULong.allocationSize(value.`userIds`)
    )

    override fun write(value: MessageReactionEmojiUsersView, buf: ByteBuffer) {
            FfiConverterString.write(value.`emoji`, buf)
            FfiConverterSequenceULong.write(value.`userIds`, buf)
    }
}




object FfiConverterTypeMessageReactionListView: FfiConverterRustBuffer<MessageReactionListView> {
    override fun read(buf: ByteBuffer): MessageReactionListView {
        return MessageReactionListView(
            FfiConverterBoolean.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterSequenceTypeMessageReactionEmojiUsersView.read(buf),
        )
    }

    override fun allocationSize(value: MessageReactionListView) = (
            FfiConverterBoolean.allocationSize(value.`success`) +
            FfiConverterULong.allocationSize(value.`totalCount`) +
            FfiConverterSequenceTypeMessageReactionEmojiUsersView.allocationSize(value.`reactions`)
    )

    override fun write(value: MessageReactionListView, buf: ByteBuffer) {
            FfiConverterBoolean.write(value.`success`, buf)
            FfiConverterULong.write(value.`totalCount`, buf)
            FfiConverterSequenceTypeMessageReactionEmojiUsersView.write(value.`reactions`, buf)
    }
}




object FfiConverterTypeMessageReactionStatsView: FfiConverterRustBuffer<MessageReactionStatsView> {
    override fun read(buf: ByteBuffer): MessageReactionStatsView {
        return MessageReactionStatsView(
            FfiConverterBoolean.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterSequenceTypeMessageReactionEmojiUsersView.read(buf),
        )
    }

    override fun allocationSize(value: MessageReactionStatsView) = (
            FfiConverterBoolean.allocationSize(value.`success`) +
            FfiConverterULong.allocationSize(value.`totalCount`) +
            FfiConverterSequenceTypeMessageReactionEmojiUsersView.allocationSize(value.`reactions`)
    )

    override fun write(value: MessageReactionStatsView, buf: ByteBuffer) {
            FfiConverterBoolean.write(value.`success`, buf)
            FfiConverterULong.write(value.`totalCount`, buf)
            FfiConverterSequenceTypeMessageReactionEmojiUsersView.write(value.`reactions`, buf)
    }
}




object FfiConverterTypeMessageReadListView: FfiConverterRustBuffer<MessageReadListView> {
    override fun read(buf: ByteBuffer): MessageReadListView {
        return MessageReadListView(
            FfiConverterSequenceTypeMessageReadUserView.read(buf),
            FfiConverterULong.read(buf),
        )
    }

    override fun allocationSize(value: MessageReadListView) = (
            FfiConverterSequenceTypeMessageReadUserView.allocationSize(value.`readers`) +
            FfiConverterULong.allocationSize(value.`total`)
    )

    override fun write(value: MessageReadListView, buf: ByteBuffer) {
            FfiConverterSequenceTypeMessageReadUserView.write(value.`readers`, buf)
            FfiConverterULong.write(value.`total`, buf)
    }
}




object FfiConverterTypeMessageReadStatsView: FfiConverterRustBuffer<MessageReadStatsView> {
    override fun read(buf: ByteBuffer): MessageReadStatsView {
        return MessageReadStatsView(
            FfiConverterUInt.read(buf),
            FfiConverterUInt.read(buf),
        )
    }

    override fun allocationSize(value: MessageReadStatsView) = (
            FfiConverterUInt.allocationSize(value.`readCount`) +
            FfiConverterUInt.allocationSize(value.`totalCount`)
    )

    override fun write(value: MessageReadStatsView, buf: ByteBuffer) {
            FfiConverterUInt.write(value.`readCount`, buf)
            FfiConverterUInt.write(value.`totalCount`, buf)
    }
}




object FfiConverterTypeMessageReadUserView: FfiConverterRustBuffer<MessageReadUserView> {
    override fun read(buf: ByteBuffer): MessageReadUserView {
        return MessageReadUserView(
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: MessageReadUserView) = (
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterOptionalString.allocationSize(value.`username`) +
            FfiConverterOptionalString.allocationSize(value.`nickname`) +
            FfiConverterOptionalString.allocationSize(value.`avatarUrl`) +
            FfiConverterOptionalString.allocationSize(value.`readAt`)
    )

    override fun write(value: MessageReadUserView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterOptionalString.write(value.`username`, buf)
            FfiConverterOptionalString.write(value.`nickname`, buf)
            FfiConverterOptionalString.write(value.`avatarUrl`, buf)
            FfiConverterOptionalString.write(value.`readAt`, buf)
    }
}




object FfiConverterTypeMessageUnreadCountView: FfiConverterRustBuffer<MessageUnreadCountView> {
    override fun read(buf: ByteBuffer): MessageUnreadCountView {
        return MessageUnreadCountView(
            FfiConverterInt.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: MessageUnreadCountView) = (
            FfiConverterInt.allocationSize(value.`unreadCount`) +
            FfiConverterOptionalString.allocationSize(value.`channelId`)
    )

    override fun write(value: MessageUnreadCountView, buf: ByteBuffer) {
            FfiConverterInt.write(value.`unreadCount`, buf)
            FfiConverterOptionalString.write(value.`channelId`, buf)
    }
}




object FfiConverterTypeNewMessage: FfiConverterRustBuffer<NewMessage> {
    override fun read(buf: ByteBuffer): NewMessage {
        return NewMessage(
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: NewMessage) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterULong.allocationSize(value.`fromUid`) +
            FfiConverterInt.allocationSize(value.`messageType`) +
            FfiConverterString.allocationSize(value.`content`) +
            FfiConverterString.allocationSize(value.`searchableWord`) +
            FfiConverterInt.allocationSize(value.`setting`) +
            FfiConverterString.allocationSize(value.`extra`)
    )

    override fun write(value: NewMessage, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterULong.write(value.`fromUid`, buf)
            FfiConverterInt.write(value.`messageType`, buf)
            FfiConverterString.write(value.`content`, buf)
            FfiConverterString.write(value.`searchableWord`, buf)
            FfiConverterInt.write(value.`setting`, buf)
            FfiConverterString.write(value.`extra`, buf)
    }
}




object FfiConverterTypePresenceStatsView: FfiConverterRustBuffer<PresenceStatsView> {
    override fun read(buf: ByteBuffer): PresenceStatsView {
        return PresenceStatsView(
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
        )
    }

    override fun allocationSize(value: PresenceStatsView) = (
            FfiConverterULong.allocationSize(value.`online`) +
            FfiConverterULong.allocationSize(value.`offline`) +
            FfiConverterULong.allocationSize(value.`total`)
    )

    override fun write(value: PresenceStatsView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`online`, buf)
            FfiConverterULong.write(value.`offline`, buf)
            FfiConverterULong.write(value.`total`, buf)
    }
}




object FfiConverterTypePresenceStatus: FfiConverterRustBuffer<PresenceStatus> {
    override fun read(buf: ByteBuffer): PresenceStatus {
        return PresenceStatus(
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterSequenceString.read(buf),
        )
    }

    override fun allocationSize(value: PresenceStatus) = (
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterString.allocationSize(value.`status`) +
            FfiConverterLong.allocationSize(value.`lastSeen`) +
            FfiConverterSequenceString.allocationSize(value.`onlineDevices`)
    )

    override fun write(value: PresenceStatus, buf: ByteBuffer) {
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterString.write(value.`status`, buf)
            FfiConverterLong.write(value.`lastSeen`, buf)
            FfiConverterSequenceString.write(value.`onlineDevices`, buf)
    }
}




object FfiConverterTypePrivacySettingsView: FfiConverterRustBuffer<PrivacySettingsView> {
    override fun read(buf: ByteBuffer): PrivacySettingsView {
        return PrivacySettingsView(
            FfiConverterULong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: PrivacySettingsView) = (
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterBoolean.allocationSize(value.`allowAddByGroup`) +
            FfiConverterBoolean.allocationSize(value.`allowSearchByPhone`) +
            FfiConverterBoolean.allocationSize(value.`allowSearchByUsername`) +
            FfiConverterBoolean.allocationSize(value.`allowSearchByEmail`) +
            FfiConverterBoolean.allocationSize(value.`allowSearchByQrcode`) +
            FfiConverterBoolean.allocationSize(value.`allowViewByNonFriend`) +
            FfiConverterBoolean.allocationSize(value.`allowReceiveMessageFromNonFriend`) +
            FfiConverterString.allocationSize(value.`updatedAt`)
    )

    override fun write(value: PrivacySettingsView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterBoolean.write(value.`allowAddByGroup`, buf)
            FfiConverterBoolean.write(value.`allowSearchByPhone`, buf)
            FfiConverterBoolean.write(value.`allowSearchByUsername`, buf)
            FfiConverterBoolean.write(value.`allowSearchByEmail`, buf)
            FfiConverterBoolean.write(value.`allowSearchByQrcode`, buf)
            FfiConverterBoolean.write(value.`allowViewByNonFriend`, buf)
            FfiConverterBoolean.write(value.`allowReceiveMessageFromNonFriend`, buf)
            FfiConverterString.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypePrivchatConfig: FfiConverterRustBuffer<PrivchatConfig> {
    override fun read(buf: ByteBuffer): PrivchatConfig {
        return PrivchatConfig(
            FfiConverterSequenceTypeServerEndpoint.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: PrivchatConfig) = (
            FfiConverterSequenceTypeServerEndpoint.allocationSize(value.`endpoints`) +
            FfiConverterULong.allocationSize(value.`connectionTimeoutSecs`) +
            FfiConverterString.allocationSize(value.`dataDir`)
    )

    override fun write(value: PrivchatConfig, buf: ByteBuffer) {
            FfiConverterSequenceTypeServerEndpoint.write(value.`endpoints`, buf)
            FfiConverterULong.write(value.`connectionTimeoutSecs`, buf)
            FfiConverterString.write(value.`dataDir`, buf)
    }
}




object FfiConverterTypeProfileUpdateInput: FfiConverterRustBuffer<ProfileUpdateInput> {
    override fun read(buf: ByteBuffer): ProfileUpdateInput {
        return ProfileUpdateInput(
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: ProfileUpdateInput) = (
            FfiConverterOptionalString.allocationSize(value.`displayName`) +
            FfiConverterOptionalString.allocationSize(value.`avatarUrl`) +
            FfiConverterOptionalString.allocationSize(value.`bio`)
    )

    override fun write(value: ProfileUpdateInput, buf: ByteBuffer) {
            FfiConverterOptionalString.write(value.`displayName`, buf)
            FfiConverterOptionalString.write(value.`avatarUrl`, buf)
            FfiConverterOptionalString.write(value.`bio`, buf)
    }
}




object FfiConverterTypeProfileView: FfiConverterRustBuffer<ProfileView> {
    override fun read(buf: ByteBuffer): ProfileView {
        return ProfileView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: ProfileView) = (
            FfiConverterString.allocationSize(value.`status`) +
            FfiConverterString.allocationSize(value.`action`) +
            FfiConverterString.allocationSize(value.`timestamp`)
    )

    override fun write(value: ProfileView, buf: ByteBuffer) {
            FfiConverterString.write(value.`status`, buf)
            FfiConverterString.write(value.`action`, buf)
            FfiConverterString.write(value.`timestamp`, buf)
    }
}




object FfiConverterTypeQrCodeEntryView: FfiConverterRustBuffer<QrCodeEntryView> {
    override fun read(buf: ByteBuffer): QrCodeEntryView {
        return QrCodeEntryView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterUInt.read(buf),
            FfiConverterOptionalUInt.read(buf),
            FfiConverterBoolean.read(buf),
        )
    }

    override fun allocationSize(value: QrCodeEntryView) = (
            FfiConverterString.allocationSize(value.`qrKey`) +
            FfiConverterString.allocationSize(value.`qrCode`) +
            FfiConverterString.allocationSize(value.`qrType`) +
            FfiConverterString.allocationSize(value.`targetId`) +
            FfiConverterString.allocationSize(value.`createdAt`) +
            FfiConverterOptionalString.allocationSize(value.`expireAt`) +
            FfiConverterUInt.allocationSize(value.`usedCount`) +
            FfiConverterOptionalUInt.allocationSize(value.`maxUsage`) +
            FfiConverterBoolean.allocationSize(value.`revoked`)
    )

    override fun write(value: QrCodeEntryView, buf: ByteBuffer) {
            FfiConverterString.write(value.`qrKey`, buf)
            FfiConverterString.write(value.`qrCode`, buf)
            FfiConverterString.write(value.`qrType`, buf)
            FfiConverterString.write(value.`targetId`, buf)
            FfiConverterString.write(value.`createdAt`, buf)
            FfiConverterOptionalString.write(value.`expireAt`, buf)
            FfiConverterUInt.write(value.`usedCount`, buf)
            FfiConverterOptionalUInt.write(value.`maxUsage`, buf)
            FfiConverterBoolean.write(value.`revoked`, buf)
    }
}




object FfiConverterTypeQrCodeGenerateView: FfiConverterRustBuffer<QrCodeGenerateView> {
    override fun read(buf: ByteBuffer): QrCodeGenerateView {
        return QrCodeGenerateView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalUInt.read(buf),
            FfiConverterUInt.read(buf),
        )
    }

    override fun allocationSize(value: QrCodeGenerateView) = (
            FfiConverterString.allocationSize(value.`qrKey`) +
            FfiConverterString.allocationSize(value.`qrCode`) +
            FfiConverterString.allocationSize(value.`qrType`) +
            FfiConverterULong.allocationSize(value.`targetId`) +
            FfiConverterString.allocationSize(value.`createdAt`) +
            FfiConverterOptionalString.allocationSize(value.`expireAt`) +
            FfiConverterOptionalUInt.allocationSize(value.`maxUsage`) +
            FfiConverterUInt.allocationSize(value.`usedCount`)
    )

    override fun write(value: QrCodeGenerateView, buf: ByteBuffer) {
            FfiConverterString.write(value.`qrKey`, buf)
            FfiConverterString.write(value.`qrCode`, buf)
            FfiConverterString.write(value.`qrType`, buf)
            FfiConverterULong.write(value.`targetId`, buf)
            FfiConverterString.write(value.`createdAt`, buf)
            FfiConverterOptionalString.write(value.`expireAt`, buf)
            FfiConverterOptionalUInt.write(value.`maxUsage`, buf)
            FfiConverterUInt.write(value.`usedCount`, buf)
    }
}




object FfiConverterTypeQrCodeListView: FfiConverterRustBuffer<QrCodeListView> {
    override fun read(buf: ByteBuffer): QrCodeListView {
        return QrCodeListView(
            FfiConverterSequenceTypeQrCodeEntryView.read(buf),
            FfiConverterULong.read(buf),
        )
    }

    override fun allocationSize(value: QrCodeListView) = (
            FfiConverterSequenceTypeQrCodeEntryView.allocationSize(value.`qrKeys`) +
            FfiConverterULong.allocationSize(value.`total`)
    )

    override fun write(value: QrCodeListView, buf: ByteBuffer) {
            FfiConverterSequenceTypeQrCodeEntryView.write(value.`qrKeys`, buf)
            FfiConverterULong.write(value.`total`, buf)
    }
}




object FfiConverterTypeQrCodeRefreshView: FfiConverterRustBuffer<QrCodeRefreshView> {
    override fun read(buf: ByteBuffer): QrCodeRefreshView {
        return QrCodeRefreshView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: QrCodeRefreshView) = (
            FfiConverterString.allocationSize(value.`oldQrKey`) +
            FfiConverterString.allocationSize(value.`newQrKey`) +
            FfiConverterString.allocationSize(value.`newQrCode`) +
            FfiConverterString.allocationSize(value.`revokedAt`)
    )

    override fun write(value: QrCodeRefreshView, buf: ByteBuffer) {
            FfiConverterString.write(value.`oldQrKey`, buf)
            FfiConverterString.write(value.`newQrKey`, buf)
            FfiConverterString.write(value.`newQrCode`, buf)
            FfiConverterString.write(value.`revokedAt`, buf)
    }
}




object FfiConverterTypeQrCodeResolveView: FfiConverterRustBuffer<QrCodeResolveView> {
    override fun read(buf: ByteBuffer): QrCodeResolveView {
        return QrCodeResolveView(
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterUInt.read(buf),
            FfiConverterOptionalUInt.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: QrCodeResolveView) = (
            FfiConverterString.allocationSize(value.`qrType`) +
            FfiConverterULong.allocationSize(value.`targetId`) +
            FfiConverterString.allocationSize(value.`action`) +
            FfiConverterOptionalString.allocationSize(value.`dataJson`) +
            FfiConverterUInt.allocationSize(value.`usedCount`) +
            FfiConverterOptionalUInt.allocationSize(value.`maxUsage`) +
            FfiConverterOptionalString.allocationSize(value.`expireAt`)
    )

    override fun write(value: QrCodeResolveView, buf: ByteBuffer) {
            FfiConverterString.write(value.`qrType`, buf)
            FfiConverterULong.write(value.`targetId`, buf)
            FfiConverterString.write(value.`action`, buf)
            FfiConverterOptionalString.write(value.`dataJson`, buf)
            FfiConverterUInt.write(value.`usedCount`, buf)
            FfiConverterOptionalUInt.write(value.`maxUsage`, buf)
            FfiConverterOptionalString.write(value.`expireAt`, buf)
    }
}




object FfiConverterTypeQrCodeRevokeView: FfiConverterRustBuffer<QrCodeRevokeView> {
    override fun read(buf: ByteBuffer): QrCodeRevokeView {
        return QrCodeRevokeView(
            FfiConverterBoolean.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: QrCodeRevokeView) = (
            FfiConverterBoolean.allocationSize(value.`success`) +
            FfiConverterString.allocationSize(value.`qrKey`) +
            FfiConverterString.allocationSize(value.`revokedAt`)
    )

    override fun write(value: QrCodeRevokeView, buf: ByteBuffer) {
            FfiConverterBoolean.write(value.`success`, buf)
            FfiConverterString.write(value.`qrKey`, buf)
            FfiConverterString.write(value.`revokedAt`, buf)
    }
}




object FfiConverterTypeQueueConfigView: FfiConverterRustBuffer<QueueConfigView> {
    override fun read(buf: ByteBuffer): QueueConfigView {
        return QueueConfigView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: QueueConfigView) = (
            FfiConverterString.allocationSize(value.`normalQueue`) +
            FfiConverterString.allocationSize(value.`fileQueue`)
    )

    override fun write(value: QueueConfigView, buf: ByteBuffer) {
            FfiConverterString.write(value.`normalQueue`, buf)
            FfiConverterString.write(value.`fileQueue`, buf)
    }
}




object FfiConverterTypeQueueMessage: FfiConverterRustBuffer<QueueMessage> {
    override fun read(buf: ByteBuffer): QueueMessage {
        return QueueMessage(
            FfiConverterULong.read(buf),
            FfiConverterByteArray.read(buf),
        )
    }

    override fun allocationSize(value: QueueMessage) = (
            FfiConverterULong.allocationSize(value.`messageId`) +
            FfiConverterByteArray.allocationSize(value.`payload`)
    )

    override fun write(value: QueueMessage, buf: ByteBuffer) {
            FfiConverterULong.write(value.`messageId`, buf)
            FfiConverterByteArray.write(value.`payload`, buf)
    }
}




object FfiConverterTypeReactionsBatchItemView: FfiConverterRustBuffer<ReactionsBatchItemView> {
    override fun read(buf: ByteBuffer): ReactionsBatchItemView {
        return ReactionsBatchItemView(
            FfiConverterULong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterSequenceTypeMessageReactionEmojiUsersView.read(buf),
        )
    }

    override fun allocationSize(value: ReactionsBatchItemView) = (
            FfiConverterULong.allocationSize(value.`serverMessageId`) +
            FfiConverterBoolean.allocationSize(value.`success`) +
            FfiConverterULong.allocationSize(value.`totalCount`) +
            FfiConverterSequenceTypeMessageReactionEmojiUsersView.allocationSize(value.`reactions`)
    )

    override fun write(value: ReactionsBatchItemView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`serverMessageId`, buf)
            FfiConverterBoolean.write(value.`success`, buf)
            FfiConverterULong.write(value.`totalCount`, buf)
            FfiConverterSequenceTypeMessageReactionEmojiUsersView.write(value.`reactions`, buf)
    }
}




object FfiConverterTypeReactionsBatchView: FfiConverterRustBuffer<ReactionsBatchView> {
    override fun read(buf: ByteBuffer): ReactionsBatchView {
        return ReactionsBatchView(
            FfiConverterSequenceTypeReactionsBatchItemView.read(buf),
        )
    }

    override fun allocationSize(value: ReactionsBatchView) = (
            FfiConverterSequenceTypeReactionsBatchItemView.allocationSize(value.`items`)
    )

    override fun write(value: ReactionsBatchView, buf: ByteBuffer) {
            FfiConverterSequenceTypeReactionsBatchItemView.write(value.`items`, buf)
    }
}




object FfiConverterTypeRetryConfigView: FfiConverterRustBuffer<RetryConfigView> {
    override fun read(buf: ByteBuffer): RetryConfigView {
        return RetryConfigView(
            FfiConverterBoolean.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: RetryConfigView) = (
            FfiConverterBoolean.allocationSize(value.`messageRetry`) +
            FfiConverterString.allocationSize(value.`strategy`)
    )

    override fun write(value: RetryConfigView, buf: ByteBuffer) {
            FfiConverterBoolean.write(value.`messageRetry`, buf)
            FfiConverterString.write(value.`strategy`, buf)
    }
}




object FfiConverterTypeSearchUserEntry: FfiConverterRustBuffer<SearchUserEntry> {
    override fun read(buf: ByteBuffer): SearchUserEntry {
        return SearchUserEntry(
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
        )
    }

    override fun allocationSize(value: SearchUserEntry) = (
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterString.allocationSize(value.`username`) +
            FfiConverterString.allocationSize(value.`nickname`) +
            FfiConverterOptionalString.allocationSize(value.`avatarUrl`) +
            FfiConverterInt.allocationSize(value.`userType`) +
            FfiConverterULong.allocationSize(value.`searchSessionId`) +
            FfiConverterBoolean.allocationSize(value.`isFriend`) +
            FfiConverterBoolean.allocationSize(value.`canSendMessage`)
    )

    override fun write(value: SearchUserEntry, buf: ByteBuffer) {
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterString.write(value.`username`, buf)
            FfiConverterString.write(value.`nickname`, buf)
            FfiConverterOptionalString.write(value.`avatarUrl`, buf)
            FfiConverterInt.write(value.`userType`, buf)
            FfiConverterULong.write(value.`searchSessionId`, buf)
            FfiConverterBoolean.write(value.`isFriend`, buf)
            FfiConverterBoolean.write(value.`canSendMessage`, buf)
    }
}




object FfiConverterTypeSeenByEntry: FfiConverterRustBuffer<SeenByEntry> {
    override fun read(buf: ByteBuffer): SeenByEntry {
        return SeenByEntry(
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: SeenByEntry) = (
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterOptionalString.allocationSize(value.`readAt`)
    )

    override fun write(value: SeenByEntry, buf: ByteBuffer) {
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterOptionalString.write(value.`readAt`, buf)
    }
}




object FfiConverterTypeSendMessageOptionsInput: FfiConverterRustBuffer<SendMessageOptionsInput> {
    override fun read(buf: ByteBuffer): SendMessageOptionsInput {
        return SendMessageOptionsInput(
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: SendMessageOptionsInput) = (
            FfiConverterOptionalString.allocationSize(value.`optionsJson`)
    )

    override fun write(value: SendMessageOptionsInput, buf: ByteBuffer) {
            FfiConverterOptionalString.write(value.`optionsJson`, buf)
    }
}




object FfiConverterTypeSequencedSdkEvent: FfiConverterRustBuffer<SequencedSdkEvent> {
    override fun read(buf: ByteBuffer): SequencedSdkEvent {
        return SequencedSdkEvent(
            FfiConverterULong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterTypeSdkEvent.read(buf),
        )
    }

    override fun allocationSize(value: SequencedSdkEvent) = (
            FfiConverterULong.allocationSize(value.`sequenceId`) +
            FfiConverterLong.allocationSize(value.`timestampMs`) +
            FfiConverterTypeSdkEvent.allocationSize(value.`event`)
    )

    override fun write(value: SequencedSdkEvent, buf: ByteBuffer) {
            FfiConverterULong.write(value.`sequenceId`, buf)
            FfiConverterLong.write(value.`timestampMs`, buf)
            FfiConverterTypeSdkEvent.write(value.`event`, buf)
    }
}




object FfiConverterTypeServerEndpoint: FfiConverterRustBuffer<ServerEndpoint> {
    override fun read(buf: ByteBuffer): ServerEndpoint {
        return ServerEndpoint(
            FfiConverterTypeTransportProtocol.read(buf),
            FfiConverterString.read(buf),
            FfiConverterUShort.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterBoolean.read(buf),
        )
    }

    override fun allocationSize(value: ServerEndpoint) = (
            FfiConverterTypeTransportProtocol.allocationSize(value.`protocol`) +
            FfiConverterString.allocationSize(value.`host`) +
            FfiConverterUShort.allocationSize(value.`port`) +
            FfiConverterOptionalString.allocationSize(value.`path`) +
            FfiConverterBoolean.allocationSize(value.`useTls`)
    )

    override fun write(value: ServerEndpoint, buf: ByteBuffer) {
            FfiConverterTypeTransportProtocol.write(value.`protocol`, buf)
            FfiConverterString.write(value.`host`, buf)
            FfiConverterUShort.write(value.`port`, buf)
            FfiConverterOptionalString.write(value.`path`, buf)
            FfiConverterBoolean.write(value.`useTls`, buf)
    }
}




object FfiConverterTypeSessionSnapshot: FfiConverterRustBuffer<SessionSnapshot> {
    override fun read(buf: ByteBuffer): SessionSnapshot {
        return SessionSnapshot(
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterBoolean.read(buf),
        )
    }

    override fun allocationSize(value: SessionSnapshot) = (
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterString.allocationSize(value.`token`) +
            FfiConverterString.allocationSize(value.`deviceId`) +
            FfiConverterBoolean.allocationSize(value.`bootstrapCompleted`)
    )

    override fun write(value: SessionSnapshot, buf: ByteBuffer) {
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterString.write(value.`token`, buf)
            FfiConverterString.write(value.`deviceId`, buf)
            FfiConverterBoolean.write(value.`bootstrapCompleted`, buf)
    }
}




object FfiConverterTypeStickerInfoView: FfiConverterRustBuffer<StickerInfoView> {
    override fun read(buf: ByteBuffer): StickerInfoView {
        return StickerInfoView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterUInt.read(buf),
            FfiConverterUInt.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: StickerInfoView) = (
            FfiConverterString.allocationSize(value.`stickerId`) +
            FfiConverterString.allocationSize(value.`packageId`) +
            FfiConverterString.allocationSize(value.`imageUrl`) +
            FfiConverterString.allocationSize(value.`altText`) +
            FfiConverterOptionalString.allocationSize(value.`emoji`) +
            FfiConverterUInt.allocationSize(value.`width`) +
            FfiConverterUInt.allocationSize(value.`height`) +
            FfiConverterString.allocationSize(value.`mimeType`)
    )

    override fun write(value: StickerInfoView, buf: ByteBuffer) {
            FfiConverterString.write(value.`stickerId`, buf)
            FfiConverterString.write(value.`packageId`, buf)
            FfiConverterString.write(value.`imageUrl`, buf)
            FfiConverterString.write(value.`altText`, buf)
            FfiConverterOptionalString.write(value.`emoji`, buf)
            FfiConverterUInt.write(value.`width`, buf)
            FfiConverterUInt.write(value.`height`, buf)
            FfiConverterString.write(value.`mimeType`, buf)
    }
}




object FfiConverterTypeStickerPackageDetailInput: FfiConverterRustBuffer<StickerPackageDetailInput> {
    override fun read(buf: ByteBuffer): StickerPackageDetailInput {
        return StickerPackageDetailInput(
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: StickerPackageDetailInput) = (
            FfiConverterString.allocationSize(value.`packageId`)
    )

    override fun write(value: StickerPackageDetailInput, buf: ByteBuffer) {
            FfiConverterString.write(value.`packageId`, buf)
    }
}




object FfiConverterTypeStickerPackageDetailView: FfiConverterRustBuffer<StickerPackageDetailView> {
    override fun read(buf: ByteBuffer): StickerPackageDetailView {
        return StickerPackageDetailView(
            FfiConverterTypeStickerPackageInfoView.read(buf),
        )
    }

    override fun allocationSize(value: StickerPackageDetailView) = (
            FfiConverterTypeStickerPackageInfoView.allocationSize(value.`package`)
    )

    override fun write(value: StickerPackageDetailView, buf: ByteBuffer) {
            FfiConverterTypeStickerPackageInfoView.write(value.`package`, buf)
    }
}




object FfiConverterTypeStickerPackageInfoView: FfiConverterRustBuffer<StickerPackageInfoView> {
    override fun read(buf: ByteBuffer): StickerPackageInfoView {
        return StickerPackageInfoView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterSequenceTypeStickerInfoView.read(buf),
        )
    }

    override fun allocationSize(value: StickerPackageInfoView) = (
            FfiConverterString.allocationSize(value.`packageId`) +
            FfiConverterString.allocationSize(value.`name`) +
            FfiConverterString.allocationSize(value.`thumbnailUrl`) +
            FfiConverterString.allocationSize(value.`author`) +
            FfiConverterString.allocationSize(value.`description`) +
            FfiConverterULong.allocationSize(value.`stickerCount`) +
            FfiConverterSequenceTypeStickerInfoView.allocationSize(value.`stickers`)
    )

    override fun write(value: StickerPackageInfoView, buf: ByteBuffer) {
            FfiConverterString.write(value.`packageId`, buf)
            FfiConverterString.write(value.`name`, buf)
            FfiConverterString.write(value.`thumbnailUrl`, buf)
            FfiConverterString.write(value.`author`, buf)
            FfiConverterString.write(value.`description`, buf)
            FfiConverterULong.write(value.`stickerCount`, buf)
            FfiConverterSequenceTypeStickerInfoView.write(value.`stickers`, buf)
    }
}




object FfiConverterTypeStickerPackageListInput: FfiConverterRustBuffer<StickerPackageListInput> {
    override fun read(buf: ByteBuffer): StickerPackageListInput {
        return StickerPackageListInput()
    }

    override fun allocationSize(value: StickerPackageListInput) = 0UL

    override fun write(value: StickerPackageListInput, buf: ByteBuffer) {
    }
}




object FfiConverterTypeStickerPackageListView: FfiConverterRustBuffer<StickerPackageListView> {
    override fun read(buf: ByteBuffer): StickerPackageListView {
        return StickerPackageListView(
            FfiConverterSequenceTypeStickerPackageInfoView.read(buf),
        )
    }

    override fun allocationSize(value: StickerPackageListView) = (
            FfiConverterSequenceTypeStickerPackageInfoView.allocationSize(value.`packages`)
    )

    override fun write(value: StickerPackageListView, buf: ByteBuffer) {
            FfiConverterSequenceTypeStickerPackageInfoView.write(value.`packages`, buf)
    }
}




object FfiConverterTypeStoredBlacklistEntry: FfiConverterRustBuffer<StoredBlacklistEntry> {
    override fun read(buf: ByteBuffer): StoredBlacklistEntry {
        return StoredBlacklistEntry(
            FfiConverterULong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: StoredBlacklistEntry) = (
            FfiConverterULong.allocationSize(value.`blockedUserId`) +
            FfiConverterLong.allocationSize(value.`createdAt`) +
            FfiConverterLong.allocationSize(value.`updatedAt`)
    )

    override fun write(value: StoredBlacklistEntry, buf: ByteBuffer) {
            FfiConverterULong.write(value.`blockedUserId`, buf)
            FfiConverterLong.write(value.`createdAt`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypeStoredChannel: FfiConverterRustBuffer<StoredChannel> {
    override fun read(buf: ByteBuffer): StoredChannel {
        return StoredChannel(
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: StoredChannel) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterString.allocationSize(value.`channelName`) +
            FfiConverterString.allocationSize(value.`channelRemark`) +
            FfiConverterString.allocationSize(value.`avatar`) +
            FfiConverterInt.allocationSize(value.`unreadCount`) +
            FfiConverterInt.allocationSize(value.`top`) +
            FfiConverterInt.allocationSize(value.`mute`) +
            FfiConverterLong.allocationSize(value.`lastMsgTimestamp`) +
            FfiConverterULong.allocationSize(value.`lastLocalMessageId`) +
            FfiConverterString.allocationSize(value.`lastMsgContent`) +
            FfiConverterLong.allocationSize(value.`updatedAt`)
    )

    override fun write(value: StoredChannel, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterString.write(value.`channelName`, buf)
            FfiConverterString.write(value.`channelRemark`, buf)
            FfiConverterString.write(value.`avatar`, buf)
            FfiConverterInt.write(value.`unreadCount`, buf)
            FfiConverterInt.write(value.`top`, buf)
            FfiConverterInt.write(value.`mute`, buf)
            FfiConverterLong.write(value.`lastMsgTimestamp`, buf)
            FfiConverterULong.write(value.`lastLocalMessageId`, buf)
            FfiConverterString.write(value.`lastMsgContent`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypeStoredChannelExtra: FfiConverterRustBuffer<StoredChannelExtra> {
    override fun read(buf: ByteBuffer): StoredChannelExtra {
        return StoredChannelExtra(
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: StoredChannelExtra) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterULong.allocationSize(value.`browseTo`) +
            FfiConverterULong.allocationSize(value.`keepPts`) +
            FfiConverterInt.allocationSize(value.`keepOffsetY`) +
            FfiConverterString.allocationSize(value.`draft`) +
            FfiConverterULong.allocationSize(value.`draftUpdatedAt`) +
            FfiConverterLong.allocationSize(value.`version`)
    )

    override fun write(value: StoredChannelExtra, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterULong.write(value.`browseTo`, buf)
            FfiConverterULong.write(value.`keepPts`, buf)
            FfiConverterInt.write(value.`keepOffsetY`, buf)
            FfiConverterString.write(value.`draft`, buf)
            FfiConverterULong.write(value.`draftUpdatedAt`, buf)
            FfiConverterLong.write(value.`version`, buf)
    }
}




object FfiConverterTypeStoredChannelMember: FfiConverterRustBuffer<StoredChannelMember> {
    override fun read(buf: ByteBuffer): StoredChannelMember {
        return StoredChannelMember(
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: StoredChannelMember) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterULong.allocationSize(value.`memberUid`) +
            FfiConverterString.allocationSize(value.`memberName`) +
            FfiConverterString.allocationSize(value.`memberRemark`) +
            FfiConverterString.allocationSize(value.`memberAvatar`) +
            FfiConverterULong.allocationSize(value.`memberInviteUid`) +
            FfiConverterInt.allocationSize(value.`role`) +
            FfiConverterInt.allocationSize(value.`status`) +
            FfiConverterBoolean.allocationSize(value.`isDeleted`) +
            FfiConverterInt.allocationSize(value.`robot`) +
            FfiConverterLong.allocationSize(value.`version`) +
            FfiConverterLong.allocationSize(value.`createdAt`) +
            FfiConverterLong.allocationSize(value.`updatedAt`) +
            FfiConverterString.allocationSize(value.`extra`) +
            FfiConverterLong.allocationSize(value.`forbiddenExpirationTime`) +
            FfiConverterString.allocationSize(value.`memberAvatarCacheKey`)
    )

    override fun write(value: StoredChannelMember, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterULong.write(value.`memberUid`, buf)
            FfiConverterString.write(value.`memberName`, buf)
            FfiConverterString.write(value.`memberRemark`, buf)
            FfiConverterString.write(value.`memberAvatar`, buf)
            FfiConverterULong.write(value.`memberInviteUid`, buf)
            FfiConverterInt.write(value.`role`, buf)
            FfiConverterInt.write(value.`status`, buf)
            FfiConverterBoolean.write(value.`isDeleted`, buf)
            FfiConverterInt.write(value.`robot`, buf)
            FfiConverterLong.write(value.`version`, buf)
            FfiConverterLong.write(value.`createdAt`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
            FfiConverterString.write(value.`extra`, buf)
            FfiConverterLong.write(value.`forbiddenExpirationTime`, buf)
            FfiConverterString.write(value.`memberAvatarCacheKey`, buf)
    }
}




object FfiConverterTypeStoredFriend: FfiConverterRustBuffer<StoredFriend> {
    override fun read(buf: ByteBuffer): StoredFriend {
        return StoredFriend(
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: StoredFriend) = (
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterOptionalString.allocationSize(value.`tags`) +
            FfiConverterBoolean.allocationSize(value.`isPinned`) +
            FfiConverterLong.allocationSize(value.`createdAt`) +
            FfiConverterLong.allocationSize(value.`updatedAt`)
    )

    override fun write(value: StoredFriend, buf: ByteBuffer) {
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterOptionalString.write(value.`tags`, buf)
            FfiConverterBoolean.write(value.`isPinned`, buf)
            FfiConverterLong.write(value.`createdAt`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypeStoredGroup: FfiConverterRustBuffer<StoredGroup> {
    override fun read(buf: ByteBuffer): StoredGroup {
        return StoredGroup(
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalULong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: StoredGroup) = (
            FfiConverterULong.allocationSize(value.`groupId`) +
            FfiConverterOptionalString.allocationSize(value.`name`) +
            FfiConverterString.allocationSize(value.`avatar`) +
            FfiConverterOptionalULong.allocationSize(value.`ownerId`) +
            FfiConverterBoolean.allocationSize(value.`isDismissed`) +
            FfiConverterLong.allocationSize(value.`createdAt`) +
            FfiConverterLong.allocationSize(value.`updatedAt`)
    )

    override fun write(value: StoredGroup, buf: ByteBuffer) {
            FfiConverterULong.write(value.`groupId`, buf)
            FfiConverterOptionalString.write(value.`name`, buf)
            FfiConverterString.write(value.`avatar`, buf)
            FfiConverterOptionalULong.write(value.`ownerId`, buf)
            FfiConverterBoolean.write(value.`isDismissed`, buf)
            FfiConverterLong.write(value.`createdAt`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypeStoredGroupMember: FfiConverterRustBuffer<StoredGroupMember> {
    override fun read(buf: ByteBuffer): StoredGroupMember {
        return StoredGroupMember(
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: StoredGroupMember) = (
            FfiConverterULong.allocationSize(value.`groupId`) +
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterInt.allocationSize(value.`role`) +
            FfiConverterInt.allocationSize(value.`status`) +
            FfiConverterOptionalString.allocationSize(value.`alias`) +
            FfiConverterBoolean.allocationSize(value.`isMuted`) +
            FfiConverterLong.allocationSize(value.`joinedAt`) +
            FfiConverterLong.allocationSize(value.`updatedAt`)
    )

    override fun write(value: StoredGroupMember, buf: ByteBuffer) {
            FfiConverterULong.write(value.`groupId`, buf)
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterInt.write(value.`role`, buf)
            FfiConverterInt.write(value.`status`, buf)
            FfiConverterOptionalString.write(value.`alias`, buf)
            FfiConverterBoolean.write(value.`isMuted`, buf)
            FfiConverterLong.write(value.`joinedAt`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypeStoredMessage: FfiConverterRustBuffer<StoredMessage> {
    override fun read(buf: ByteBuffer): StoredMessage {
        return StoredMessage(
            FfiConverterULong.read(buf),
            FfiConverterOptionalULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterString.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: StoredMessage) = (
            FfiConverterULong.allocationSize(value.`messageId`) +
            FfiConverterOptionalULong.allocationSize(value.`serverMessageId`) +
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterULong.allocationSize(value.`fromUid`) +
            FfiConverterInt.allocationSize(value.`messageType`) +
            FfiConverterString.allocationSize(value.`content`) +
            FfiConverterInt.allocationSize(value.`status`) +
            FfiConverterLong.allocationSize(value.`createdAt`) +
            FfiConverterLong.allocationSize(value.`updatedAt`) +
            FfiConverterString.allocationSize(value.`extra`)
    )

    override fun write(value: StoredMessage, buf: ByteBuffer) {
            FfiConverterULong.write(value.`messageId`, buf)
            FfiConverterOptionalULong.write(value.`serverMessageId`, buf)
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterULong.write(value.`fromUid`, buf)
            FfiConverterInt.write(value.`messageType`, buf)
            FfiConverterString.write(value.`content`, buf)
            FfiConverterInt.write(value.`status`, buf)
            FfiConverterLong.write(value.`createdAt`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
            FfiConverterString.write(value.`extra`, buf)
    }
}




object FfiConverterTypeStoredMessageExtra: FfiConverterRustBuffer<StoredMessageExtra> {
    override fun read(buf: ByteBuffer): StoredMessageExtra {
        return StoredMessageExtra(
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterOptionalULong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
        )
    }

    override fun allocationSize(value: StoredMessageExtra) = (
            FfiConverterULong.allocationSize(value.`messageId`) +
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterInt.allocationSize(value.`readed`) +
            FfiConverterInt.allocationSize(value.`readedCount`) +
            FfiConverterInt.allocationSize(value.`unreadCount`) +
            FfiConverterBoolean.allocationSize(value.`revoke`) +
            FfiConverterOptionalULong.allocationSize(value.`revoker`) +
            FfiConverterLong.allocationSize(value.`extraVersion`) +
            FfiConverterBoolean.allocationSize(value.`isMutualDeleted`) +
            FfiConverterOptionalString.allocationSize(value.`contentEdit`) +
            FfiConverterInt.allocationSize(value.`editedAt`) +
            FfiConverterBoolean.allocationSize(value.`needUpload`) +
            FfiConverterBoolean.allocationSize(value.`isPinned`)
    )

    override fun write(value: StoredMessageExtra, buf: ByteBuffer) {
            FfiConverterULong.write(value.`messageId`, buf)
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterInt.write(value.`readed`, buf)
            FfiConverterInt.write(value.`readedCount`, buf)
            FfiConverterInt.write(value.`unreadCount`, buf)
            FfiConverterBoolean.write(value.`revoke`, buf)
            FfiConverterOptionalULong.write(value.`revoker`, buf)
            FfiConverterLong.write(value.`extraVersion`, buf)
            FfiConverterBoolean.write(value.`isMutualDeleted`, buf)
            FfiConverterOptionalString.write(value.`contentEdit`, buf)
            FfiConverterInt.write(value.`editedAt`, buf)
            FfiConverterBoolean.write(value.`needUpload`, buf)
            FfiConverterBoolean.write(value.`isPinned`, buf)
    }
}




object FfiConverterTypeStoredMessageReaction: FfiConverterRustBuffer<StoredMessageReaction> {
    override fun read(buf: ByteBuffer): StoredMessageReaction {
        return StoredMessageReaction(
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: StoredMessageReaction) = (
            FfiConverterULong.allocationSize(value.`id`) +
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterULong.allocationSize(value.`uid`) +
            FfiConverterString.allocationSize(value.`name`) +
            FfiConverterString.allocationSize(value.`emoji`) +
            FfiConverterULong.allocationSize(value.`messageId`) +
            FfiConverterLong.allocationSize(value.`seq`) +
            FfiConverterBoolean.allocationSize(value.`isDeleted`) +
            FfiConverterLong.allocationSize(value.`createdAt`)
    )

    override fun write(value: StoredMessageReaction, buf: ByteBuffer) {
            FfiConverterULong.write(value.`id`, buf)
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterULong.write(value.`uid`, buf)
            FfiConverterString.write(value.`name`, buf)
            FfiConverterString.write(value.`emoji`, buf)
            FfiConverterULong.write(value.`messageId`, buf)
            FfiConverterLong.write(value.`seq`, buf)
            FfiConverterBoolean.write(value.`isDeleted`, buf)
            FfiConverterLong.write(value.`createdAt`, buf)
    }
}




object FfiConverterTypeStoredReminder: FfiConverterRustBuffer<StoredReminder> {
    override fun read(buf: ByteBuffer): StoredReminder {
        return StoredReminder(
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterOptionalULong.read(buf),
        )
    }

    override fun allocationSize(value: StoredReminder) = (
            FfiConverterULong.allocationSize(value.`id`) +
            FfiConverterULong.allocationSize(value.`reminderId`) +
            FfiConverterULong.allocationSize(value.`messageId`) +
            FfiConverterLong.allocationSize(value.`pts`) +
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterULong.allocationSize(value.`uid`) +
            FfiConverterInt.allocationSize(value.`reminderType`) +
            FfiConverterString.allocationSize(value.`text`) +
            FfiConverterString.allocationSize(value.`data`) +
            FfiConverterBoolean.allocationSize(value.`isLocate`) +
            FfiConverterLong.allocationSize(value.`version`) +
            FfiConverterBoolean.allocationSize(value.`done`) +
            FfiConverterBoolean.allocationSize(value.`needUpload`) +
            FfiConverterOptionalULong.allocationSize(value.`publisher`)
    )

    override fun write(value: StoredReminder, buf: ByteBuffer) {
            FfiConverterULong.write(value.`id`, buf)
            FfiConverterULong.write(value.`reminderId`, buf)
            FfiConverterULong.write(value.`messageId`, buf)
            FfiConverterLong.write(value.`pts`, buf)
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterULong.write(value.`uid`, buf)
            FfiConverterInt.write(value.`reminderType`, buf)
            FfiConverterString.write(value.`text`, buf)
            FfiConverterString.write(value.`data`, buf)
            FfiConverterBoolean.write(value.`isLocate`, buf)
            FfiConverterLong.write(value.`version`, buf)
            FfiConverterBoolean.write(value.`done`, buf)
            FfiConverterBoolean.write(value.`needUpload`, buf)
            FfiConverterOptionalULong.write(value.`publisher`, buf)
    }
}




object FfiConverterTypeStoredUser: FfiConverterRustBuffer<StoredUser> {
    override fun read(buf: ByteBuffer): StoredUser {
        return StoredUser(
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterString.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: StoredUser) = (
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterOptionalString.allocationSize(value.`username`) +
            FfiConverterOptionalString.allocationSize(value.`nickname`) +
            FfiConverterOptionalString.allocationSize(value.`alias`) +
            FfiConverterString.allocationSize(value.`avatar`) +
            FfiConverterInt.allocationSize(value.`userType`) +
            FfiConverterBoolean.allocationSize(value.`isDeleted`) +
            FfiConverterString.allocationSize(value.`channelId`) +
            FfiConverterLong.allocationSize(value.`updatedAt`)
    )

    override fun write(value: StoredUser, buf: ByteBuffer) {
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterOptionalString.write(value.`username`, buf)
            FfiConverterOptionalString.write(value.`nickname`, buf)
            FfiConverterOptionalString.write(value.`alias`, buf)
            FfiConverterString.write(value.`avatar`, buf)
            FfiConverterInt.write(value.`userType`, buf)
            FfiConverterBoolean.write(value.`isDeleted`, buf)
            FfiConverterString.write(value.`channelId`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypeSyncEntitiesInput: FfiConverterRustBuffer<SyncEntitiesInput> {
    override fun read(buf: ByteBuffer): SyncEntitiesInput {
        return SyncEntitiesInput(
            FfiConverterString.read(buf),
            FfiConverterOptionalULong.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalUInt.read(buf),
        )
    }

    override fun allocationSize(value: SyncEntitiesInput) = (
            FfiConverterString.allocationSize(value.`entityType`) +
            FfiConverterOptionalULong.allocationSize(value.`sinceVersion`) +
            FfiConverterOptionalString.allocationSize(value.`scope`) +
            FfiConverterOptionalUInt.allocationSize(value.`limit`)
    )

    override fun write(value: SyncEntitiesInput, buf: ByteBuffer) {
            FfiConverterString.write(value.`entityType`, buf)
            FfiConverterOptionalULong.write(value.`sinceVersion`, buf)
            FfiConverterOptionalString.write(value.`scope`, buf)
            FfiConverterOptionalUInt.write(value.`limit`, buf)
    }
}




object FfiConverterTypeSyncEntitiesView: FfiConverterRustBuffer<SyncEntitiesView> {
    override fun read(buf: ByteBuffer): SyncEntitiesView {
        return SyncEntitiesView(
            FfiConverterSequenceTypeSyncEntityItemView.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterOptionalULong.read(buf),
        )
    }

    override fun allocationSize(value: SyncEntitiesView) = (
            FfiConverterSequenceTypeSyncEntityItemView.allocationSize(value.`items`) +
            FfiConverterULong.allocationSize(value.`nextVersion`) +
            FfiConverterBoolean.allocationSize(value.`hasMore`) +
            FfiConverterOptionalULong.allocationSize(value.`minVersion`)
    )

    override fun write(value: SyncEntitiesView, buf: ByteBuffer) {
            FfiConverterSequenceTypeSyncEntityItemView.write(value.`items`, buf)
            FfiConverterULong.write(value.`nextVersion`, buf)
            FfiConverterBoolean.write(value.`hasMore`, buf)
            FfiConverterOptionalULong.write(value.`minVersion`, buf)
    }
}




object FfiConverterTypeSyncEntityItemView: FfiConverterRustBuffer<SyncEntityItemView> {
    override fun read(buf: ByteBuffer): SyncEntityItemView {
        return SyncEntityItemView(
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: SyncEntityItemView) = (
            FfiConverterString.allocationSize(value.`entityId`) +
            FfiConverterULong.allocationSize(value.`version`) +
            FfiConverterBoolean.allocationSize(value.`deleted`) +
            FfiConverterOptionalString.allocationSize(value.`payloadJson`)
    )

    override fun write(value: SyncEntityItemView, buf: ByteBuffer) {
            FfiConverterString.write(value.`entityId`, buf)
            FfiConverterULong.write(value.`version`, buf)
            FfiConverterBoolean.write(value.`deleted`, buf)
            FfiConverterOptionalString.write(value.`payloadJson`, buf)
    }
}




object FfiConverterTypeSyncPayloadEntry: FfiConverterRustBuffer<SyncPayloadEntry> {
    override fun read(buf: ByteBuffer): SyncPayloadEntry {
        return SyncPayloadEntry(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: SyncPayloadEntry) = (
            FfiConverterString.allocationSize(value.`key`) +
            FfiConverterString.allocationSize(value.`value`)
    )

    override fun write(value: SyncPayloadEntry, buf: ByteBuffer) {
            FfiConverterString.write(value.`key`, buf)
            FfiConverterString.write(value.`value`, buf)
    }
}




object FfiConverterTypeSyncSubmitInput: FfiConverterRustBuffer<SyncSubmitInput> {
    override fun read(buf: ByteBuffer): SyncSubmitInput {
        return SyncSubmitInput(
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterUByte.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterSequenceTypeSyncPayloadEntry.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterOptionalString.read(buf),
        )
    }

    override fun allocationSize(value: SyncSubmitInput) = (
            FfiConverterULong.allocationSize(value.`localMessageId`) +
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterUByte.allocationSize(value.`channelType`) +
            FfiConverterULong.allocationSize(value.`lastPts`) +
            FfiConverterString.allocationSize(value.`commandType`) +
            FfiConverterSequenceTypeSyncPayloadEntry.allocationSize(value.`payloadEntries`) +
            FfiConverterLong.allocationSize(value.`clientTimestamp`) +
            FfiConverterOptionalString.allocationSize(value.`deviceId`)
    )

    override fun write(value: SyncSubmitInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`localMessageId`, buf)
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterUByte.write(value.`channelType`, buf)
            FfiConverterULong.write(value.`lastPts`, buf)
            FfiConverterString.write(value.`commandType`, buf)
            FfiConverterSequenceTypeSyncPayloadEntry.write(value.`payloadEntries`, buf)
            FfiConverterLong.write(value.`clientTimestamp`, buf)
            FfiConverterOptionalString.write(value.`deviceId`, buf)
    }
}




object FfiConverterTypeSyncSubmitView: FfiConverterRustBuffer<SyncSubmitView> {
    override fun read(buf: ByteBuffer): SyncSubmitView {
        return SyncSubmitView(
            FfiConverterString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalULong.read(buf),
            FfiConverterOptionalULong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterULong.read(buf),
        )
    }

    override fun allocationSize(value: SyncSubmitView) = (
            FfiConverterString.allocationSize(value.`decision`) +
            FfiConverterOptionalString.allocationSize(value.`decisionReason`) +
            FfiConverterOptionalULong.allocationSize(value.`pts`) +
            FfiConverterOptionalULong.allocationSize(value.`serverMsgId`) +
            FfiConverterLong.allocationSize(value.`serverTimestamp`) +
            FfiConverterULong.allocationSize(value.`localMessageId`) +
            FfiConverterBoolean.allocationSize(value.`hasGap`) +
            FfiConverterULong.allocationSize(value.`currentPts`)
    )

    override fun write(value: SyncSubmitView, buf: ByteBuffer) {
            FfiConverterString.write(value.`decision`, buf)
            FfiConverterOptionalString.write(value.`decisionReason`, buf)
            FfiConverterOptionalULong.write(value.`pts`, buf)
            FfiConverterOptionalULong.write(value.`serverMsgId`, buf)
            FfiConverterLong.write(value.`serverTimestamp`, buf)
            FfiConverterULong.write(value.`localMessageId`, buf)
            FfiConverterBoolean.write(value.`hasGap`, buf)
            FfiConverterULong.write(value.`currentPts`, buf)
    }
}




object FfiConverterTypeTypingChannelView: FfiConverterRustBuffer<TypingChannelView> {
    override fun read(buf: ByteBuffer): TypingChannelView {
        return TypingChannelView(
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
        )
    }

    override fun allocationSize(value: TypingChannelView) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`)
    )

    override fun write(value: TypingChannelView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
    }
}




object FfiConverterTypeTypingStatsView: FfiConverterRustBuffer<TypingStatsView> {
    override fun read(buf: ByteBuffer): TypingStatsView {
        return TypingStatsView(
            FfiConverterULong.read(buf),
            FfiConverterSequenceTypeTypingChannelView.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
        )
    }

    override fun allocationSize(value: TypingStatsView) = (
            FfiConverterULong.allocationSize(value.`typing`) +
            FfiConverterSequenceTypeTypingChannelView.allocationSize(value.`activeChannels`) +
            FfiConverterULong.allocationSize(value.`startedCount`) +
            FfiConverterULong.allocationSize(value.`stoppedCount`)
    )

    override fun write(value: TypingStatsView, buf: ByteBuffer) {
            FfiConverterULong.write(value.`typing`, buf)
            FfiConverterSequenceTypeTypingChannelView.write(value.`activeChannels`, buf)
            FfiConverterULong.write(value.`startedCount`, buf)
            FfiConverterULong.write(value.`stoppedCount`, buf)
    }
}




object FfiConverterTypeUnreadMentionCount: FfiConverterRustBuffer<UnreadMentionCount> {
    override fun read(buf: ByteBuffer): UnreadMentionCount {
        return UnreadMentionCount(
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterInt.read(buf),
        )
    }

    override fun allocationSize(value: UnreadMentionCount) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterInt.allocationSize(value.`unreadCount`)
    )

    override fun write(value: UnreadMentionCount, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterInt.write(value.`unreadCount`, buf)
    }
}




object FfiConverterTypeUpsertBlacklistInput: FfiConverterRustBuffer<UpsertBlacklistInput> {
    override fun read(buf: ByteBuffer): UpsertBlacklistInput {
        return UpsertBlacklistInput(
            FfiConverterULong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: UpsertBlacklistInput) = (
            FfiConverterULong.allocationSize(value.`blockedUserId`) +
            FfiConverterLong.allocationSize(value.`createdAt`) +
            FfiConverterLong.allocationSize(value.`updatedAt`)
    )

    override fun write(value: UpsertBlacklistInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`blockedUserId`, buf)
            FfiConverterLong.write(value.`createdAt`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypeUpsertChannelExtraInput: FfiConverterRustBuffer<UpsertChannelExtraInput> {
    override fun read(buf: ByteBuffer): UpsertChannelExtraInput {
        return UpsertChannelExtraInput(
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
        )
    }

    override fun allocationSize(value: UpsertChannelExtraInput) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterULong.allocationSize(value.`browseTo`) +
            FfiConverterULong.allocationSize(value.`keepPts`) +
            FfiConverterInt.allocationSize(value.`keepOffsetY`) +
            FfiConverterString.allocationSize(value.`draft`) +
            FfiConverterULong.allocationSize(value.`draftUpdatedAt`)
    )

    override fun write(value: UpsertChannelExtraInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterULong.write(value.`browseTo`, buf)
            FfiConverterULong.write(value.`keepPts`, buf)
            FfiConverterInt.write(value.`keepOffsetY`, buf)
            FfiConverterString.write(value.`draft`, buf)
            FfiConverterULong.write(value.`draftUpdatedAt`, buf)
    }
}




object FfiConverterTypeUpsertChannelInput: FfiConverterRustBuffer<UpsertChannelInput> {
    override fun read(buf: ByteBuffer): UpsertChannelInput {
        return UpsertChannelInput(
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: UpsertChannelInput) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterString.allocationSize(value.`channelName`) +
            FfiConverterString.allocationSize(value.`channelRemark`) +
            FfiConverterString.allocationSize(value.`avatar`) +
            FfiConverterInt.allocationSize(value.`unreadCount`) +
            FfiConverterInt.allocationSize(value.`top`) +
            FfiConverterInt.allocationSize(value.`mute`) +
            FfiConverterLong.allocationSize(value.`lastMsgTimestamp`) +
            FfiConverterULong.allocationSize(value.`lastLocalMessageId`) +
            FfiConverterString.allocationSize(value.`lastMsgContent`)
    )

    override fun write(value: UpsertChannelInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterString.write(value.`channelName`, buf)
            FfiConverterString.write(value.`channelRemark`, buf)
            FfiConverterString.write(value.`avatar`, buf)
            FfiConverterInt.write(value.`unreadCount`, buf)
            FfiConverterInt.write(value.`top`, buf)
            FfiConverterInt.write(value.`mute`, buf)
            FfiConverterLong.write(value.`lastMsgTimestamp`, buf)
            FfiConverterULong.write(value.`lastLocalMessageId`, buf)
            FfiConverterString.write(value.`lastMsgContent`, buf)
    }
}




object FfiConverterTypeUpsertChannelMemberInput: FfiConverterRustBuffer<UpsertChannelMemberInput> {
    override fun read(buf: ByteBuffer): UpsertChannelMemberInput {
        return UpsertChannelMemberInput(
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: UpsertChannelMemberInput) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterULong.allocationSize(value.`memberUid`) +
            FfiConverterString.allocationSize(value.`memberName`) +
            FfiConverterString.allocationSize(value.`memberRemark`) +
            FfiConverterString.allocationSize(value.`memberAvatar`) +
            FfiConverterULong.allocationSize(value.`memberInviteUid`) +
            FfiConverterInt.allocationSize(value.`role`) +
            FfiConverterInt.allocationSize(value.`status`) +
            FfiConverterBoolean.allocationSize(value.`isDeleted`) +
            FfiConverterInt.allocationSize(value.`robot`) +
            FfiConverterLong.allocationSize(value.`version`) +
            FfiConverterLong.allocationSize(value.`createdAt`) +
            FfiConverterLong.allocationSize(value.`updatedAt`) +
            FfiConverterString.allocationSize(value.`extra`) +
            FfiConverterLong.allocationSize(value.`forbiddenExpirationTime`) +
            FfiConverterString.allocationSize(value.`memberAvatarCacheKey`)
    )

    override fun write(value: UpsertChannelMemberInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterULong.write(value.`memberUid`, buf)
            FfiConverterString.write(value.`memberName`, buf)
            FfiConverterString.write(value.`memberRemark`, buf)
            FfiConverterString.write(value.`memberAvatar`, buf)
            FfiConverterULong.write(value.`memberInviteUid`, buf)
            FfiConverterInt.write(value.`role`, buf)
            FfiConverterInt.write(value.`status`, buf)
            FfiConverterBoolean.write(value.`isDeleted`, buf)
            FfiConverterInt.write(value.`robot`, buf)
            FfiConverterLong.write(value.`version`, buf)
            FfiConverterLong.write(value.`createdAt`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
            FfiConverterString.write(value.`extra`, buf)
            FfiConverterLong.write(value.`forbiddenExpirationTime`, buf)
            FfiConverterString.write(value.`memberAvatarCacheKey`, buf)
    }
}




object FfiConverterTypeUpsertFriendInput: FfiConverterRustBuffer<UpsertFriendInput> {
    override fun read(buf: ByteBuffer): UpsertFriendInput {
        return UpsertFriendInput(
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: UpsertFriendInput) = (
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterOptionalString.allocationSize(value.`tags`) +
            FfiConverterBoolean.allocationSize(value.`isPinned`) +
            FfiConverterLong.allocationSize(value.`createdAt`) +
            FfiConverterLong.allocationSize(value.`updatedAt`)
    )

    override fun write(value: UpsertFriendInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterOptionalString.write(value.`tags`, buf)
            FfiConverterBoolean.write(value.`isPinned`, buf)
            FfiConverterLong.write(value.`createdAt`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypeUpsertGroupInput: FfiConverterRustBuffer<UpsertGroupInput> {
    override fun read(buf: ByteBuffer): UpsertGroupInput {
        return UpsertGroupInput(
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterOptionalULong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: UpsertGroupInput) = (
            FfiConverterULong.allocationSize(value.`groupId`) +
            FfiConverterOptionalString.allocationSize(value.`name`) +
            FfiConverterString.allocationSize(value.`avatar`) +
            FfiConverterOptionalULong.allocationSize(value.`ownerId`) +
            FfiConverterBoolean.allocationSize(value.`isDismissed`) +
            FfiConverterLong.allocationSize(value.`createdAt`) +
            FfiConverterLong.allocationSize(value.`updatedAt`)
    )

    override fun write(value: UpsertGroupInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`groupId`, buf)
            FfiConverterOptionalString.write(value.`name`, buf)
            FfiConverterString.write(value.`avatar`, buf)
            FfiConverterOptionalULong.write(value.`ownerId`, buf)
            FfiConverterBoolean.write(value.`isDismissed`, buf)
            FfiConverterLong.write(value.`createdAt`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypeUpsertGroupMemberInput: FfiConverterRustBuffer<UpsertGroupMemberInput> {
    override fun read(buf: ByteBuffer): UpsertGroupMemberInput {
        return UpsertGroupMemberInput(
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: UpsertGroupMemberInput) = (
            FfiConverterULong.allocationSize(value.`groupId`) +
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterInt.allocationSize(value.`role`) +
            FfiConverterInt.allocationSize(value.`status`) +
            FfiConverterOptionalString.allocationSize(value.`alias`) +
            FfiConverterBoolean.allocationSize(value.`isMuted`) +
            FfiConverterLong.allocationSize(value.`joinedAt`) +
            FfiConverterLong.allocationSize(value.`updatedAt`)
    )

    override fun write(value: UpsertGroupMemberInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`groupId`, buf)
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterInt.write(value.`role`, buf)
            FfiConverterInt.write(value.`status`, buf)
            FfiConverterOptionalString.write(value.`alias`, buf)
            FfiConverterBoolean.write(value.`isMuted`, buf)
            FfiConverterLong.write(value.`joinedAt`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypeUpsertMessageReactionInput: FfiConverterRustBuffer<UpsertMessageReactionInput> {
    override fun read(buf: ByteBuffer): UpsertMessageReactionInput {
        return UpsertMessageReactionInput(
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: UpsertMessageReactionInput) = (
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterULong.allocationSize(value.`uid`) +
            FfiConverterString.allocationSize(value.`name`) +
            FfiConverterString.allocationSize(value.`emoji`) +
            FfiConverterULong.allocationSize(value.`messageId`) +
            FfiConverterLong.allocationSize(value.`seq`) +
            FfiConverterBoolean.allocationSize(value.`isDeleted`) +
            FfiConverterLong.allocationSize(value.`createdAt`)
    )

    override fun write(value: UpsertMessageReactionInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterULong.write(value.`uid`, buf)
            FfiConverterString.write(value.`name`, buf)
            FfiConverterString.write(value.`emoji`, buf)
            FfiConverterULong.write(value.`messageId`, buf)
            FfiConverterLong.write(value.`seq`, buf)
            FfiConverterBoolean.write(value.`isDeleted`, buf)
            FfiConverterLong.write(value.`createdAt`, buf)
    }
}




object FfiConverterTypeUpsertReminderInput: FfiConverterRustBuffer<UpsertReminderInput> {
    override fun read(buf: ByteBuffer): UpsertReminderInput {
        return UpsertReminderInput(
            FfiConverterULong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterULong.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterLong.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterOptionalULong.read(buf),
        )
    }

    override fun allocationSize(value: UpsertReminderInput) = (
            FfiConverterULong.allocationSize(value.`reminderId`) +
            FfiConverterULong.allocationSize(value.`messageId`) +
            FfiConverterLong.allocationSize(value.`pts`) +
            FfiConverterULong.allocationSize(value.`channelId`) +
            FfiConverterInt.allocationSize(value.`channelType`) +
            FfiConverterULong.allocationSize(value.`uid`) +
            FfiConverterInt.allocationSize(value.`reminderType`) +
            FfiConverterString.allocationSize(value.`text`) +
            FfiConverterString.allocationSize(value.`data`) +
            FfiConverterBoolean.allocationSize(value.`isLocate`) +
            FfiConverterLong.allocationSize(value.`version`) +
            FfiConverterBoolean.allocationSize(value.`done`) +
            FfiConverterBoolean.allocationSize(value.`needUpload`) +
            FfiConverterOptionalULong.allocationSize(value.`publisher`)
    )

    override fun write(value: UpsertReminderInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`reminderId`, buf)
            FfiConverterULong.write(value.`messageId`, buf)
            FfiConverterLong.write(value.`pts`, buf)
            FfiConverterULong.write(value.`channelId`, buf)
            FfiConverterInt.write(value.`channelType`, buf)
            FfiConverterULong.write(value.`uid`, buf)
            FfiConverterInt.write(value.`reminderType`, buf)
            FfiConverterString.write(value.`text`, buf)
            FfiConverterString.write(value.`data`, buf)
            FfiConverterBoolean.write(value.`isLocate`, buf)
            FfiConverterLong.write(value.`version`, buf)
            FfiConverterBoolean.write(value.`done`, buf)
            FfiConverterBoolean.write(value.`needUpload`, buf)
            FfiConverterOptionalULong.write(value.`publisher`, buf)
    }
}




object FfiConverterTypeUpsertUserInput: FfiConverterRustBuffer<UpsertUserInput> {
    override fun read(buf: ByteBuffer): UpsertUserInput {
        return UpsertUserInput(
            FfiConverterULong.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterOptionalString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterInt.read(buf),
            FfiConverterBoolean.read(buf),
            FfiConverterString.read(buf),
            FfiConverterLong.read(buf),
        )
    }

    override fun allocationSize(value: UpsertUserInput) = (
            FfiConverterULong.allocationSize(value.`userId`) +
            FfiConverterOptionalString.allocationSize(value.`username`) +
            FfiConverterOptionalString.allocationSize(value.`nickname`) +
            FfiConverterOptionalString.allocationSize(value.`alias`) +
            FfiConverterString.allocationSize(value.`avatar`) +
            FfiConverterInt.allocationSize(value.`userType`) +
            FfiConverterBoolean.allocationSize(value.`isDeleted`) +
            FfiConverterString.allocationSize(value.`channelId`) +
            FfiConverterLong.allocationSize(value.`updatedAt`)
    )

    override fun write(value: UpsertUserInput, buf: ByteBuffer) {
            FfiConverterULong.write(value.`userId`, buf)
            FfiConverterOptionalString.write(value.`username`, buf)
            FfiConverterOptionalString.write(value.`nickname`, buf)
            FfiConverterOptionalString.write(value.`alias`, buf)
            FfiConverterString.write(value.`avatar`, buf)
            FfiConverterInt.write(value.`userType`, buf)
            FfiConverterBoolean.write(value.`isDeleted`, buf)
            FfiConverterString.write(value.`channelId`, buf)
            FfiConverterLong.write(value.`updatedAt`, buf)
    }
}




object FfiConverterTypeUserQrCodeGenerateView: FfiConverterRustBuffer<UserQrCodeGenerateView> {
    override fun read(buf: ByteBuffer): UserQrCodeGenerateView {
        return UserQrCodeGenerateView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: UserQrCodeGenerateView) = (
            FfiConverterString.allocationSize(value.`qrKey`) +
            FfiConverterString.allocationSize(value.`qrCode`) +
            FfiConverterString.allocationSize(value.`createdAt`)
    )

    override fun write(value: UserQrCodeGenerateView, buf: ByteBuffer) {
            FfiConverterString.write(value.`qrKey`, buf)
            FfiConverterString.write(value.`qrCode`, buf)
            FfiConverterString.write(value.`createdAt`, buf)
    }
}




object FfiConverterTypeUserQrCodeGetView: FfiConverterRustBuffer<UserQrCodeGetView> {
    override fun read(buf: ByteBuffer): UserQrCodeGetView {
        return UserQrCodeGetView(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterUInt.read(buf),
        )
    }

    override fun allocationSize(value: UserQrCodeGetView) = (
            FfiConverterString.allocationSize(value.`qrKey`) +
            FfiConverterString.allocationSize(value.`qrCode`) +
            FfiConverterString.allocationSize(value.`createdAt`) +
            FfiConverterUInt.allocationSize(value.`usedCount`)
    )

    override fun write(value: UserQrCodeGetView, buf: ByteBuffer) {
            FfiConverterString.write(value.`qrKey`, buf)
            FfiConverterString.write(value.`qrCode`, buf)
            FfiConverterString.write(value.`createdAt`, buf)
            FfiConverterUInt.write(value.`usedCount`, buf)
    }
}




object FfiConverterTypeUserSettingsView: FfiConverterRustBuffer<UserSettingsView> {
    override fun read(buf: ByteBuffer): UserSettingsView {
        return UserSettingsView(
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: UserSettingsView) = (
            FfiConverterString.allocationSize(value.`settingsJson`)
    )

    override fun write(value: UserSettingsView, buf: ByteBuffer) {
            FfiConverterString.write(value.`settingsJson`, buf)
    }
}




object FfiConverterTypeUserStoragePaths: FfiConverterRustBuffer<UserStoragePaths> {
    override fun read(buf: ByteBuffer): UserStoragePaths {
        return UserStoragePaths(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
            FfiConverterSequenceString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: UserStoragePaths) = (
            FfiConverterString.allocationSize(value.`userRoot`) +
            FfiConverterString.allocationSize(value.`dbPath`) +
            FfiConverterString.allocationSize(value.`kvPath`) +
            FfiConverterString.allocationSize(value.`queueRoot`) +
            FfiConverterString.allocationSize(value.`normalQueuePath`) +
            FfiConverterSequenceString.allocationSize(value.`fileQueuePaths`) +
            FfiConverterString.allocationSize(value.`mediaRoot`)
    )

    override fun write(value: UserStoragePaths, buf: ByteBuffer) {
            FfiConverterString.write(value.`userRoot`, buf)
            FfiConverterString.write(value.`dbPath`, buf)
            FfiConverterString.write(value.`kvPath`, buf)
            FfiConverterString.write(value.`queueRoot`, buf)
            FfiConverterString.write(value.`normalQueuePath`, buf)
            FfiConverterSequenceString.write(value.`fileQueuePaths`, buf)
            FfiConverterString.write(value.`mediaRoot`, buf)
    }
}





object FfiConverterTypeConnectionState: FfiConverterRustBuffer<ConnectionState> {
    override fun read(buf: ByteBuffer) = try {
        ConnectionState.values()[buf.getInt() - 1]
    } catch (e: IndexOutOfBoundsException) {
        throw RuntimeException("invalid enum value, something is very wrong!!", e)
    }

    override fun allocationSize(value: ConnectionState) = 4UL

    override fun write(value: ConnectionState, buf: ByteBuffer) {
        buf.putInt(value.ordinal + 1)
    }
}







object FfiConverterTypeNetworkHint: FfiConverterRustBuffer<NetworkHint> {
    override fun read(buf: ByteBuffer) = try {
        NetworkHint.values()[buf.getInt() - 1]
    } catch (e: IndexOutOfBoundsException) {
        throw RuntimeException("invalid enum value, something is very wrong!!", e)
    }

    override fun allocationSize(value: NetworkHint) = 4UL

    override fun write(value: NetworkHint, buf: ByteBuffer) {
        buf.putInt(value.ordinal + 1)
    }
}






object PrivchatFfiExceptionErrorHandler : UniffiRustCallStatusErrorHandler<PrivchatFfiException> {
    override fun lift(errorBuf: RustBufferByValue): PrivchatFfiException = FfiConverterTypePrivchatFfiError.lift(errorBuf)
}

object FfiConverterTypePrivchatFfiError : FfiConverterRustBuffer<PrivchatFfiException> {
    override fun read(buf: ByteBuffer): PrivchatFfiException {
        

        return when(buf.getInt()) {
            1 -> PrivchatFfiException.SdkException(
                FfiConverterUInt.read(buf),
                FfiConverterString.read(buf),
                )
            else -> throw RuntimeException("invalid error enum value, something is very wrong!!")
        }
    }

    override fun allocationSize(value: PrivchatFfiException): ULong {
        return when(value) {
            is PrivchatFfiException.SdkException -> (
                // Add the size for the Int that specifies the variant plus the size needed for all fields
                4UL
                + FfiConverterUInt.allocationSize(value.`code`)
                + FfiConverterString.allocationSize(value.`detail`)
            )
        }
    }

    override fun write(value: PrivchatFfiException, buf: ByteBuffer) {
        when(value) {
            is PrivchatFfiException.SdkException -> {
                buf.putInt(1)
                FfiConverterUInt.write(value.`code`, buf)
                FfiConverterString.write(value.`detail`, buf)
                Unit
            }
        }.let { /* this makes the `when` an expression, which ensures it is exhaustive */ }
    }

}





object FfiConverterTypeSdkEvent : FfiConverterRustBuffer<SdkEvent>{
    override fun read(buf: ByteBuffer): SdkEvent {
        return when(buf.getInt()) {
            1 -> SdkEvent.ConnectionStateChanged(
                FfiConverterTypeConnectionState.read(buf),
                FfiConverterTypeConnectionState.read(buf),
                )
            2 -> SdkEvent.BootstrapCompleted(
                FfiConverterULong.read(buf),
                )
            3 -> SdkEvent.ShutdownStarted
            4 -> SdkEvent.ShutdownCompleted
            else -> throw RuntimeException("invalid enum value, something is very wrong!!")
        }
    }

    override fun allocationSize(value: SdkEvent) = when(value) {
        is SdkEvent.ConnectionStateChanged -> {
            // Add the size for the Int that specifies the variant plus the size needed for all fields
            (
                4UL
                + FfiConverterTypeConnectionState.allocationSize(value.`from`)
                + FfiConverterTypeConnectionState.allocationSize(value.`to`)
            )
        }
        is SdkEvent.BootstrapCompleted -> {
            // Add the size for the Int that specifies the variant plus the size needed for all fields
            (
                4UL
                + FfiConverterULong.allocationSize(value.`userId`)
            )
        }
        is SdkEvent.ShutdownStarted -> {
            // Add the size for the Int that specifies the variant plus the size needed for all fields
            (
                4UL
            )
        }
        is SdkEvent.ShutdownCompleted -> {
            // Add the size for the Int that specifies the variant plus the size needed for all fields
            (
                4UL
            )
        }
    }

    override fun write(value: SdkEvent, buf: ByteBuffer) {
        when(value) {
            is SdkEvent.ConnectionStateChanged -> {
                buf.putInt(1)
                FfiConverterTypeConnectionState.write(value.`from`, buf)
                FfiConverterTypeConnectionState.write(value.`to`, buf)
                Unit
            }
            is SdkEvent.BootstrapCompleted -> {
                buf.putInt(2)
                FfiConverterULong.write(value.`userId`, buf)
                Unit
            }
            is SdkEvent.ShutdownStarted -> {
                buf.putInt(3)
                Unit
            }
            is SdkEvent.ShutdownCompleted -> {
                buf.putInt(4)
                Unit
            }
        }.let { /* this makes the `when` an expression, which ensures it is exhaustive */ }
    }
}







object FfiConverterTypeTransportProtocol: FfiConverterRustBuffer<TransportProtocol> {
    override fun read(buf: ByteBuffer) = try {
        TransportProtocol.values()[buf.getInt() - 1]
    } catch (e: IndexOutOfBoundsException) {
        throw RuntimeException("invalid enum value, something is very wrong!!", e)
    }

    override fun allocationSize(value: TransportProtocol) = 4UL

    override fun write(value: TransportProtocol, buf: ByteBuffer) {
        buf.putInt(value.ordinal + 1)
    }
}







object FfiConverterTypeTypingActionType: FfiConverterRustBuffer<TypingActionType> {
    override fun read(buf: ByteBuffer) = try {
        TypingActionType.values()[buf.getInt() - 1]
    } catch (e: IndexOutOfBoundsException) {
        throw RuntimeException("invalid enum value, something is very wrong!!", e)
    }

    override fun allocationSize(value: TypingActionType) = 4UL

    override fun write(value: TypingActionType, buf: ByteBuffer) {
        buf.putInt(value.ordinal + 1)
    }
}






public object FfiConverterOptionalUInt: FfiConverterRustBuffer<kotlin.UInt?> {
    override fun read(buf: ByteBuffer): kotlin.UInt? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterUInt.read(buf)
    }

    override fun allocationSize(value: kotlin.UInt?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterUInt.allocationSize(value)
        }
    }

    override fun write(value: kotlin.UInt?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterUInt.write(value, buf)
        }
    }
}




public object FfiConverterOptionalULong: FfiConverterRustBuffer<kotlin.ULong?> {
    override fun read(buf: ByteBuffer): kotlin.ULong? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterULong.read(buf)
    }

    override fun allocationSize(value: kotlin.ULong?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterULong.allocationSize(value)
        }
    }

    override fun write(value: kotlin.ULong?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterULong.write(value, buf)
        }
    }
}




public object FfiConverterOptionalLong: FfiConverterRustBuffer<kotlin.Long?> {
    override fun read(buf: ByteBuffer): kotlin.Long? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterLong.read(buf)
    }

    override fun allocationSize(value: kotlin.Long?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterLong.allocationSize(value)
        }
    }

    override fun write(value: kotlin.Long?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterLong.write(value, buf)
        }
    }
}




public object FfiConverterOptionalBoolean: FfiConverterRustBuffer<kotlin.Boolean?> {
    override fun read(buf: ByteBuffer): kotlin.Boolean? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterBoolean.read(buf)
    }

    override fun allocationSize(value: kotlin.Boolean?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterBoolean.allocationSize(value)
        }
    }

    override fun write(value: kotlin.Boolean?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterBoolean.write(value, buf)
        }
    }
}




public object FfiConverterOptionalString: FfiConverterRustBuffer<kotlin.String?> {
    override fun read(buf: ByteBuffer): kotlin.String? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterString.read(buf)
    }

    override fun allocationSize(value: kotlin.String?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterString.allocationSize(value)
        }
    }

    override fun write(value: kotlin.String?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterString.write(value, buf)
        }
    }
}




public object FfiConverterOptionalByteArray: FfiConverterRustBuffer<kotlin.ByteArray?> {
    override fun read(buf: ByteBuffer): kotlin.ByteArray? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterByteArray.read(buf)
    }

    override fun allocationSize(value: kotlin.ByteArray?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterByteArray.allocationSize(value)
        }
    }

    override fun write(value: kotlin.ByteArray?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterByteArray.write(value, buf)
        }
    }
}




public object FfiConverterOptionalTypePresenceStatus: FfiConverterRustBuffer<PresenceStatus?> {
    override fun read(buf: ByteBuffer): PresenceStatus? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterTypePresenceStatus.read(buf)
    }

    override fun allocationSize(value: PresenceStatus?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterTypePresenceStatus.allocationSize(value)
        }
    }

    override fun write(value: PresenceStatus?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterTypePresenceStatus.write(value, buf)
        }
    }
}




public object FfiConverterOptionalTypeSequencedSdkEvent: FfiConverterRustBuffer<SequencedSdkEvent?> {
    override fun read(buf: ByteBuffer): SequencedSdkEvent? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterTypeSequencedSdkEvent.read(buf)
    }

    override fun allocationSize(value: SequencedSdkEvent?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterTypeSequencedSdkEvent.allocationSize(value)
        }
    }

    override fun write(value: SequencedSdkEvent?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterTypeSequencedSdkEvent.write(value, buf)
        }
    }
}




public object FfiConverterOptionalTypeSessionSnapshot: FfiConverterRustBuffer<SessionSnapshot?> {
    override fun read(buf: ByteBuffer): SessionSnapshot? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterTypeSessionSnapshot.read(buf)
    }

    override fun allocationSize(value: SessionSnapshot?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterTypeSessionSnapshot.allocationSize(value)
        }
    }

    override fun write(value: SessionSnapshot?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterTypeSessionSnapshot.write(value, buf)
        }
    }
}




public object FfiConverterOptionalTypeStoredChannel: FfiConverterRustBuffer<StoredChannel?> {
    override fun read(buf: ByteBuffer): StoredChannel? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterTypeStoredChannel.read(buf)
    }

    override fun allocationSize(value: StoredChannel?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterTypeStoredChannel.allocationSize(value)
        }
    }

    override fun write(value: StoredChannel?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterTypeStoredChannel.write(value, buf)
        }
    }
}




public object FfiConverterOptionalTypeStoredChannelExtra: FfiConverterRustBuffer<StoredChannelExtra?> {
    override fun read(buf: ByteBuffer): StoredChannelExtra? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterTypeStoredChannelExtra.read(buf)
    }

    override fun allocationSize(value: StoredChannelExtra?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterTypeStoredChannelExtra.allocationSize(value)
        }
    }

    override fun write(value: StoredChannelExtra?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterTypeStoredChannelExtra.write(value, buf)
        }
    }
}




public object FfiConverterOptionalTypeStoredGroup: FfiConverterRustBuffer<StoredGroup?> {
    override fun read(buf: ByteBuffer): StoredGroup? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterTypeStoredGroup.read(buf)
    }

    override fun allocationSize(value: StoredGroup?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterTypeStoredGroup.allocationSize(value)
        }
    }

    override fun write(value: StoredGroup?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterTypeStoredGroup.write(value, buf)
        }
    }
}




public object FfiConverterOptionalTypeStoredMessage: FfiConverterRustBuffer<StoredMessage?> {
    override fun read(buf: ByteBuffer): StoredMessage? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterTypeStoredMessage.read(buf)
    }

    override fun allocationSize(value: StoredMessage?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterTypeStoredMessage.allocationSize(value)
        }
    }

    override fun write(value: StoredMessage?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterTypeStoredMessage.write(value, buf)
        }
    }
}




public object FfiConverterOptionalTypeStoredMessageExtra: FfiConverterRustBuffer<StoredMessageExtra?> {
    override fun read(buf: ByteBuffer): StoredMessageExtra? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterTypeStoredMessageExtra.read(buf)
    }

    override fun allocationSize(value: StoredMessageExtra?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterTypeStoredMessageExtra.allocationSize(value)
        }
    }

    override fun write(value: StoredMessageExtra?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterTypeStoredMessageExtra.write(value, buf)
        }
    }
}




public object FfiConverterOptionalTypeStoredUser: FfiConverterRustBuffer<StoredUser?> {
    override fun read(buf: ByteBuffer): StoredUser? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterTypeStoredUser.read(buf)
    }

    override fun allocationSize(value: StoredUser?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterTypeStoredUser.allocationSize(value)
        }
    }

    override fun write(value: StoredUser?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterTypeStoredUser.write(value, buf)
        }
    }
}




public object FfiConverterOptionalTypeSdkEvent: FfiConverterRustBuffer<SdkEvent?> {
    override fun read(buf: ByteBuffer): SdkEvent? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterTypeSdkEvent.read(buf)
    }

    override fun allocationSize(value: SdkEvent?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterTypeSdkEvent.allocationSize(value)
        }
    }

    override fun write(value: SdkEvent?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterTypeSdkEvent.write(value, buf)
        }
    }
}




public object FfiConverterOptionalSequenceULong: FfiConverterRustBuffer<List<kotlin.ULong>?> {
    override fun read(buf: ByteBuffer): List<kotlin.ULong>? {
        if (buf.get().toInt() == 0) {
            return null
        }
        return FfiConverterSequenceULong.read(buf)
    }

    override fun allocationSize(value: List<kotlin.ULong>?): ULong {
        if (value == null) {
            return 1UL
        } else {
            return 1UL + FfiConverterSequenceULong.allocationSize(value)
        }
    }

    override fun write(value: List<kotlin.ULong>?, buf: ByteBuffer) {
        if (value == null) {
            buf.put(0)
        } else {
            buf.put(1)
            FfiConverterSequenceULong.write(value, buf)
        }
    }
}




public object FfiConverterSequenceULong: FfiConverterRustBuffer<List<kotlin.ULong>> {
    override fun read(buf: ByteBuffer): List<kotlin.ULong> {
        val len = buf.getInt()
        return List<kotlin.ULong>(len) {
            FfiConverterULong.read(buf)
        }
    }

    override fun allocationSize(value: List<kotlin.ULong>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterULong.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<kotlin.ULong>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterULong.write(it, buf)
        }
    }
}




public object FfiConverterSequenceString: FfiConverterRustBuffer<List<kotlin.String>> {
    override fun read(buf: ByteBuffer): List<kotlin.String> {
        val len = buf.getInt()
        return List<kotlin.String>(len) {
            FfiConverterString.read(buf)
        }
    }

    override fun allocationSize(value: List<kotlin.String>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterString.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<kotlin.String>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterString.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeChannelPtsView: FfiConverterRustBuffer<List<ChannelPtsView>> {
    override fun read(buf: ByteBuffer): List<ChannelPtsView> {
        val len = buf.getInt()
        return List<ChannelPtsView>(len) {
            FfiConverterTypeChannelPtsView.read(buf)
        }
    }

    override fun allocationSize(value: List<ChannelPtsView>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeChannelPtsView.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<ChannelPtsView>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeChannelPtsView.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeDeviceInfoView: FfiConverterRustBuffer<List<DeviceInfoView>> {
    override fun read(buf: ByteBuffer): List<DeviceInfoView> {
        val len = buf.getInt()
        return List<DeviceInfoView>(len) {
            FfiConverterTypeDeviceInfoView.read(buf)
        }
    }

    override fun allocationSize(value: List<DeviceInfoView>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeDeviceInfoView.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<DeviceInfoView>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeDeviceInfoView.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeDevicePushInfoView: FfiConverterRustBuffer<List<DevicePushInfoView>> {
    override fun read(buf: ByteBuffer): List<DevicePushInfoView> {
        val len = buf.getInt()
        return List<DevicePushInfoView>(len) {
            FfiConverterTypeDevicePushInfoView.read(buf)
        }
    }

    override fun allocationSize(value: List<DevicePushInfoView>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeDevicePushInfoView.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<DevicePushInfoView>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeDevicePushInfoView.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeFriendPendingEntry: FfiConverterRustBuffer<List<FriendPendingEntry>> {
    override fun read(buf: ByteBuffer): List<FriendPendingEntry> {
        val len = buf.getInt()
        return List<FriendPendingEntry>(len) {
            FfiConverterTypeFriendPendingEntry.read(buf)
        }
    }

    override fun allocationSize(value: List<FriendPendingEntry>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeFriendPendingEntry.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<FriendPendingEntry>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeFriendPendingEntry.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeGetChannelPtsInput: FfiConverterRustBuffer<List<GetChannelPtsInput>> {
    override fun read(buf: ByteBuffer): List<GetChannelPtsInput> {
        val len = buf.getInt()
        return List<GetChannelPtsInput>(len) {
            FfiConverterTypeGetChannelPtsInput.read(buf)
        }
    }

    override fun allocationSize(value: List<GetChannelPtsInput>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeGetChannelPtsInput.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<GetChannelPtsInput>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeGetChannelPtsInput.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeGetDifferenceCommitView: FfiConverterRustBuffer<List<GetDifferenceCommitView>> {
    override fun read(buf: ByteBuffer): List<GetDifferenceCommitView> {
        val len = buf.getInt()
        return List<GetDifferenceCommitView>(len) {
            FfiConverterTypeGetDifferenceCommitView.read(buf)
        }
    }

    override fun allocationSize(value: List<GetDifferenceCommitView>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeGetDifferenceCommitView.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<GetDifferenceCommitView>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeGetDifferenceCommitView.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeGroupApprovalItemView: FfiConverterRustBuffer<List<GroupApprovalItemView>> {
    override fun read(buf: ByteBuffer): List<GroupApprovalItemView> {
        val len = buf.getInt()
        return List<GroupApprovalItemView>(len) {
            FfiConverterTypeGroupApprovalItemView.read(buf)
        }
    }

    override fun allocationSize(value: List<GroupApprovalItemView>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeGroupApprovalItemView.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<GroupApprovalItemView>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeGroupApprovalItemView.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeGroupMemberRemoteEntry: FfiConverterRustBuffer<List<GroupMemberRemoteEntry>> {
    override fun read(buf: ByteBuffer): List<GroupMemberRemoteEntry> {
        val len = buf.getInt()
        return List<GroupMemberRemoteEntry>(len) {
            FfiConverterTypeGroupMemberRemoteEntry.read(buf)
        }
    }

    override fun allocationSize(value: List<GroupMemberRemoteEntry>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeGroupMemberRemoteEntry.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<GroupMemberRemoteEntry>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeGroupMemberRemoteEntry.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeLocalAccountSummary: FfiConverterRustBuffer<List<LocalAccountSummary>> {
    override fun read(buf: ByteBuffer): List<LocalAccountSummary> {
        val len = buf.getInt()
        return List<LocalAccountSummary>(len) {
            FfiConverterTypeLocalAccountSummary.read(buf)
        }
    }

    override fun allocationSize(value: List<LocalAccountSummary>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeLocalAccountSummary.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<LocalAccountSummary>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeLocalAccountSummary.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeMessageHistoryItemView: FfiConverterRustBuffer<List<MessageHistoryItemView>> {
    override fun read(buf: ByteBuffer): List<MessageHistoryItemView> {
        val len = buf.getInt()
        return List<MessageHistoryItemView>(len) {
            FfiConverterTypeMessageHistoryItemView.read(buf)
        }
    }

    override fun allocationSize(value: List<MessageHistoryItemView>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeMessageHistoryItemView.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<MessageHistoryItemView>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeMessageHistoryItemView.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeMessageReactionEmojiUsersView: FfiConverterRustBuffer<List<MessageReactionEmojiUsersView>> {
    override fun read(buf: ByteBuffer): List<MessageReactionEmojiUsersView> {
        val len = buf.getInt()
        return List<MessageReactionEmojiUsersView>(len) {
            FfiConverterTypeMessageReactionEmojiUsersView.read(buf)
        }
    }

    override fun allocationSize(value: List<MessageReactionEmojiUsersView>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeMessageReactionEmojiUsersView.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<MessageReactionEmojiUsersView>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeMessageReactionEmojiUsersView.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeMessageReadUserView: FfiConverterRustBuffer<List<MessageReadUserView>> {
    override fun read(buf: ByteBuffer): List<MessageReadUserView> {
        val len = buf.getInt()
        return List<MessageReadUserView>(len) {
            FfiConverterTypeMessageReadUserView.read(buf)
        }
    }

    override fun allocationSize(value: List<MessageReadUserView>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeMessageReadUserView.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<MessageReadUserView>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeMessageReadUserView.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypePresenceStatus: FfiConverterRustBuffer<List<PresenceStatus>> {
    override fun read(buf: ByteBuffer): List<PresenceStatus> {
        val len = buf.getInt()
        return List<PresenceStatus>(len) {
            FfiConverterTypePresenceStatus.read(buf)
        }
    }

    override fun allocationSize(value: List<PresenceStatus>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypePresenceStatus.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<PresenceStatus>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypePresenceStatus.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeQrCodeEntryView: FfiConverterRustBuffer<List<QrCodeEntryView>> {
    override fun read(buf: ByteBuffer): List<QrCodeEntryView> {
        val len = buf.getInt()
        return List<QrCodeEntryView>(len) {
            FfiConverterTypeQrCodeEntryView.read(buf)
        }
    }

    override fun allocationSize(value: List<QrCodeEntryView>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeQrCodeEntryView.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<QrCodeEntryView>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeQrCodeEntryView.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeQueueMessage: FfiConverterRustBuffer<List<QueueMessage>> {
    override fun read(buf: ByteBuffer): List<QueueMessage> {
        val len = buf.getInt()
        return List<QueueMessage>(len) {
            FfiConverterTypeQueueMessage.read(buf)
        }
    }

    override fun allocationSize(value: List<QueueMessage>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeQueueMessage.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<QueueMessage>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeQueueMessage.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeReactionsBatchItemView: FfiConverterRustBuffer<List<ReactionsBatchItemView>> {
    override fun read(buf: ByteBuffer): List<ReactionsBatchItemView> {
        val len = buf.getInt()
        return List<ReactionsBatchItemView>(len) {
            FfiConverterTypeReactionsBatchItemView.read(buf)
        }
    }

    override fun allocationSize(value: List<ReactionsBatchItemView>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeReactionsBatchItemView.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<ReactionsBatchItemView>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeReactionsBatchItemView.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeSearchUserEntry: FfiConverterRustBuffer<List<SearchUserEntry>> {
    override fun read(buf: ByteBuffer): List<SearchUserEntry> {
        val len = buf.getInt()
        return List<SearchUserEntry>(len) {
            FfiConverterTypeSearchUserEntry.read(buf)
        }
    }

    override fun allocationSize(value: List<SearchUserEntry>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeSearchUserEntry.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<SearchUserEntry>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeSearchUserEntry.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeSeenByEntry: FfiConverterRustBuffer<List<SeenByEntry>> {
    override fun read(buf: ByteBuffer): List<SeenByEntry> {
        val len = buf.getInt()
        return List<SeenByEntry>(len) {
            FfiConverterTypeSeenByEntry.read(buf)
        }
    }

    override fun allocationSize(value: List<SeenByEntry>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeSeenByEntry.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<SeenByEntry>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeSeenByEntry.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeSequencedSdkEvent: FfiConverterRustBuffer<List<SequencedSdkEvent>> {
    override fun read(buf: ByteBuffer): List<SequencedSdkEvent> {
        val len = buf.getInt()
        return List<SequencedSdkEvent>(len) {
            FfiConverterTypeSequencedSdkEvent.read(buf)
        }
    }

    override fun allocationSize(value: List<SequencedSdkEvent>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeSequencedSdkEvent.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<SequencedSdkEvent>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeSequencedSdkEvent.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeServerEndpoint: FfiConverterRustBuffer<List<ServerEndpoint>> {
    override fun read(buf: ByteBuffer): List<ServerEndpoint> {
        val len = buf.getInt()
        return List<ServerEndpoint>(len) {
            FfiConverterTypeServerEndpoint.read(buf)
        }
    }

    override fun allocationSize(value: List<ServerEndpoint>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeServerEndpoint.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<ServerEndpoint>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeServerEndpoint.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeStickerInfoView: FfiConverterRustBuffer<List<StickerInfoView>> {
    override fun read(buf: ByteBuffer): List<StickerInfoView> {
        val len = buf.getInt()
        return List<StickerInfoView>(len) {
            FfiConverterTypeStickerInfoView.read(buf)
        }
    }

    override fun allocationSize(value: List<StickerInfoView>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeStickerInfoView.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<StickerInfoView>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeStickerInfoView.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeStickerPackageInfoView: FfiConverterRustBuffer<List<StickerPackageInfoView>> {
    override fun read(buf: ByteBuffer): List<StickerPackageInfoView> {
        val len = buf.getInt()
        return List<StickerPackageInfoView>(len) {
            FfiConverterTypeStickerPackageInfoView.read(buf)
        }
    }

    override fun allocationSize(value: List<StickerPackageInfoView>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeStickerPackageInfoView.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<StickerPackageInfoView>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeStickerPackageInfoView.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeStoredBlacklistEntry: FfiConverterRustBuffer<List<StoredBlacklistEntry>> {
    override fun read(buf: ByteBuffer): List<StoredBlacklistEntry> {
        val len = buf.getInt()
        return List<StoredBlacklistEntry>(len) {
            FfiConverterTypeStoredBlacklistEntry.read(buf)
        }
    }

    override fun allocationSize(value: List<StoredBlacklistEntry>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeStoredBlacklistEntry.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<StoredBlacklistEntry>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeStoredBlacklistEntry.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeStoredChannel: FfiConverterRustBuffer<List<StoredChannel>> {
    override fun read(buf: ByteBuffer): List<StoredChannel> {
        val len = buf.getInt()
        return List<StoredChannel>(len) {
            FfiConverterTypeStoredChannel.read(buf)
        }
    }

    override fun allocationSize(value: List<StoredChannel>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeStoredChannel.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<StoredChannel>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeStoredChannel.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeStoredChannelMember: FfiConverterRustBuffer<List<StoredChannelMember>> {
    override fun read(buf: ByteBuffer): List<StoredChannelMember> {
        val len = buf.getInt()
        return List<StoredChannelMember>(len) {
            FfiConverterTypeStoredChannelMember.read(buf)
        }
    }

    override fun allocationSize(value: List<StoredChannelMember>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeStoredChannelMember.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<StoredChannelMember>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeStoredChannelMember.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeStoredFriend: FfiConverterRustBuffer<List<StoredFriend>> {
    override fun read(buf: ByteBuffer): List<StoredFriend> {
        val len = buf.getInt()
        return List<StoredFriend>(len) {
            FfiConverterTypeStoredFriend.read(buf)
        }
    }

    override fun allocationSize(value: List<StoredFriend>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeStoredFriend.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<StoredFriend>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeStoredFriend.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeStoredGroup: FfiConverterRustBuffer<List<StoredGroup>> {
    override fun read(buf: ByteBuffer): List<StoredGroup> {
        val len = buf.getInt()
        return List<StoredGroup>(len) {
            FfiConverterTypeStoredGroup.read(buf)
        }
    }

    override fun allocationSize(value: List<StoredGroup>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeStoredGroup.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<StoredGroup>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeStoredGroup.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeStoredGroupMember: FfiConverterRustBuffer<List<StoredGroupMember>> {
    override fun read(buf: ByteBuffer): List<StoredGroupMember> {
        val len = buf.getInt()
        return List<StoredGroupMember>(len) {
            FfiConverterTypeStoredGroupMember.read(buf)
        }
    }

    override fun allocationSize(value: List<StoredGroupMember>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeStoredGroupMember.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<StoredGroupMember>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeStoredGroupMember.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeStoredMessage: FfiConverterRustBuffer<List<StoredMessage>> {
    override fun read(buf: ByteBuffer): List<StoredMessage> {
        val len = buf.getInt()
        return List<StoredMessage>(len) {
            FfiConverterTypeStoredMessage.read(buf)
        }
    }

    override fun allocationSize(value: List<StoredMessage>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeStoredMessage.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<StoredMessage>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeStoredMessage.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeStoredMessageReaction: FfiConverterRustBuffer<List<StoredMessageReaction>> {
    override fun read(buf: ByteBuffer): List<StoredMessageReaction> {
        val len = buf.getInt()
        return List<StoredMessageReaction>(len) {
            FfiConverterTypeStoredMessageReaction.read(buf)
        }
    }

    override fun allocationSize(value: List<StoredMessageReaction>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeStoredMessageReaction.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<StoredMessageReaction>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeStoredMessageReaction.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeStoredReminder: FfiConverterRustBuffer<List<StoredReminder>> {
    override fun read(buf: ByteBuffer): List<StoredReminder> {
        val len = buf.getInt()
        return List<StoredReminder>(len) {
            FfiConverterTypeStoredReminder.read(buf)
        }
    }

    override fun allocationSize(value: List<StoredReminder>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeStoredReminder.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<StoredReminder>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeStoredReminder.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeStoredUser: FfiConverterRustBuffer<List<StoredUser>> {
    override fun read(buf: ByteBuffer): List<StoredUser> {
        val len = buf.getInt()
        return List<StoredUser>(len) {
            FfiConverterTypeStoredUser.read(buf)
        }
    }

    override fun allocationSize(value: List<StoredUser>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeStoredUser.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<StoredUser>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeStoredUser.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeSyncEntityItemView: FfiConverterRustBuffer<List<SyncEntityItemView>> {
    override fun read(buf: ByteBuffer): List<SyncEntityItemView> {
        val len = buf.getInt()
        return List<SyncEntityItemView>(len) {
            FfiConverterTypeSyncEntityItemView.read(buf)
        }
    }

    override fun allocationSize(value: List<SyncEntityItemView>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeSyncEntityItemView.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<SyncEntityItemView>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeSyncEntityItemView.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeSyncPayloadEntry: FfiConverterRustBuffer<List<SyncPayloadEntry>> {
    override fun read(buf: ByteBuffer): List<SyncPayloadEntry> {
        val len = buf.getInt()
        return List<SyncPayloadEntry>(len) {
            FfiConverterTypeSyncPayloadEntry.read(buf)
        }
    }

    override fun allocationSize(value: List<SyncPayloadEntry>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeSyncPayloadEntry.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<SyncPayloadEntry>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeSyncPayloadEntry.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeTypingChannelView: FfiConverterRustBuffer<List<TypingChannelView>> {
    override fun read(buf: ByteBuffer): List<TypingChannelView> {
        val len = buf.getInt()
        return List<TypingChannelView>(len) {
            FfiConverterTypeTypingChannelView.read(buf)
        }
    }

    override fun allocationSize(value: List<TypingChannelView>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeTypingChannelView.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<TypingChannelView>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeTypingChannelView.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeUnreadMentionCount: FfiConverterRustBuffer<List<UnreadMentionCount>> {
    override fun read(buf: ByteBuffer): List<UnreadMentionCount> {
        val len = buf.getInt()
        return List<UnreadMentionCount>(len) {
            FfiConverterTypeUnreadMentionCount.read(buf)
        }
    }

    override fun allocationSize(value: List<UnreadMentionCount>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.map { FfiConverterTypeUnreadMentionCount.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<UnreadMentionCount>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            FfiConverterTypeUnreadMentionCount.write(it, buf)
        }
    }
}













actual fun `buildTime`(): kotlin.String {
            return FfiConverterString.lift(
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_func_build_time(
        _status)!!
}
    )
    }
    


actual fun `gitSha`(): kotlin.String {
            return FfiConverterString.lift(
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_func_git_sha(
        _status)!!
}
    )
    }
    


actual fun `sdkVersion`(): kotlin.String {
            return FfiConverterString.lift(
    uniffiRustCall() { _status ->
    UniffiLib.INSTANCE.uniffi_privchat_sdk_ffi_fn_func_sdk_version(
        _status)!!
}
    )
    }
    



