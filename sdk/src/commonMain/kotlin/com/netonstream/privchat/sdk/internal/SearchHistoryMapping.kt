package com.netonstream.privchat.sdk.internal

import com.netonstream.privchat.sdk.dto.AroundContextEntry
import com.netonstream.privchat.sdk.dto.MessagesAroundPage
import com.netonstream.privchat.sdk.dto.SearchHistoryHit
import com.netonstream.privchat.sdk.dto.SearchHistoryPage
import uniffi.privchat_sdk_ffi.MessageHistoryItemView
import uniffi.privchat_sdk_ffi.MessagesAroundView
import uniffi.privchat_sdk_ffi.SearchHistoryView

/** FFI view → 公开 dto（android/native 双 actual 共用，防两端映射漂移） */
internal fun SearchHistoryView.toPage(): SearchHistoryPage = SearchHistoryPage(
    hits = hits.map { h ->
        SearchHistoryHit(
            channelId = h.channelId,
            messageId = h.messageId,
            senderUserId = h.senderUserId,
            createdAt = h.createdAt,
            messageType = h.messageType,
            snippet = h.snippet,
            highlightRanges = h.highlightRanges.map { it.start.toInt() to it.end.toInt() },
        )
    },
    nextCursor = nextCursor,
)

internal fun MessageHistoryItemView.toAroundEntry(): AroundContextEntry = AroundContextEntry(
    messageId = messageId,
    channelId = channelId,
    senderId = senderId,
    content = content,
    messageType = messageType,
    timestamp = timestamp,
    messageSeq = messageSeq,
    revoked = revoked,
)

internal fun MessagesAroundView.toPage(): MessagesAroundPage = MessagesAroundPage(
    beforeMessages = beforeMessages.map { it.toAroundEntry() },
    anchor = anchorMessage.toAroundEntry(),
    afterMessages = afterMessages.map { it.toAroundEntry() },
    hasMoreBefore = hasMoreBefore,
    hasMoreAfter = hasMoreAfter,
)
