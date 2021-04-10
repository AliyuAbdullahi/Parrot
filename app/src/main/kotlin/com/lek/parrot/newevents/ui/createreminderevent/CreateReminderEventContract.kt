package com.lek.parrot.newevents.ui.createreminderevent

import androidx.work.Data
import com.lek.parrot.core.BaseView
import kotlinx.coroutines.flow.Flow

interface CreateReminderEventContract {

    interface Presenter {
        fun onTimeSet(hourOfDay: Int, minute: Int)
        fun onDateSet(year: Int, month: Int, dayOfMonth: Int)
    }

    interface View : BaseView {
        fun eventDate(eventDate: String)
        fun eventTime(eventTime: String)
        fun setMessage(message: String)
        fun message(): Flow<CharSequence>
        fun eventDateClick(): Flow<Unit>
        fun eventTimeClick(): Flow<Unit>
        fun createEventClicked(): Flow<Unit>
        fun setDate(day: Int, month: Int, year: Int)
        fun setTime(hour: Int, minute: Int)
        fun showDatePickerDialog()
        fun showTimePicker()
        fun showSuccessMessage()
        fun onBack()
        fun scheduleNotification(data: Data, delay: Long)
    }
}