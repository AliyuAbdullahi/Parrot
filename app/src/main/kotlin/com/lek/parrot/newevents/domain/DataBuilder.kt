package com.lek.parrot.newevents.domain

import androidx.work.Data
import com.lek.parrot.notification.NOTIFICATION_ID
import com.lek.parrot.notification.NOTIFICATION_MESSAGE
import com.lek.parrot.notification.NOTIFICATION_TARGET
import com.lek.parrot.notification.NOTIFICATION_TITLE
import com.lek.parrot.shared.Event

object DataBuilder {
    fun buildFromMessage(messageEvent: Event.MessageEvent): Data =
        Data.Builder().putInt(NOTIFICATION_ID, 0)
            .putString(NOTIFICATION_TITLE, getName(messageEvent))
            .putString(NOTIFICATION_MESSAGE, messageEvent.message)
            .putString(NOTIFICATION_TARGET, "Target")
            .build()

    fun buildFromCallEvent(callEvent: Event.CallEvent): Data =
        Data.Builder().putInt(NOTIFICATION_ID, 0)
            .putString(NOTIFICATION_TITLE, getName(callEvent))
            .putString(NOTIFICATION_TARGET, "Target")
            .build()

    private fun getName(messageEvent: Event.MessageEvent): String {
        if (messageEvent.receiverName.isBlank()) {
            return if (messageEvent.receiverNumber.isBlank()) "-" else messageEvent.receiverNumber
        }
        return if (messageEvent.receiverName.isBlank()) "-" else messageEvent.receiverName
    }

    private fun getName(messageEvent: Event.CallEvent): String {
        if (messageEvent.receiverName.isBlank()) {
            return if (messageEvent.receiverNumber.isBlank()) "-" else messageEvent.receiverNumber
        }
        return if (messageEvent.receiverName.isBlank()) "-" else messageEvent.receiverName
    }
}
