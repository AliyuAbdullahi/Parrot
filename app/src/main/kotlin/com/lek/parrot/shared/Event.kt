package com.lek.parrot.shared

import java.util.UUID

sealed class Event {
    abstract val eventId: String
    abstract val fireAt: Long
    abstract val isCancelled: Boolean

    data class CallEvent(
        val receiverName: String,
        val receiverNumber: String,
        val createdAt: Long,
        override val fireAt: Long,
        override val isCancelled: Boolean,
        override val eventId: String = UUID.randomUUID().toString()
    ) : Event()

    data class MessageEvent(
        val receiverName: String,
        val receiverNumber: String,
        val message: String,
        val createdAt: Long,
        override val fireAt: Long,
        override val isCancelled: Boolean,
        override val eventId: String = UUID.randomUUID().toString()
    ) : Event()

    class ReminderEvent(
        val message: String,
        val createdAt: Long,
        override val fireAt: Long,
        override val isCancelled: Boolean,
        override val eventId: String = UUID.randomUUID().toString()
    ) : Event()
}