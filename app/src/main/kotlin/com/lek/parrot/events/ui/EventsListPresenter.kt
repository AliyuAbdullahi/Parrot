package com.lek.parrot.events.ui

import androidx.lifecycle.viewModelScope
import com.lek.parrot.R
import com.lek.parrot.core.BasePresenter
import com.lek.parrot.core.invoke
import com.lek.parrot.core.to
import com.lek.parrot.events.domain.EventsListInteractor
import com.lek.parrot.shared.Event
import com.lek.parrot.shared.IStringService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EventsListPresenter(
    private val interactor: EventsListInteractor,
    private val stringService: IStringService
) : BasePresenter<EventsListContract.View>(), EventsListContract.Presenter {

    override fun onStart() {
        super.onStart()
        observeEventList()
        observeCreateEventClick()
    }

    override fun onCreate() {
        super.onCreate()
        view.setTitle(stringService.getString(R.string.upcoming_events))
    }

    private fun observeCreateEventClick() = viewModelScope.launch {
        view.openMessageEvent().handleError(stringService.getString(R.string.error_occurred)).collect {
            view.startCreateEvent()
        }
    }

    private fun observeEventList() = viewModelScope.launch {
        interactor().handleError(stringService.getString(R.string.error_occurred)).collect { events ->
            (events?.to<List<Event>>())?.let {
                if (it.isEmpty()) {
                    view.showEmptyState(stringService.getString(R.string.event_empty_state_message))
                } else {
                    view.showEvents(it)
                }
            }
        }
    }
}
