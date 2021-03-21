package com.lek.parrot.events.di

import com.lek.parrot.events.ui.EventsListContract
import com.lek.parrot.events.domain.EventsListInteractor
import com.lek.parrot.events.ui.EventsListPresenter
import com.lek.parrot.newevents.domain.IEventRepository
import com.lek.parrot.newevents.ui.CreateMessageEventStarter
import com.lek.parrot.newevents.ui.ICreateMessageEventStarter
import com.lek.parrot.shared.IStringService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent
import kotlinx.coroutines.Dispatchers

@InstallIn(ViewComponent::class)
@Module
object EventsListModule {

    @Provides
    fun provideEventsListInteractor(repository: IEventRepository) =
        EventsListInteractor(repository, Dispatchers.Main)

    @Provides
    fun provideEventsListPresenter(
        interactor: EventsListInteractor,
        stringService: IStringService
    ): EventsListContract.Presenter = EventsListPresenter(interactor, stringService)

    @Provides
    fun provideCreateMessageEventStarter(): ICreateMessageEventStarter = CreateMessageEventStarter()
}