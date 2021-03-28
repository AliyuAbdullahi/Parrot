package com.lek.parrot.newevents.ui

enum class CreateEventType {
    MESSAGE, CALL
}

fun createEventTypeFromOrdinal(ordinal: Int) = CreateEventType.values()[ordinal]