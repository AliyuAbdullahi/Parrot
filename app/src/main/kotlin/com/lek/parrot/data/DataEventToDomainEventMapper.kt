package com.lek.parrot.data

import com.lek.parrot.newevents.domain.Event

object DataEventToDomainEventMapper : IDataEventToDomainEventMapper {
    override fun mapDataEventToEvent(dataEvent: DataEvent): Event = when (dataEvent.eventType) {
        EventType.CALL -> Event.CallEvent(
            dataEvent.receiverName,
            dataEvent.receiverNumber,
            dataEvent.createdAt,
            dataEvent.fireAt,
            dataEvent.isCancelled,
            dataEvent.eventId
        )

        EventType.MESSAGE -> Event.MessageEvent(
            dataEvent.receiverName,
            dataEvent.receiverNumber,
            dataEvent.message,
            dataEvent.createdAt,
            dataEvent.fireAt,
            dataEvent.isCancelled,
            dataEvent.eventId
        )

        EventType.REMINDER -> Event.ReminderEvent(
            dataEvent.message,
            dataEvent.createdAt,
            dataEvent.fireAt,
            dataEvent.isCancelled,
            dataEvent.eventId
        )
    }

    override fun mapEventToDataEvent(event: Event): DataEvent = when (event) {
        is Event.CallEvent -> DataEvent(
            event.eventId,
            event.receiverName,
            event.receiverNumber,
            "",
            event.createdAt,
            event.fireAt,
            event.isCancelled,
            EventType.CALL
        )

        is Event.MessageEvent -> DataEvent(
            event.eventId,
            event.receiverName,
            event.receiverNumber,
            event.message,
            event.createdAt,
            event.fireAt,
            event.isCancelled,
            EventType.MESSAGE
        )

        is Event.ReminderEvent -> DataEvent(
            event.eventId,
            "",
            "",
            event.message,
            event.createdAt,
            event.fireAt,
            event.isCancelled,
            EventType.REMINDER
        )
    }

    override fun mapDataEventsToEvents(dataEvents: List<DataEvent>): List<Event> =
        dataEvents.map { mapDataEventToEvent(it) }
}
