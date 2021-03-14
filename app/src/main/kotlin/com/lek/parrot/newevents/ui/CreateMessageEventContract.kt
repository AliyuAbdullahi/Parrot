package com.lek.parrot.newevents.ui

import kotlinx.coroutines.flow.Flow

interface CreateMessageEventContract {
    interface Presenter {
        fun onTimeSet(hourOfDay: Int, minute: Int)
        fun onDateSet(year: Int, month: Int, dayOfMonth: Int)
    }

    interface View {
        fun receiverNumber(): Flow<CharSequence>
        fun date(): Flow<Unit>
        fun time(): Flow<Unit>
        fun onAddEventClicked(): Flow<Unit>
        fun setReceiverName(name: String)
        fun setReceiverNumber(number: String)
        fun setDate(date: String)
        fun setTime(time: String)
        fun showDatePickerDialog()
        fun showTimePicker()
        fun showAddMessageError(s: String)
        fun showError(errorMessage: String)
        fun phoneNumber(): Flow<CharSequence>
        fun receiverName(): Flow<CharSequence>
        fun message(): Flow<CharSequence>
        fun showSuccessMessage()
    }
}