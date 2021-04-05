package com.lek.parrot.newevents.di

import com.lek.parrot.newevents.ui.createcallevent.CreateCallEventContract
import com.lek.parrot.newevents.ui.createcallevent.CreateCallEventPresenter
import com.lek.parrot.newevents.ui.createevent.CreateEventContract
import com.lek.parrot.newevents.ui.createevent.CreateEventPresenter
import com.lek.parrot.newevents.ui.createmessageevent.CreateMessageEventContract
import com.lek.parrot.newevents.ui.createmessageevent.CreateMessageEventPresenter
import com.lek.parrot.newevents.ui.createreminderevent.CreateReminderEventContract
import com.lek.parrot.newevents.ui.createreminderevent.CreateReminderEventPresenter
import com.lek.parrot.shared.CreateEventInteractor
import com.lek.parrot.shared.IStringService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent

@Module
@InstallIn(ViewComponent::class)
object NewEventModule {

    @Provides
    fun provideCreateMessageEventPresenter(
        interactor: CreateEventInteractor,
        stringService: IStringService
    ): CreateMessageEventContract.Presenter = CreateMessageEventPresenter(interactor, stringService)

    @Provides
    fun provideCreateCallEventPresenter(
        interactor: CreateEventInteractor,
        stringService: IStringService
    ): CreateCallEventContract.Presenter = CreateCallEventPresenter(interactor, stringService)

    @Provides
    fun provideCreateReminderEventPresenter(
        interactor: CreateEventInteractor,
        stringService: IStringService
    ): CreateReminderEventContract.Presenter = CreateReminderEventPresenter(interactor, stringService)

    @Provides
    fun provideCreateEventPresenter(): CreateEventContract.Presenter = CreateEventPresenter()
}