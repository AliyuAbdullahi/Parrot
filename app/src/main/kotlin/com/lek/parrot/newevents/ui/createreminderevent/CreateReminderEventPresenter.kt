package com.lek.parrot.newevents.ui.createreminderevent

import androidx.lifecycle.viewModelScope
import com.lek.parrot.R
import com.lek.parrot.core.BasePresenter
import com.lek.parrot.newevents.domain.CreateEventState
import com.lek.parrot.newevents.domain.DataBuilder
import com.lek.parrot.newevents.domain.DateUtil
import com.lek.parrot.newevents.domain.validateReminderEvent
import com.lek.parrot.shared.CreateEventInteractor
import com.lek.parrot.shared.Event
import com.lek.parrot.shared.IStringService
import com.lek.parrot.shared.logDebug
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class CreateReminderEventPresenter(
    private val interactor: CreateEventInteractor,
    private val stringService: IStringService
) : BasePresenter<CreateReminderEventContract.View>(), CreateReminderEventContract.Presenter {

    private var eventState = CreateEventState.ReminderEvent()

    override fun onStart() {
        super.onStart()
        observeEventDateClick()
        observeEventTimeClick()
        observeMessage()
        observeAddEventClick()
    }

    private fun observeEventDateClick() = viewModelScope.launch {
        view.eventDateClick()
            .handleError(stringService.getString(R.string.error_occurred))
            .collect {
                view.showDatePickerDialog()
            }
    }

    private fun observeAddEventClick() = viewModelScope.launch {
        view.createEventClicked()
            .handleError(stringService.getString(R.string.error_occurred))
            .collect {
                when (val validateResult = eventState.validateReminderEvent()) {
                    CreateEventState.InvalidEventState -> view.showError(stringService.getString(R.string.error_create_event))
                    CreateEventState.InvalidTime -> view.showError(stringService.getString(R.string.invalid_time))
                    CreateEventState.InvalidMonth -> view.showError(stringService.getString(R.string.invalid_month))
                    CreateEventState.InvalidYear -> view.showError(stringService.getString(R.string.invalid_year))
                    CreateEventState.EmptyTime -> view.showError(stringService.getString(R.string.enter_event_time))
                    CreateEventState.EmptyMessage -> view.showError(
                        stringService.getString(
                            R.string.enter_event_message
                        )
                    )
                    CreateEventState.EmptyReceiver -> view.showError(stringService.getString(R.string.enter_receiver))
                    CreateEventState.InvalidDay -> view.showError(stringService.getString(R.string.enter_valid_date))

                    is CreateEventState.ReminderEvent -> {
                        val reminderEvent = Event.ReminderEvent(
                            validateResult.message,
                            System.currentTimeMillis(),
                            DateUtil.getTimeStamp(
                                validateResult.minute,
                                validateResult.hour,
                                validateResult.dayOfMonth,
                                validateResult.month,
                                validateResult.year
                            ),
                            false
                        )
                        logDebug(reminderEvent.toString())
                        createEvent(reminderEvent)
                    }
                    else -> {}
                }
            }
    }

    private suspend fun createEvent(reminderEvent: Event.ReminderEvent) {
        interactor(reminderEvent)
            .handleError(stringService.getString(R.string.error_occurred))
            .onCompletion {
                view.showSuccessMessage()
                view.scheduleNotification(DataBuilder.buildForReminderEvent(reminderEvent), getEventTimeStamp() - System.currentTimeMillis())
                view.onBack()
            }
            .collect()
    }

    private fun getEventTimeStamp() = DateUtil.getTimeStamp(
        eventState.minute,
        eventState.hour,
        eventState.dayOfMonth,
        eventState.month,
        eventState.year
    )

    private fun observeEventTimeClick() = viewModelScope.launch {
        view.eventTimeClick()
            .handleError(stringService.getString(R.string.error_occurred))
            .collect {
                view.showTimePicker()
            }
    }

    private fun observeMessage() = viewModelScope.launch {
        view.message()
            .handleError(stringService.getString(R.string.error_occurred))
            .collect { message ->
                eventState = eventState.copy(message = message?.toString() ?: "")
            }
    }

    override fun onTimeSet(hourOfDay: Int, minute: Int) {
        eventState = eventState.copy(minute = minute, hour = hourOfDay)
        view.setTime(hourOfDay, minute)
    }

    override fun onDateSet(year: Int, month: Int, dayOfMonth: Int) {
        eventState = eventState.copy(year = year, month = month, dayOfMonth = dayOfMonth)
        view.setDate(dayOfMonth, month, year)
    }
}