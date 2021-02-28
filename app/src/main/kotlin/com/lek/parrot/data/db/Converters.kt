package com.lek.parrot.data.db

import androidx.room.TypeConverter
import com.lek.parrot.data.EventType

class Converters {

    @TypeConverter
    fun toDataEvent(value: Int) = enumValues<EventType>()[value]

    @TypeConverter
    fun fromDataEvent(eventType: EventType) = eventType.ordinal
}