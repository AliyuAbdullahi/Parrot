package com.lek.parrot.data

import com.lek.parrot.newevents.domain.Event

interface IDataEventToDomainEventMapper {
    fun mapDataEventToEvent(dataEvent: DataEvent): Event
    fun mapEventToDataEvent(event: Event): DataEvent
    fun mapDataEventsToEvents(dataEvents: List<DataEvent>): List<Event>
}