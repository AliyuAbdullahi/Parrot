package com.lek.parrot.data

import com.lek.parrot.mockDataEvent
import com.lek.parrot.mockEvent
import com.lek.parrot.shared.Event
import org.junit.jupiter.api.Test

internal class DataEventToDomainEventMapperTest {

    @Test
    fun `when mapper mapDataEventToEvent is invoked - event is returned`() {
        val dataEvent = mockDataEvent()
        val result = DataEventToDomainEventMapper.mapDataEventToEvent(dataEvent)

        assert(result is Event.CallEvent)
        assert((result as Event.CallEvent).fireAt == dataEvent.fireAt)
        assert(result.eventId == dataEvent.eventId)
    }

    @Test
    fun `when mapper mapEventToDataEvent is invoked - data event is returned`() {
        val event = mockEvent()
        val result = DataEventToDomainEventMapper.mapEventToDataEvent(event)

        assert(result.eventType == EventType.CALL)
        assert(result.eventId == event.eventId)
    }

    @Test
    fun `when mapper mapDataEventsToEvents is invoked - mapped result is returned`() {
        val dataEvents = listOf(mockDataEvent())
        val result = DataEventToDomainEventMapper.mapDataEventsToEvents(dataEvents)
        assert(result.size == 1)
        assert(result[0] is Event.CallEvent)
        assert((result[0] as Event.CallEvent).eventId == dataEvents[0].eventId)
    }
}