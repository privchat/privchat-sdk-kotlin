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

typedef void (*UniffiForeignFutureFree)(int64_t
    );

typedef void (*UniffiCallbackInterfaceFree)(int64_t
    );

typedef struct UniffiForeignFuture {
    int64_t handle;
    UniffiForeignFutureFree free;
} UniffiForeignFuture;

typedef struct UniffiForeignFutureStructU8 {
    int8_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureStructU8;

typedef void (*UniffiForeignFutureCompleteU8)(int64_t, UniffiForeignFutureStructU8
    );

typedef struct UniffiForeignFutureStructI8 {
    int8_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureStructI8;

typedef void (*UniffiForeignFutureCompleteI8)(int64_t, UniffiForeignFutureStructI8
    );

typedef struct UniffiForeignFutureStructU16 {
    int16_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureStructU16;

typedef void (*UniffiForeignFutureCompleteU16)(int64_t, UniffiForeignFutureStructU16
    );

typedef struct UniffiForeignFutureStructI16 {
    int16_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureStructI16;

typedef void (*UniffiForeignFutureCompleteI16)(int64_t, UniffiForeignFutureStructI16
    );

typedef struct UniffiForeignFutureStructU32 {
    int32_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureStructU32;

typedef void (*UniffiForeignFutureCompleteU32)(int64_t, UniffiForeignFutureStructU32
    );

typedef struct UniffiForeignFutureStructI32 {
    int32_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureStructI32;

typedef void (*UniffiForeignFutureCompleteI32)(int64_t, UniffiForeignFutureStructI32
    );

typedef struct UniffiForeignFutureStructU64 {
    int64_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureStructU64;

typedef void (*UniffiForeignFutureCompleteU64)(int64_t, UniffiForeignFutureStructU64
    );

typedef struct UniffiForeignFutureStructI64 {
    int64_t returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureStructI64;

typedef void (*UniffiForeignFutureCompleteI64)(int64_t, UniffiForeignFutureStructI64
    );

typedef struct UniffiForeignFutureStructF32 {
    float returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureStructF32;

typedef void (*UniffiForeignFutureCompleteF32)(int64_t, UniffiForeignFutureStructF32
    );

typedef struct UniffiForeignFutureStructF64 {
    double returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureStructF64;

typedef void (*UniffiForeignFutureCompleteF64)(int64_t, UniffiForeignFutureStructF64
    );

typedef struct UniffiForeignFutureStructPointer {
    void * returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureStructPointer;

typedef void (*UniffiForeignFutureCompletePointer)(int64_t, UniffiForeignFutureStructPointer
    );

typedef struct UniffiForeignFutureStructRustBuffer {
    RustBuffer returnValue;
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureStructRustBuffer;

typedef void (*UniffiForeignFutureCompleteRustBuffer)(int64_t, UniffiForeignFutureStructRustBuffer
    );

typedef struct UniffiForeignFutureStructVoid {
    UniffiRustCallStatus callStatus;
} UniffiForeignFutureStructVoid;

typedef void (*UniffiForeignFutureCompleteVoid)(int64_t, UniffiForeignFutureStructVoid
    );

void * uniffi_privchat_sdk_ffi_fn_clone_privchatclient(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
void uniffi_privchat_sdk_ffi_fn_free_privchatclient(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
void * uniffi_privchat_sdk_ffi_fn_constructor_privchatclient_new(RustBuffer config, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_accept_friend_request(void * ptr, int64_t fromUserId, RustBuffer message
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_detail_remote(void * ptr, int64_t userId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_share_card_remote(void * ptr, int64_t userId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_account_user_update_remote(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_ack_outbound_files(void * ptr, int64_t queueIndex, RustBuffer messageIds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_ack_outbound_messages(void * ptr, RustBuffer messageIds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_channel_members(void * ptr, int64_t channelId, int32_t channelType, RustBuffer memberUids
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_reaction(void * ptr, int64_t serverMessageId, RustBuffer channelId, RustBuffer emoji
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_reaction_blocking(void * ptr, int64_t serverMessageId, RustBuffer channelId, RustBuffer emoji
);
void uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_server(void * ptr, RustBuffer endpoint, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_add_to_blacklist(void * ptr, int64_t blockedUserId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_assets_dir(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_auth_logout_remote(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_auth_refresh_remote(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_authenticate(void * ptr, int64_t userId, RustBuffer token, RustBuffer deviceId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_batch_get_presence(void * ptr, RustBuffer userIds
);
RustBuffer uniffi_privchat_sdk_ffi_fn_method_privchatclient_build(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
RustBuffer uniffi_privchat_sdk_ffi_fn_method_privchatclient_builder(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_create_remote(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_list_remote(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_broadcast_subscribe_remote(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_content_list_remote(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_content_publish_remote(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_notification_mode(void * ptr, int64_t channelId, int32_t channelType
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_send_queue_set_enabled(void * ptr, int64_t channelId, int32_t channelType, int8_t enabled
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_tags(void * ptr, int64_t channelId, int32_t channelType
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_channel_unread_stats(void * ptr, int64_t channelId, int32_t channelType
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_check_blacklist(void * ptr, int64_t targetUserId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_check_friend(void * ptr, int64_t friendId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_clear_local_state(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_clear_presence_cache(void * ptr
);
RustBuffer uniffi_privchat_sdk_ffi_fn_method_privchatclient_config(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_connect(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_connect_blocking(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_connection_state(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_connection_timeout(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_create_group(void * ptr, RustBuffer name, RustBuffer description, RustBuffer memberIds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_create_local_message(void * ptr, RustBuffer input
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_current_user_id(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_data_dir(void * ptr
);
int8_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_debug_mode(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_blacklist_entry(void * ptr, int64_t blockedUserId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_channel_member(void * ptr, int64_t channelId, int32_t channelType, int64_t memberUid
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_delete_friend(void * ptr, int64_t friendId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_disconnect(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_dm_peer_user_id(void * ptr, int64_t channelId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_cache(void * ptr, RustBuffer sourcePath, RustBuffer fileName
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_message_dir(void * ptr, RustBuffer sourcePath, int64_t messageId, RustBuffer fileName
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_download_attachment_to_path(void * ptr, RustBuffer sourcePath, RustBuffer targetPath
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_edit_message(void * ptr, int64_t messageId, RustBuffer content, int32_t editedAt
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_edit_message_blocking(void * ptr, int64_t messageId, RustBuffer content, int32_t editedAt
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_outbound_file(void * ptr, int64_t messageId, RustBuffer routeKey, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_outbound_message(void * ptr, int64_t messageId, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_enqueue_text(void * ptr, int64_t channelId, int32_t channelType, int64_t fromUid, RustBuffer content
);
void uniffi_privchat_sdk_ffi_fn_method_privchatclient_enter_background(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
void uniffi_privchat_sdk_ffi_fn_method_privchatclient_enter_foreground(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_entity_sync_remote(void * ptr, RustBuffer payload
);
RustBuffer uniffi_privchat_sdk_ffi_fn_method_privchatclient_event_config(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_event_stream_cursor(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
RustBuffer uniffi_privchat_sdk_ffi_fn_method_privchatclient_events_since(void * ptr, int64_t sequenceId, int64_t limit, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_fetch_group_members_remote(void * ptr, int64_t groupId, RustBuffer page, RustBuffer pageSize
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_fetch_presence(void * ptr, RustBuffer userIds
);
RustBuffer uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_api_base_url(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_request_upload_token_remote(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_file_upload_callback_remote(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_all_unread_mention_counts(void * ptr, int64_t userId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_all_user_settings(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_blacklist(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_by_id(void * ptr, int64_t channelId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_extra(void * ptr, int64_t channelId, int32_t channelType
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_list_entries(void * ptr, int64_t page, int64_t pageSize
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_sync_state(void * ptr, int64_t channelId, int32_t channelType
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channel_unread_count(void * ptr, int64_t channelId, int32_t channelType
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_channels(void * ptr, int64_t limit, int64_t offset
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_connection_state(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_connection_summary(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_device_push_status(void * ptr, RustBuffer deviceId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_earliest_id(void * ptr, int64_t channelId, int32_t channelType
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_friend_pending_requests(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_friends(void * ptr, int64_t limit, int64_t offset
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_by_id(void * ptr, int64_t groupId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_info(void * ptr, int64_t groupId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_group_members(void * ptr, int64_t groupId, int64_t limit, int64_t offset
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_groups(void * ptr, int64_t limit, int64_t offset
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_message_by_id(void * ptr, int64_t messageId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_message_extra(void * ptr, int64_t messageId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_messages(void * ptr, int64_t channelId, int32_t channelType, int64_t limit, int64_t offset
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_messages_remote(void * ptr, int64_t channelId, RustBuffer beforeServerMessageId, RustBuffer limit
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_or_create_direct_channel(void * ptr, int64_t peerUserId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_presence(void * ptr, int64_t userId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_presence_stats(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_privacy_settings(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_profile(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_total_unread_count(void * ptr, int8_t excludeMuted
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_typing_stats(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_unread_mention_count(void * ptr, int64_t channelId, int32_t channelType, int64_t userId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_user_by_id(void * ptr, int64_t userId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_get_user_setting(void * ptr, RustBuffer key
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_add_members_remote(void * ptr, int64_t groupId, RustBuffer userIds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_approval_handle_remote(void * ptr, int64_t approvalId, int8_t approved, RustBuffer reason
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_approval_list_remote(void * ptr, int64_t groupId, RustBuffer page, RustBuffer pageSize
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_get_settings_remote(void * ptr, int64_t groupId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_leave_remote(void * ptr, int64_t groupId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_mute_all_remote(void * ptr, int64_t groupId, int8_t enabled
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_mute_member_remote(void * ptr, int64_t groupId, int64_t userId, RustBuffer durationSeconds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_qrcode_generate_remote(void * ptr, int64_t groupId, RustBuffer expireSeconds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_qrcode_join_remote(void * ptr, RustBuffer qrKey, RustBuffer token
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_remove_member_remote(void * ptr, int64_t groupId, int64_t userId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_set_role_remote(void * ptr, int64_t groupId, int64_t userId, RustBuffer role
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_transfer_owner_remote(void * ptr, int64_t groupId, int64_t targetUserId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_unmute_member_remote(void * ptr, int64_t groupId, int64_t userId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_group_update_settings_remote(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_heartbeat_interval(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_hide_channel(void * ptr, int64_t channelId
);
RustBuffer uniffi_privchat_sdk_ffi_fn_method_privchatclient_http_client_config(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int32_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_image_send_max_edge(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_invite_to_group(void * ptr, int64_t groupId, RustBuffer memberIds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_bootstrap_completed(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_connected(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_event_read_by(void * ptr, int64_t serverMessageId, int64_t userId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_initialized(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_shutting_down(void * ptr
);
int8_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_is_supervised_sync_running(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_join_group_by_qrcode(void * ptr, RustBuffer qrKey
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_kv_get(void * ptr, RustBuffer key
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_kv_put(void * ptr, RustBuffer key, RustBuffer value
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_leave_channel(void * ptr, int64_t channelId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_leave_group(void * ptr, int64_t groupId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_blacklist_entries(void * ptr, int64_t limit, int64_t offset
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_channel_members(void * ptr, int64_t channelId, int32_t channelType, int64_t limit, int64_t offset
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_channels(void * ptr, int64_t limit, int64_t offset
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_friends(void * ptr, int64_t limit, int64_t offset
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_group_members(void * ptr, int64_t groupId, int64_t limit, int64_t offset
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_groups(void * ptr, int64_t limit, int64_t offset
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_message_reactions(void * ptr, int64_t messageId, int64_t limit, int64_t offset
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_messages(void * ptr, int64_t channelId, int32_t channelType, int64_t limit, int64_t offset
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_my_devices(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_pending_reminders(void * ptr, int64_t uid, int64_t limit, int64_t offset
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_reactions(void * ptr, int64_t serverMessageId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_unread_mention_message_ids(void * ptr, int64_t channelId, int32_t channelType, int64_t userId, int64_t limit
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_list_users_by_ids(void * ptr, RustBuffer userIds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_log_connection_state(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_login(void * ptr, RustBuffer username, RustBuffer password, RustBuffer deviceId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_logout(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_all_mentions_read(void * ptr, int64_t channelId, int32_t channelType, int64_t userId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_as_read(void * ptr, int64_t channelId, int64_t serverMessageId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_as_read_blocking(void * ptr, int64_t channelId, int64_t serverMessageId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_channel_read(void * ptr, int64_t channelId, int32_t channelType
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_fully_read_at(void * ptr, int64_t channelId, int64_t serverMessageId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_mention_read(void * ptr, int64_t messageId, int64_t userId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_message_sent(void * ptr, int64_t messageId, int64_t serverMessageId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_mark_reminder_done(void * ptr, int64_t reminderId, int8_t done
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_read_list(void * ptr, int64_t serverMessageId, int64_t channelId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_read_stats(void * ptr, int64_t serverMessageId, int64_t channelId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_message_unread_count_remote(void * ptr, int64_t channelId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_mute_channel(void * ptr, int64_t channelId, int8_t muted
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_needs_sync(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_next_event(void * ptr, int64_t timeoutMs
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_next_event_envelope(void * ptr, int64_t timeoutMs
);
void uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_app_background(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
void uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_app_foreground(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
void uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_connection_state_changed(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
void uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_message_received(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
void uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_reaction_changed(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
void uniffi_privchat_sdk_ffi_fn_method_privchatclient_on_typing_indicator(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_own_last_read(void * ptr, int64_t channelId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_paginate_back(void * ptr, int64_t channelId, int32_t channelType, int64_t page, int64_t pageSize
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_paginate_forward(void * ptr, int64_t channelId, int32_t channelType, int64_t page, int64_t pageSize
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_peek_outbound_files(void * ptr, int64_t queueIndex, int64_t limit
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_peek_outbound_messages(void * ptr, int64_t limit
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_pin_channel(void * ptr, int64_t channelId, int8_t pinned
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_ping(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_generate(void * ptr, RustBuffer qrType, RustBuffer payload, RustBuffer expireSeconds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_list(void * ptr, RustBuffer qrType
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_refresh(void * ptr, RustBuffer qrKey, RustBuffer expireSeconds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_resolve(void * ptr, RustBuffer qrKey, RustBuffer token
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_qrcode_revoke(void * ptr, RustBuffer qrKey
);
RustBuffer uniffi_privchat_sdk_ffi_fn_method_privchatclient_queue_config(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_reaction_stats(void * ptr, int64_t serverMessageId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_reactions(void * ptr, int64_t serverMessageId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_reactions_batch(void * ptr, RustBuffer serverMessageIds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_recall_message(void * ptr, int64_t serverMessageId, int64_t channelId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_recall_message_blocking(void * ptr, int64_t serverMessageId, int64_t channelId
);
RustBuffer uniffi_privchat_sdk_ffi_fn_method_privchatclient_recent_events(void * ptr, int64_t limit, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_record_mention(void * ptr, RustBuffer input
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_register(void * ptr, RustBuffer username, RustBuffer password, RustBuffer deviceId
);
void uniffi_privchat_sdk_ffi_fn_method_privchatclient_register_lifecycle_hook(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_reject_friend_request(void * ptr, int64_t fromUserId, RustBuffer message
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_channel_member(void * ptr, int64_t channelId, int32_t channelType, int64_t memberUid
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_from_blacklist(void * ptr, int64_t blockedUserId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_group_member(void * ptr, int64_t groupId, int64_t userId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_remove_reaction(void * ptr, int64_t serverMessageId, RustBuffer emoji
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_channel_id_by_server_message_id(void * ptr, int64_t serverMessageId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_channel_type(void * ptr, int64_t channelId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_resolve_local_message_id_by_server_message_id(void * ptr, int64_t channelId, int32_t channelType, int64_t serverMessageId
);
RustBuffer uniffi_privchat_sdk_ffi_fn_method_privchatclient_retry_config(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_retry_message(void * ptr, int64_t messageId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_rpc_call(void * ptr, RustBuffer route, RustBuffer bodyJson
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_run_bootstrap_sync(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_channel(void * ptr, RustBuffer keyword
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_messages(void * ptr, int64_t channelId, int32_t channelType, RustBuffer keyword
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_user_by_qrcode(void * ptr, RustBuffer qrKey, RustBuffer token
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_search_users(void * ptr, RustBuffer query
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_seen_by_for_event(void * ptr, int64_t serverMessageId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_attachment_bytes(void * ptr, int64_t messageId, RustBuffer routeKey, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_attachment_from_path(void * ptr, int64_t messageId, RustBuffer routeKey, RustBuffer path
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_friend_request(void * ptr, int64_t targetUserId, RustBuffer message, RustBuffer source, RustBuffer sourceId
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message(void * ptr, int64_t channelId, int32_t channelType, int64_t fromUid, RustBuffer content
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_blocking(void * ptr, int64_t channelId, int32_t channelType, int64_t fromUid, RustBuffer content
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_with_input(void * ptr, RustBuffer input
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_message_with_options(void * ptr, RustBuffer input, RustBuffer options
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_queue_set_enabled(void * ptr, int8_t enabled
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_send_typing(void * ptr, int64_t channelId, int32_t channelType, int8_t isTyping, RustBuffer actionType
);
RustBuffer uniffi_privchat_sdk_ffi_fn_method_privchatclient_server_config(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
RustBuffer uniffi_privchat_sdk_ffi_fn_method_privchatclient_servers(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_session_snapshot(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_favourite(void * ptr, int64_t channelId, int32_t channelType, int8_t enabled
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_low_priority(void * ptr, int64_t channelId, int32_t channelType, int8_t enabled
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_channel_notification_mode(void * ptr, int64_t channelId, int32_t channelType, int32_t mode
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_pinned(void * ptr, int64_t messageId, int8_t isPinned
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_read(void * ptr, int64_t messageId, int64_t channelId, int32_t channelType, int8_t isRead
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_message_revoke(void * ptr, int64_t messageId, int8_t revoked, RustBuffer revoker
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_user_setting(void * ptr, RustBuffer key, RustBuffer value
);
void uniffi_privchat_sdk_ffi_fn_method_privchatclient_set_video_process_hook(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_shutdown(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_shutdown_blocking(void * ptr
);
void uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_supervised_sync(void * ptr, int64_t intervalSecs, UniffiRustCallStatus *_Nonnull out_status
);
void uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_transport_disconnect_listener(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_typing(void * ptr, int64_t channelId, int32_t channelType
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_start_typing_blocking(void * ptr, int64_t channelId, int32_t channelType
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_sticker_package_detail_remote(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_sticker_package_list_remote(void * ptr, RustBuffer payload
);
void uniffi_privchat_sdk_ffi_fn_method_privchatclient_stop_supervised_sync(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_stop_typing(void * ptr, int64_t channelId, int32_t channelType
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_storage(void * ptr
);
int8_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_events(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int8_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_network_status(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_subscribe_presence(void * ptr, RustBuffer userIds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_all_channels(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_batch_get_channel_pts_remote(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_channel(void * ptr, int64_t channelId, int32_t channelType
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_entities(void * ptr, RustBuffer entityType, RustBuffer scope
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_entities_in_background(void * ptr, RustBuffer entityType, RustBuffer scope
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_get_channel_pts_remote(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_get_difference_remote(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_messages(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_messages_in_background(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_sync_submit_remote(void * ptr, RustBuffer payload
);
int32_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_hours(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
RustBuffer uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_local(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int32_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_minutes(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int32_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_timezone_seconds(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
RustBuffer uniffi_privchat_sdk_ffi_fn_method_privchatclient_to_client_endpoint(void * ptr, UniffiRustCallStatus *_Nonnull out_status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_unsubscribe_presence(void * ptr, RustBuffer userIds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_device_push_state(void * ptr, RustBuffer deviceId, int8_t apnsArmed, RustBuffer pushToken
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_message_status(void * ptr, int64_t messageId, int32_t status
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_privacy_settings(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_update_profile(void * ptr, RustBuffer payload
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_blacklist_entry(void * ptr, RustBuffer input
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel(void * ptr, RustBuffer input
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel_extra(void * ptr, RustBuffer input
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_channel_member(void * ptr, RustBuffer input
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_friend(void * ptr, RustBuffer input
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_group(void * ptr, RustBuffer input
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_group_member(void * ptr, RustBuffer input
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_message_reaction(void * ptr, RustBuffer input
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_reminder(void * ptr, RustBuffer input
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_upsert_user(void * ptr, RustBuffer input
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_id(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_generate(void * ptr, RustBuffer expireSeconds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_get(void * ptr
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_qrcode_refresh(void * ptr, RustBuffer expireSeconds
);
int64_t uniffi_privchat_sdk_ffi_fn_method_privchatclient_user_storage_paths(void * ptr
);
RustBuffer uniffi_privchat_sdk_ffi_fn_func_sdk_version(UniffiRustCallStatus *_Nonnull out_status
    
);
RustBuffer ffi_privchat_sdk_ffi_rustbuffer_alloc(int64_t size, UniffiRustCallStatus *_Nonnull out_status
);
RustBuffer ffi_privchat_sdk_ffi_rustbuffer_from_bytes(ForeignBytes bytes, UniffiRustCallStatus *_Nonnull out_status
);
void ffi_privchat_sdk_ffi_rustbuffer_free(RustBuffer buf, UniffiRustCallStatus *_Nonnull out_status
);
RustBuffer ffi_privchat_sdk_ffi_rustbuffer_reserve(RustBuffer buf, int64_t additional, UniffiRustCallStatus *_Nonnull out_status
);
void ffi_privchat_sdk_ffi_rust_future_poll_u8(int64_t handle, UniffiRustFutureContinuationCallback callback, int64_t callbackData
);
void ffi_privchat_sdk_ffi_rust_future_cancel_u8(int64_t handle
);
void ffi_privchat_sdk_ffi_rust_future_free_u8(int64_t handle
);
int8_t ffi_privchat_sdk_ffi_rust_future_complete_u8(int64_t handle, UniffiRustCallStatus *_Nonnull out_status
);
void ffi_privchat_sdk_ffi_rust_future_poll_i8(int64_t handle, UniffiRustFutureContinuationCallback callback, int64_t callbackData
);
void ffi_privchat_sdk_ffi_rust_future_cancel_i8(int64_t handle
);
void ffi_privchat_sdk_ffi_rust_future_free_i8(int64_t handle
);
int8_t ffi_privchat_sdk_ffi_rust_future_complete_i8(int64_t handle, UniffiRustCallStatus *_Nonnull out_status
);
void ffi_privchat_sdk_ffi_rust_future_poll_u16(int64_t handle, UniffiRustFutureContinuationCallback callback, int64_t callbackData
);
void ffi_privchat_sdk_ffi_rust_future_cancel_u16(int64_t handle
);
void ffi_privchat_sdk_ffi_rust_future_free_u16(int64_t handle
);
int16_t ffi_privchat_sdk_ffi_rust_future_complete_u16(int64_t handle, UniffiRustCallStatus *_Nonnull out_status
);
void ffi_privchat_sdk_ffi_rust_future_poll_i16(int64_t handle, UniffiRustFutureContinuationCallback callback, int64_t callbackData
);
void ffi_privchat_sdk_ffi_rust_future_cancel_i16(int64_t handle
);
void ffi_privchat_sdk_ffi_rust_future_free_i16(int64_t handle
);
int16_t ffi_privchat_sdk_ffi_rust_future_complete_i16(int64_t handle, UniffiRustCallStatus *_Nonnull out_status
);
void ffi_privchat_sdk_ffi_rust_future_poll_u32(int64_t handle, UniffiRustFutureContinuationCallback callback, int64_t callbackData
);
void ffi_privchat_sdk_ffi_rust_future_cancel_u32(int64_t handle
);
void ffi_privchat_sdk_ffi_rust_future_free_u32(int64_t handle
);
int32_t ffi_privchat_sdk_ffi_rust_future_complete_u32(int64_t handle, UniffiRustCallStatus *_Nonnull out_status
);
void ffi_privchat_sdk_ffi_rust_future_poll_i32(int64_t handle, UniffiRustFutureContinuationCallback callback, int64_t callbackData
);
void ffi_privchat_sdk_ffi_rust_future_cancel_i32(int64_t handle
);
void ffi_privchat_sdk_ffi_rust_future_free_i32(int64_t handle
);
int32_t ffi_privchat_sdk_ffi_rust_future_complete_i32(int64_t handle, UniffiRustCallStatus *_Nonnull out_status
);
void ffi_privchat_sdk_ffi_rust_future_poll_u64(int64_t handle, UniffiRustFutureContinuationCallback callback, int64_t callbackData
);
void ffi_privchat_sdk_ffi_rust_future_cancel_u64(int64_t handle
);
void ffi_privchat_sdk_ffi_rust_future_free_u64(int64_t handle
);
int64_t ffi_privchat_sdk_ffi_rust_future_complete_u64(int64_t handle, UniffiRustCallStatus *_Nonnull out_status
);
void ffi_privchat_sdk_ffi_rust_future_poll_i64(int64_t handle, UniffiRustFutureContinuationCallback callback, int64_t callbackData
);
void ffi_privchat_sdk_ffi_rust_future_cancel_i64(int64_t handle
);
void ffi_privchat_sdk_ffi_rust_future_free_i64(int64_t handle
);
int64_t ffi_privchat_sdk_ffi_rust_future_complete_i64(int64_t handle, UniffiRustCallStatus *_Nonnull out_status
);
void ffi_privchat_sdk_ffi_rust_future_poll_f32(int64_t handle, UniffiRustFutureContinuationCallback callback, int64_t callbackData
);
void ffi_privchat_sdk_ffi_rust_future_cancel_f32(int64_t handle
);
void ffi_privchat_sdk_ffi_rust_future_free_f32(int64_t handle
);
float ffi_privchat_sdk_ffi_rust_future_complete_f32(int64_t handle, UniffiRustCallStatus *_Nonnull out_status
);
void ffi_privchat_sdk_ffi_rust_future_poll_f64(int64_t handle, UniffiRustFutureContinuationCallback callback, int64_t callbackData
);
void ffi_privchat_sdk_ffi_rust_future_cancel_f64(int64_t handle
);
void ffi_privchat_sdk_ffi_rust_future_free_f64(int64_t handle
);
double ffi_privchat_sdk_ffi_rust_future_complete_f64(int64_t handle, UniffiRustCallStatus *_Nonnull out_status
);
void ffi_privchat_sdk_ffi_rust_future_poll_pointer(int64_t handle, UniffiRustFutureContinuationCallback callback, int64_t callbackData
);
void ffi_privchat_sdk_ffi_rust_future_cancel_pointer(int64_t handle
);
void ffi_privchat_sdk_ffi_rust_future_free_pointer(int64_t handle
);
void * ffi_privchat_sdk_ffi_rust_future_complete_pointer(int64_t handle, UniffiRustCallStatus *_Nonnull out_status
);
void ffi_privchat_sdk_ffi_rust_future_poll_rust_buffer(int64_t handle, UniffiRustFutureContinuationCallback callback, int64_t callbackData
);
void ffi_privchat_sdk_ffi_rust_future_cancel_rust_buffer(int64_t handle
);
void ffi_privchat_sdk_ffi_rust_future_free_rust_buffer(int64_t handle
);
RustBuffer ffi_privchat_sdk_ffi_rust_future_complete_rust_buffer(int64_t handle, UniffiRustCallStatus *_Nonnull out_status
);
void ffi_privchat_sdk_ffi_rust_future_poll_void(int64_t handle, UniffiRustFutureContinuationCallback callback, int64_t callbackData
);
void ffi_privchat_sdk_ffi_rust_future_cancel_void(int64_t handle
);
void ffi_privchat_sdk_ffi_rust_future_free_void(int64_t handle
);
void ffi_privchat_sdk_ffi_rust_future_complete_void(int64_t handle, UniffiRustCallStatus *_Nonnull out_status
);
int16_t uniffi_privchat_sdk_ffi_checksum_func_sdk_version(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_accept_friend_request(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_account_user_detail_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_account_user_share_card_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_account_user_update_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_ack_outbound_files(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_ack_outbound_messages(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_channel_members(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_reaction(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_reaction_blocking(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_server(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_add_to_blacklist(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_assets_dir(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_auth_logout_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_auth_refresh_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_authenticate(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_batch_get_presence(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_build(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_builder(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_broadcast_create_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_broadcast_list_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_broadcast_subscribe_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_content_list_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_content_publish_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_notification_mode(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_send_queue_set_enabled(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_tags(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_channel_unread_stats(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_check_blacklist(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_check_friend(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_clear_local_state(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_clear_presence_cache(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_config(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connect(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connect_blocking(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connection_state(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_connection_timeout(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_create_group(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_create_local_message(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_current_user_id(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_data_dir(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_debug_mode(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_delete_blacklist_entry(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_delete_channel_member(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_delete_friend(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_disconnect(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_dm_peer_user_id(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_download_attachment_to_cache(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_download_attachment_to_message_dir(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_download_attachment_to_path(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_edit_message(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_edit_message_blocking(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enqueue_outbound_file(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enqueue_outbound_message(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enqueue_text(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enter_background(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_enter_foreground(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_entity_sync_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_event_config(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_event_stream_cursor(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_events_since(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_fetch_group_members_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_fetch_presence(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_file_api_base_url(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_file_request_upload_token_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_file_upload_callback_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_all_unread_mention_counts(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_all_user_settings(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_blacklist(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_by_id(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_extra(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_list_entries(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_sync_state(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channel_unread_count(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_channels(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_connection_state(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_connection_summary(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_device_push_status(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_earliest_id(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_friend_pending_requests(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_friends(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_group_by_id(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_group_info(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_group_members(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_groups(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_message_by_id(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_message_extra(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_messages(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_messages_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_or_create_direct_channel(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_presence(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_presence_stats(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_privacy_settings(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_profile(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_total_unread_count(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_typing_stats(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_unread_mention_count(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_user_by_id(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_get_user_setting(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_add_members_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_approval_handle_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_approval_list_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_get_settings_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_leave_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_mute_all_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_mute_member_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_qrcode_generate_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_qrcode_join_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_remove_member_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_set_role_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_transfer_owner_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_unmute_member_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_group_update_settings_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_heartbeat_interval(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_hide_channel(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_http_client_config(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_image_send_max_edge(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_invite_to_group(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_bootstrap_completed(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_connected(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_event_read_by(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_initialized(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_shutting_down(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_is_supervised_sync_running(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_join_group_by_qrcode(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_kv_get(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_kv_put(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_leave_channel(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_leave_group(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_blacklist_entries(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_channel_members(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_channels(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_friends(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_group_members(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_groups(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_message_reactions(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_messages(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_my_devices(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_pending_reminders(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_reactions(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_unread_mention_message_ids(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_list_users_by_ids(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_log_connection_state(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_login(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_logout(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_all_mentions_read(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_as_read(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_as_read_blocking(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_channel_read(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_fully_read_at(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_mention_read(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_message_sent(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mark_reminder_done(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_message_read_list(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_message_read_stats(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_message_unread_count_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_mute_channel(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_needs_sync(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_next_event(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_next_event_envelope(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_app_background(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_app_foreground(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_connection_state_changed(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_message_received(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_reaction_changed(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_on_typing_indicator(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_own_last_read(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_paginate_back(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_paginate_forward(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_peek_outbound_files(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_peek_outbound_messages(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_pin_channel(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_ping(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_generate(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_list(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_refresh(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_resolve(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_qrcode_revoke(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_queue_config(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reaction_stats(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reactions(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reactions_batch(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_recall_message(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_recall_message_blocking(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_recent_events(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_record_mention(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_register(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_register_lifecycle_hook(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_reject_friend_request(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_channel_member(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_from_blacklist(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_group_member(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_remove_reaction(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_resolve_channel_id_by_server_message_id(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_resolve_channel_type(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_resolve_local_message_id_by_server_message_id(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_retry_config(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_retry_message(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_rpc_call(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_run_bootstrap_sync(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_channel(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_messages(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_user_by_qrcode(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_search_users(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_seen_by_for_event(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_attachment_bytes(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_attachment_from_path(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_friend_request(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message_blocking(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message_with_input(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_message_with_options(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_queue_set_enabled(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_send_typing(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_server_config(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_servers(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_session_snapshot(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_channel_favourite(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_channel_low_priority(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_channel_notification_mode(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_message_pinned(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_message_read(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_message_revoke(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_user_setting(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_set_video_process_hook(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_shutdown(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_shutdown_blocking(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_supervised_sync(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_transport_disconnect_listener(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_typing(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_start_typing_blocking(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sticker_package_detail_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sticker_package_list_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_stop_supervised_sync(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_stop_typing(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_storage(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_subscribe_events(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_subscribe_network_status(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_subscribe_presence(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_all_channels(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_batch_get_channel_pts_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_channel(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_entities(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_entities_in_background(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_get_channel_pts_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_get_difference_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_messages(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_messages_in_background(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_sync_submit_remote(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_hours(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_local(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_minutes(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_timezone_seconds(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_to_client_endpoint(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_unsubscribe_presence(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_device_push_state(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_message_status(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_privacy_settings(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_update_profile(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_blacklist_entry(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_channel(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_channel_extra(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_channel_member(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_friend(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_group(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_group_member(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_message_reaction(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_reminder(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_upsert_user(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_id(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_qrcode_generate(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_qrcode_get(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_qrcode_refresh(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_method_privchatclient_user_storage_paths(void
    
);
int16_t uniffi_privchat_sdk_ffi_checksum_constructor_privchatclient_new(void
    
);
int32_t ffi_privchat_sdk_ffi_uniffi_contract_version(void
    
);
