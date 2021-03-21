package com.lek.parrot.newevents.ui

import androidx.lifecycle.viewModelScope
import com.lek.parrot.R
import com.lek.parrot.core.BasePresenter
import com.lek.parrot.newevents.domain.CreateEventState
import com.lek.parrot.newevents.domain.DataBuilder
import com.lek.parrot.newevents.domain.DateUtil
import com.lek.parrot.newevents.domain.validate
import com.lek.parrot.shared.CreateEventInteractor
import com.lek.parrot.shared.Event
import com.lek.parrot.shared.IStringService
import com.lek.parrot.shared.logDebug
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import timber.log.Timber

class CreateMessageEventPresenter(
    private val createEventInteractor: CreateEventInteractor,
    private val stringService: IStringService
) : BasePresenter<CreateMessageEventContract.View>(), CreateMessageEventContract.Presenter {

    private var eventState: CreateEventState = CreateEventState.MessageEvent()

    private val messageData get() = eventState as CreateEventState.MessageEvent
    private val messageEventState get() = eventState as CreateEventState.MessageEvent

    override fun onStart() {
        super.onStart()
        observeDateClicked()
        observeTimePickerDialog()
        observeAddButtonClicked()
        observePhoneNumber()
        observeName()
        observeMessage()
    }

    private fun observeDateClicked() = viewModelScope.launch {
        view.date()
            .handleError(stringService.getString(R.string.error_occurred))
            .collect { view.showDatePickerDialog() }
    }

    private fun observePhoneNumber() = viewModelScope.launch {
        view.phoneNumber().handleError(stringService.getString(R.string.error_occurred)).collect {
            (it as CharSequence).let { phoneNumber ->
                eventState = messageEventState.copy(phoneNumber = phoneNumber.toString())
            }
        }
    }

    private fun observeName() = viewModelScope.launch {
        view.receiverName().handleError(stringService.getString(R.string.error_occurred)).collect {
            (it as CharSequence).let { name ->
                eventState = messageEventState.copy(name = name.toString())
            }
        }
    }

    private fun observeMessage() = viewModelScope.launch {
        view.message().handleError(stringService.getString(R.string.error_occurred)).collect {
            (it as CharSequence).let { message ->
                eventState = messageEventState.copy(message = message.toString())
            }
        }
    }

    private fun observeTimePickerDialog() = viewModelScope.launch {
        view.time()
            .handleError(stringService.getString(R.string.error_occurred))
            .collect { view.showTimePicker() }
    }

    private fun observeAddButtonClicked() = viewModelScope.launch {
        view.onAddEventClicked()
            .catch { e ->
                Timber.e(e)
            }
            .collect {
                when (val result = eventState.validate()) {
                    CreateEventState.InvalidEventState -> view.showError(stringService.getString(R.string.error_create_event))
                    CreateEventState.InvalidTime -> view.showError(stringService.getString(R.string.invalid_time))
                    CreateEventState.InvalidMonth -> view.showError(stringService.getString(R.string.invalid_month))
                    CreateEventState.InvalidYear -> view.showError(stringService.getString(R.string.invalid_year))
                    CreateEventState.EmptyTime -> view.showError(stringService.getString(R.string.enter_event_time))
                    CreateEventState.EmptyMessage -> view.showAddMessageError(
                        stringService.getString(
                            R.string.enter_event_message
                        )
                    )
                    CreateEventState.EmptyReceiver -> view.showError(stringService.getString(R.string.enter_receiver))
                    CreateEventState.InvalidDay -> view.showError(stringService.getString(R.string.enter_valid_date))

                    is CreateEventState.MessageEvent -> {
                        val messageEvent = Event.MessageEvent(
                            result.name,
                            result.phoneNumber,
                            result.message,
                            System.currentTimeMillis(),
                            DateUtil.getTimeStamp(
                                result.minute,
                                result.hour,
                                result.dayOfMonth,
                                result.month,
                                result.year
                            ),
                            false
                        )
                        logDebug(messageEvent.toString())
                        createEvent(messageEvent)
                    }

                    else -> throw Error("Invalid State")
                }
            }
    }

    private suspend fun createEvent(messageEvent: Event.MessageEvent) {
        createEventInteractor(messageEvent)
            .handleError(stringService.getString(R.string.error_occurred))
            .onCompletion {
                view.showSuccessMessage()
                view.scheduleNotification(DataBuilder.buildFromMessage(messageEvent), getEventTimeStamp() - System.currentTimeMillis())
                view.onBack()
            }
            .collect()
    }

    private fun getEventTimeStamp() = DateUtil.getTimeStamp(
        messageData.minute,
        messageData.hour,
        messageData.dayOfMonth,
        messageData.month,
        messageData.year
    )

    override fun onTimeSet(hourOfDay: Int, minute: Int) {
        eventState =
            (eventState as CreateEventState.MessageEvent).copy(hour = hourOfDay, minute = minute)
        view.setTime(hourOfDay, minute)
    }

    override fun onDateSet(year: Int, month: Int, dayOfMonth: Int) {
        eventState = (eventState as CreateEventState.MessageEvent).copy(
            year = year,
            month = month,
            dayOfMonth = dayOfMonth
        )

        view.setDate(dayOfMonth, month, year)
    }
}