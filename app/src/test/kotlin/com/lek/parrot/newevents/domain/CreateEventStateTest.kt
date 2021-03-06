package com.lek.parrot.newevents.domain

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

internal class CreateEventStateTest {

    private var event = CreateEventState.MessageEvent()
    private val testMessage = "Love for all"
    private val testName = "Love"

    @Test
    fun `on empty - return Empty Time`() {
        val result = event.validateMessageEvent()
        assert(result is CreateEventState.EmptyTime)
    }

    @AfterEach
    fun clearEvent() {
        event = CreateEventState.MessageEvent()
    }

    @Test
    fun `on wrong year - return Invalid Year`() {
        event = event.copy(month = 6, hour = 4, minute = 12, year = 2020)
        val result = event.validateMessageEvent()
        assert(result is CreateEventState.InvalidYear)
    }

    @Test
    fun `if event is at current time and set hour is less than current hour - return Invalid Time`() {
        event = event.copy(
            hour = DateUtil.currentHour() - 1,
            minute = 12,
            month = DateUtil.currentMonth(),
            dayOfMonth = DateUtil.currentDay(),
            year = DateUtil.currentYear()
        )
        val result = event.validateMessageEvent()
        assert(result is CreateEventState.InvalidTime)
    }

    @Test
    fun `if event is at current day and hour and set minute is less than current minute - return Invalid Time`() {
        event = event.copy(
            hour = DateUtil.currentHour(),
            minute = DateUtil.currentMinute() - 5,
            month = DateUtil.currentMonth(),
            dayOfMonth = DateUtil.currentDay(),
            year = DateUtil.currentYear()
        )
        val result = event.validateMessageEvent()
        assert(result is CreateEventState.InvalidTime)
    }

    @Test
    fun `if there is no message - return Empty Message`() {
        event = event.copy(
            hour = DateUtil.currentHour(),
            minute = DateUtil.currentMinute(),
            month = DateUtil.currentMonth(),
            dayOfMonth = DateUtil.currentDay(),
            year = DateUtil.currentYear()
        )
        val result = event.validateMessageEvent()
        assert(result is CreateEventState.EmptyMessage)
    }

    @Test
    fun `if there is no target - return Empty Receiver`() {
        event = event.copy(
            hour = DateUtil.currentHour(),
            minute = DateUtil.currentMinute(),
            month = DateUtil.currentMonth(),
            dayOfMonth = DateUtil.currentDay(),
            year = DateUtil.currentYear(),
            message = "Love for all"
        )
        val result = event.validateMessageEvent()
        assert(result is CreateEventState.EmptyReceiver)
    }

    @Test
    fun `if all inputs are provided - return MessageEvent`() {
        event = event.copy(
            hour = DateUtil.currentHour(),
            minute = DateUtil.currentMinute(),
            month = DateUtil.currentMonth(),
            dayOfMonth = DateUtil.currentDay(),
            year = DateUtil.currentYear(),
            message = testMessage,
            name = testName
        )
        val result = event.validateMessageEvent()
        assert(result is CreateEventState.EmptyReceiver)
    }
}