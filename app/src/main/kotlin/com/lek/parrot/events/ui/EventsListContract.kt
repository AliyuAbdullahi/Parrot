package com.lek.parrot.events.ui

import com.lek.parrot.core.BaseView
import com.lek.parrot.shared.Event
import kotlinx.coroutines.flow.Flow

interface EventsListContract {
    interface Presenter

    interface View : BaseView {
        fun showEvents(events: List<Event>)
        fun setTitle(title: String)
        fun openMessageEvent(): Flow<Unit>
        fun startCreateMessageEvent()
        fun showEmptyState(emptyStateMessage: String)
        fun openCallEvent(): Flow<Unit>
        fun startCreateCallEvent()
    }
}