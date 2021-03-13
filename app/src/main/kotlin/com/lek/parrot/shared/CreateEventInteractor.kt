package com.lek.parrot.shared

import com.lek.parrot.core.Interactor
import com.lek.parrot.newevents.domain.IEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateEventInteractor(private val repository: IEventRepository) : Interactor<Event, Unit>() {
    override fun run(params: Event): Flow<Unit> = flow { repository.createEvent(params) }
}