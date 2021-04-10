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
        val year: Year = DateUtil.currentYear(),
        val month: Month = DateUtil.currentMonth(),
        val dayOfMonth: DayOfMonth = DateUtil.currentDay(),
        val name: String = "",
        val phoneNumber: String = "",
        val message: String = ""
    ) : CreateEventState()

    data class CallEvent(
        val hour: Hour = ABSENT_VALUE,
        val minute: Minute = ABSENT_VALUE,
        val year: Year = DateUtil.currentYear(),
        val month: Month = DateUtil.currentMonth(),
        val dayOfMonth: DayOfMonth = DateUtil.currentDay(),
        val name: String = "",
        val phoneNumber: String = ""
    ) : CreateEventState()

    data class ReminderEvent(
        val hour: Hour = ABSENT_VALUE,
        val minute: Minute = ABSENT_VALUE,
        val year: Year = DateUtil.currentYear(),
        val month: Month = DateUtil.currentMonth(),
        val dayOfMonth: DayOfMonth = DateUtil.currentDay(),
        val message: String = ""
    ) : CreateEventState()
}

fun CreateEventState.validateReminderEvent(): CreateEventState {
    if (this !is CreateEventState.ReminderEvent) {
        return CreateEventState.InvalidEventState
    }

    if (year > DateUtil.currentYear()) {
        return CreateEventState.ReminderEvent(
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

    return CreateEventState.ReminderEvent(
        hour,
        minute,
        year,
        month,
        dayOfMonth,
        message
    )
}

fun CreateEventState.validateCallEvent(): CreateEventState {
    if (this !is CreateEventState.CallEvent) {
        return CreateEventState.InvalidEventState
    }

    if (year > DateUtil.currentYear()) {
        return CreateEventState.CallEvent(
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

    if (name.isEmpty() || phoneNumber.isEmpty()) {
        return CreateEventState.EmptyReceiver
    }

    return CreateEventState.CallEvent(
        hour,
        minute,
        year,
        month,
        dayOfMonth,
        name,
        phoneNumber
    )
}

fun CreateEventState.validateMessageEvent(): CreateEventState {

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
        dayOfMonth,
        name,
        phoneNumber,
        message
    )
}

private fun Int.isAbsent() = this == ABSENT_VALUE