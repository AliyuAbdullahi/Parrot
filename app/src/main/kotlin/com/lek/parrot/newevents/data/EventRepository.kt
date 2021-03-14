package com.lek.parrot.newevents.data

import com.lek.parrot.data.EventDao
import com.lek.parrot.data.IDataEventToDomainEventMapper
import com.lek.parrot.shared.Event
import com.lek.parrot.newevents.domain.IEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepository(
    private val dao: EventDao,
    private val mapper: IDataEventToDomainEventMapper
) : IEventRepository {

    override suspend fun createEvent(event: Event) = dao.createEvent(mapper.mapEventToDataEvent(event))

    override suspend fun updateEvent(event: Event) = dao.updateEvent(mapper.mapEventToDataEvent(event))

    override fun getEvents(): Flow<List<Event>> = dao.getAllEvents().map { mapper.mapDataEventsToEvents(it) }

    override fun getEvent(eventId: String): Flow<Event> = dao.getEvent(eventId).map { mapper.mapDataEventToEvent(it) }

    override fun deleteEvent(event: Event) = dao.deleteEvent(mapper.mapEventToDataEvent(event))

    override fun deleteAllEvents() = dao.deleteAll()
}