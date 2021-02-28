package com.lek.parrot.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class DataEvent(
    @PrimaryKey
    val eventId: String,
    val receiverName: String = "",
    val receiverNumber: String = "",
    val message: String,
    val createdAt: Long,
    val fireAt: Long,
    val isCancelled: Boolean = false,
    val eventType: EventType
)