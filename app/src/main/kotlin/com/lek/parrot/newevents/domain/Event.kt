package com.lek.parrot.newevents.domain

import java.util.UUID

sealed class Event(eventId: String, fireAt: Long, isCancelled: Boolean) {
    data class CallEvent(
        val receiverName: String,
        val receiverNumber: String,
        val createdAt: Long,
        val fireAt: Long,
        val isCancelled: Boolean,
        val eventId: String = UUID.randomUUID().toString()
    ) : Event(eventId, fireAt, isCancelled)

    data class MessageEvent(
        val receiverName: String,
        val receiverNumber: String,
        val message: String,
        val createdAt: Long,
        val fireAt: Long,
        val isCancelled: Boolean,
        val eventId: String = UUID.randomUUID().toString()
    ) :
        Event(eventId, fireAt, isCancelled)

    class ReminderEvent(
        val message: String,
        val createdAt: Long,
        val fireAt: Long,
        val isCancelled: Boolean,
        val eventId: String = UUID.randomUUID().toString()
    ) :
        Event(eventId, fireAt, isCancelled)
}