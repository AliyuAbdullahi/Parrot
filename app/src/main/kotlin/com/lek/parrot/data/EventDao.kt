package com.lek.parrot.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createEvent(event: DataEvent)

    @Query("SELECT * from event WHERE eventId=:id")
    fun getEvent(id: String): Flow<DataEvent>

    @Delete
    fun deleteEvent(event: DataEvent)

    @Update
    suspend fun updateEvent(event: DataEvent)

    @Query("SELECT * from event ORDER BY fireAt ASC")
    fun getAllEvents(): Flow<List<DataEvent>>

    @Query("DELETE FROM event")
    fun deleteAll()
}