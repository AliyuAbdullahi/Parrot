package com.lek.parrot.newevents.ui.createcallevent

import androidx.lifecycle.viewModelScope
import com.lek.parrot.R
import com.lek.parrot.core.BasePresenter
import com.lek.parrot.newevents.domain.CreateEventState
import com.lek.parrot.newevents.domain.DataBuilder
import com.lek.parrot.newevents.domain.DateUtil
import com.lek.parrot.newevents.domain.validateCallEvent
import com.lek.parrot.shared.CreateEventInteractor
import com.lek.parrot.shared.Event
import com.lek.parrot.shared.IStringService
import com.lek.parrot.shared.logDebug
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import timber.log.Timber

class CreateCallEventPresenter(
    private val createEventInteractor: CreateEventInteractor,
    private val stringService: IStringService
) : BasePresenter<CreateCallEventContract.View>(), CreateCallEventContract.Presenter {

    private var eventState: CreateEventState = CreateEventState.CallEvent()

    private val callData get() = eventState as CreateEventState.CallEvent
    private val callEventState get() = eventState as CreateEventState.CallEvent

    override fun onStart() {
        super.onStart()
        observeDateClicked()
        observeTimePickerDialog()
        observeAddButtonClicked()
        observePhoneNumber()
        observeName()
    }

    private fun observeDateClicked() = viewModelScope.launch {
        view.date()
            .handleError(stringService.getString(R.string.error_occurred))
            .collect { view.showDatePickerDialog() }
    }

    private fun observePhoneNumber() = viewModelScope.launch {
        view.phoneNumber().handleError(stringService.getString(R.string.error_occurred)).collect {
            (it as CharSequence).let { phoneNumber ->
                eventState = callEventState.copy(phoneNumber = phoneNumber.toString())
            }
        }
    }

    private fun observeName() = viewModelScope.launch {
        view.receiverName().handleError(stringService.getString(R.string.error_occurred)).collect {
            (it as CharSequence).let { name ->
                eventState = callEventState.copy(name = name.toString())
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
            .catch { e -> Timber.e(e) }
            .collect {
                when (val result = eventState.validateCallEvent()) {
                    CreateEventState.InvalidEventState -> view.showError(stringService.getString(R.string.error_create_event))
                    CreateEventState.InvalidTime -> view.showError(stringService.getString(R.string.invalid_time))
                    CreateEventState.InvalidMonth -> view.showError(stringService.getString(R.string.invalid_month))
                    CreateEventState.InvalidYear -> view.showError(stringService.getString(R.string.invalid_year))
                    CreateEventState.EmptyTime -> view.showError(stringService.getString(R.string.enter_event_time))
                    CreateEventState.EmptyReceiver -> view.showError(stringService.getString(R.string.enter_receiver))
                    CreateEventState.InvalidDay -> view.showError(stringService.getString(R.string.enter_valid_date))

                    is CreateEventState.CallEvent -> {
                        val callEvent = Event.CallEvent(
                            result.name,
                            result.phoneNumber,
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
                        logDebug(callEvent.toString())
                        createEvent(callEvent)
                    }
                }
            }
    }

    private suspend fun createEvent(messageEvent: Event.CallEvent) {
        createEventInteractor(messageEvent)
            .handleError(stringService.getString(R.string.error_occurred))
            .onCompletion {
                view.showSuccessMessage()
                view.scheduleNotification(
                    DataBuilder.buildFromCallEvent(messageEvent),
                    getEventTimeStamp() - System.currentTimeMillis()
                )
                view.onBack()
            }
            .collect()
    }

    private fun getEventTimeStamp() = DateUtil.getTimeStamp(
        callData.minute,
        callData.hour,
        callData.dayOfMonth,
        callData.month,
        callData.year
    )

    override fun onTimeSet(hourOfDay: Int, minute: Int) {
        eventState =
            (eventState as CreateEventState.CallEvent).copy(hour = hourOfDay, minute = minute)
        view.setTime(hourOfDay, minute)
    }

    override fun onDateSet(year: Int, month: Int, dayOfMonth: Int) {
        eventState = (eventState as CreateEventState.CallEvent).copy(
            year = year,
            month = month,
            dayOfMonth = dayOfMonth
        )

        view.setDate(dayOfMonth, month, year)
    }
}