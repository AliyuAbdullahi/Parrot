package com.lek.parrot.di

import android.content.Context
import androidx.room.Room
import com.lek.parrot.data.db.DB_NAME
import com.lek.parrot.data.db.ParrotDb
import com.lek.parrot.data.DataEventToDomainEventMapper
import com.lek.parrot.data.EventDao
import com.lek.parrot.newevents.data.EventRepository
import com.lek.parrot.newevents.domain.IEventRepository
import com.lek.parrot.shared.CreateEventInteractor
import com.lek.parrot.shared.IStringService
import com.lek.parrot.shared.StringService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideParrotDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ParrotDb::class.java, DB_NAME).build()

    @Singleton
    @Provides
    fun provideEventDao(db: ParrotDb) = db.eventDao()

    @Singleton
    @Provides
    fun provideEventRepository(dao: EventDao): IEventRepository = EventRepository(dao, DataEventToDomainEventMapper)

    @Provides
    fun provideCreateEventInteractor(repository: IEventRepository) = CreateEventInteractor(repository, Dispatchers.Main)

    @Singleton
    @Provides
    fun provideStringService(@ApplicationContext context: Context): IStringService = StringService(context)
}