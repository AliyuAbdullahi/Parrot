package com.lek.parrot.newevents.ui.createmessageevent

import androidx.work.Data
import com.lek.parrot.core.BaseView
import kotlinx.coroutines.flow.Flow

interface CreateMessageEventContract {
    interface Presenter {
        fun onTimeSet(hourOfDay: Int, minute: Int)
        fun onDateSet(year: Int, month: Int, dayOfMonth: Int)
    }

    interface View : BaseView {
        fun receiverNumber(): Flow<CharSequence>
        fun date(): Flow<Unit>
        fun time(): Flow<Unit>
        fun onAddEventClicked(): Flow<Unit>
        fun setReceiverName(name: String)
        fun setReceiverNumber(number: String)
        fun setDate(day: Int, month: Int, year: Int)
        fun setTime(hour: Int, minute: Int)
        fun showDatePickerDialog()
        fun showTimePicker()
        fun showAddMessageError(s: String)
        fun phoneNumber(): Flow<CharSequence>
        fun receiverName(): Flow<CharSequence>
        fun message(): Flow<CharSequence>
        fun showSuccessMessage()
        fun onBack()
        fun scheduleNotification(data: Data, delay: Long)
        fun setMessage(message: String)
    }
}