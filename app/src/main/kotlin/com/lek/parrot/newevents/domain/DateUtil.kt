package com.lek.parrot.newevents.domain

import java.util.Calendar

object DateUtil {
    private val calendar = Calendar.getInstance()
    fun currentMonth() = calendar.get(Calendar.MONTH)
    fun currentYear() = calendar.get(Calendar.YEAR)
    fun currentDay() = calendar.get(Calendar.DAY_OF_MONTH)
    fun currentHour() = calendar.get(Calendar.HOUR)
    fun currentMinute() = calendar.get(Calendar.MINUTE)
    fun getTimeStamp(
        minute: Minute,
        hour: Hour,
        dayOfMonth: DayOfMonth,
        month: Month,
        year: Year
    ): Long = with(Calendar.getInstance()) {
        set(year, month, dayOfMonth, hour, minute)
        calendar.timeInMillis
    }
}