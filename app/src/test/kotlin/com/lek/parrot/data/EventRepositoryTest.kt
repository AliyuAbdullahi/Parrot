package com.lek.parrot.data

import com.lek.parrot.mockDataEvent
import com.lek.parrot.mockEvent
import com.lek.parrot.newevents.data.EventRepository
import com.lek.parrot.shared.Event
import com.lek.parrot.newevents.domain.IEventRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class EventRepositoryTest {
    private val dao: EventDao = mockk {
        coEvery { createEvent(any()) } just runs
        coEvery { updateEvent(any()) } just runs
        every { getAllEvents() } returns flowOf(listOf(dataEvent))
        every { deleteEvent(any()) } just runs
    }
    private val event: Event = mockEvent()
    private val dataEvent: DataEvent = mockDataEvent()
    private val mapper: IDataEventToDomainEventMapper = mockk {
        every { mapDataEventToEvent(any()) } returns event
        every { mapDataEventsToEvents(any()) } returns listOf(event)
        every { mapEventToDataEvent(any()) } returns dataEvent
    }

    private val repository: IEventRepository = EventRepository(dao, mapper)

    @Test
    fun `when repository is invoked to create event - event is saved to db`() = runBlocking {
        repository.createEvent(event)
        coVerify { dao.createEvent(dataEvent) }
    }

    @Test
    fun `when repository is invoked to update event - event is updated on db`() = runBlocking {
        repository.updateEvent(event)
        coVerify { dao.updateEvent(dataEvent) }
    }

    @Test
    fun `when repository is invoked to get events - available events are returned`() = runBlocking {
        repository.getEvents()
        verify { dao.getAllEvents() }
    }

    @Test
    fun `when repository is invoked to delete event - event dao invokes delete event`() =
        runBlocking {
            repository.deleteEvent(event)
            verify { dao.deleteEvent(any()) }
        }

    @Test
    fun `when repository is invoked to delete all events - all events are deleted`() = runBlocking {
        every { dao.deleteAll() } just runs
        repository.deleteAllEvents()
        coVerify { dao.deleteAll() }
    }
}