package com.lek.parrot.newevents.di

import com.lek.parrot.newevents.ui.CreateMessageEventContract
import com.lek.parrot.newevents.ui.CreateMessageEventPresenter
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
    fun provideCreateEventPresenter(
        interactor: CreateEventInteractor,
        stringService: IStringService
    ): CreateMessageEventContract.Presenter =
        CreateMessageEventPresenter(interactor, stringService)
}