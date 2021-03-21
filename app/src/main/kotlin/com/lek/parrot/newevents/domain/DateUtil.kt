package com.lek.parrot.newevents.domain

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.HOUR
import java.util.Calendar.MINUTE
import java.util.Calendar.MONTH
import java.util.Calendar.YEAR
import java.util.Calendar.getInstance

object DateUtil {

    private val calendar = getInstance()

    fun currentMonth() = calendar.get(MONTH)
    fun currentYear() = calendar.get(YEAR)
    fun currentDay() = calendar.get(DAY_OF_MONTH)
    fun currentHour() = calendar.get(HOUR)
    fun currentMinute() = calendar.get(MINUTE)

    fun getFormattedDateTime(timestamp: Long): String =
        try {
            val sdf = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.getDefault())
            val netDate = Date(timestamp)
            sdf.format(netDate)
        } catch (e: Throwable) {
            "/-/-/"
        }

    fun isToday(
        date: Long,
        currentInstance: Calendar,
        newCalendar: Calendar = getInstance()
    ): Boolean = with(currentInstance) {
        newCalendar.timeInMillis = date

        newCalendar.get(YEAR) == get(YEAR) &&
                newCalendar.get(MONTH) == get(MONTH) &&
                newCalendar.get(DAY_OF_MONTH) == get(DAY_OF_MONTH)
    }

    fun getTimeStamp(
        minute: Minute,
        hour: Hour,
        dayOfMonth: DayOfMonth,
        month: Month,
        year: Year,
        calendar: Calendar = getInstance()
    ): Long = with(calendar) {
        set(year, month, dayOfMonth, hour, minute, 0)
        timeInMillis
    }
}