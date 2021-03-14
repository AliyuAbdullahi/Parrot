package com.lek.parrot.newevents.domain

private const val ABSENT_VALUE = -1
typealias Year = Int
typealias Month = Int
typealias DayOfMonth = Int
typealias Minute = Int
typealias Hour = Int

sealed class CreateEventState {

    object InvalidTime : CreateEventState()
    object InvalidMonth : CreateEventState()
    object EmptyTime : CreateEventState()
    object InvalidYear : CreateEventState()
    object InvalidDay : CreateEventState()
    object EmptyMessage : CreateEventState()
    object EmptyReceiver : CreateEventState()
    object InvalidEventState : CreateEventState()

    data class MessageEvent(
        val hour: Hour = ABSENT_VALUE,
        val minute: Minute = ABSENT_VALUE,
        val year: Year = ABSENT_VALUE,
        val month: Month = ABSENT_VALUE,
        val dayOfMonth: DayOfMonth = ABSENT_VALUE,
        val name: String = "",
        val phoneNumber: String = "",
        val message: String = ""
    ) : CreateEventState() {
        fun updateHour(hour: Int) = copy(hour = hour)
        fun updateMinute(minute: Int) = copy(minute = minute)
        fun updateYear(year: Int) = copy(year = year)
        fun updateDayOfMonth(dayOfMonth: Int) = copy(dayOfMonth = dayOfMonth)
        fun updateMonth(month: Int) = copy(month = month)
    }
}

fun CreateEventState.validate(): CreateEventState {

    if (this !is CreateEventState.MessageEvent) {
        return CreateEventState.InvalidEventState
    }

    if (year > DateUtil.currentYear()) {
        return CreateEventState.MessageEvent(
            hour,
            minute,
            year,
            month,
            dayOfMonth
        )
    }
    if (hour.isAbsent() || minute.isAbsent()) {
        return CreateEventState.EmptyTime
    }

    if (year.isAbsent() && !month.isAbsent() && DateUtil.currentMonth() > month) {
        return CreateEventState.InvalidMonth
    }

    if (!year.isAbsent() && month.isAbsent()) {
        return CreateEventState.InvalidMonth
    }

    if (!year.isAbsent() && year < DateUtil.currentYear()) {
        return CreateEventState.InvalidYear
    }

    if (year.isAbsent() && month == DateUtil.currentMonth() && dayOfMonth < DateUtil.currentDay()) {
        return CreateEventState.InvalidDay
    }

    if (year == DateUtil.currentYear() &&
        month == DateUtil.currentMonth() &&
        dayOfMonth == DateUtil.currentDay() &&
        hour < DateUtil.currentHour()
    ) {
        return CreateEventState.InvalidTime
    }

    if (year == DateUtil.currentYear() &&
        month == DateUtil.currentMonth() &&
        dayOfMonth == DateUtil.currentDay() &&
        hour == DateUtil.currentHour() &&
        minute < DateUtil.currentMinute()
    ) {
        return CreateEventState.InvalidTime
    }

    if (message.isBlank()) {
        return CreateEventState.EmptyMessage
    }

    if (name.isEmpty() || phoneNumber.isEmpty()) {
        return CreateEventState.EmptyReceiver
    }

    return CreateEventState.MessageEvent(
        hour,
        minute,
        year,
        month,
        dayOfMonth
    )
}

private fun Int.isAbsent() = this == ABSENT_VALUE