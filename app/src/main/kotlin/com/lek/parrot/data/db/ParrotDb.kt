package com.lek.parrot.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lek.parrot.data.db.DatabaseVersions.VERSION_1
import com.lek.parrot.data.DataEvent
import com.lek.parrot.data.EventDao

const val DB_NAME = "parrot_db"

@Database(entities = [DataEvent::class], version = VERSION_1)
@TypeConverters(Converters::class)
abstract class ParrotDb : RoomDatabase() {
    abstract fun eventDao(): EventDao
}