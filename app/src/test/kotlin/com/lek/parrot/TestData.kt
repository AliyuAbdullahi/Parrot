package com.lek.parrot

import com.lek.parrot.data.DataEvent
import com.lek.parrot.data.EventType
import com.lek.parrot.shared.Event

fun mockDataEvent() = DataEvent(
    "testId",
    "testName",
    "testNumber",
    "testMessage",
    0L,
    0L,
    false,
    EventType.CALL
)

fun mockEvent() = Event.CallEvent(
    "testName",
    "testNumber",
    0L,
    0L,
    false,
    "testId"
)