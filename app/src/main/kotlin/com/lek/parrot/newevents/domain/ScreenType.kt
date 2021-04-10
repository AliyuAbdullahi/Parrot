package com.lek.parrot.newevents.domain

enum class ScreenType {
    MESSAGE, CALL, REMINDER
}

fun screenTypeFromOrdinal(ordinal: Int) = ScreenType.values()[ordinal]