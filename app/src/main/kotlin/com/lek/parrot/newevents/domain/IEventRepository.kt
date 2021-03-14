package com.lek.parrot.newevents.domain

import com.lek.parrot.shared.Event
import kotlinx.coroutines.flow.Flow

interface IEventRepository {
    suspend fun createEvent(event: Event)
    suspend fun updateEvent(event: Event)
    fun getEvents(): Flow<List<Event>>
    fun getEvent(eventId: String): Flow<Event>
    fun deleteEvent(event: Event)
    fun deleteAllEvents()
}