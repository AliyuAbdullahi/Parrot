package com.lek.parrot.events.domain

import com.lek.parrot.core.Interactor
import com.lek.parrot.newevents.domain.IEventRepository
import com.lek.parrot.shared.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class EventsListInteractor(
    private val repository: IEventRepository,
    dispatcher: CoroutineDispatcher
) : Interactor<Unit, List<Event>>(dispatcher) {

    override fun run(params: Unit): Flow<List<Event>> = repository.getEvents()
}