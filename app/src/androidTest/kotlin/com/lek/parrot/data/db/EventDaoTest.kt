package com.lek.parrot.data.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class EventDaoTest {
    private lateinit var db: ParrotDb
//
//    private val coroutineDispatcher = TestCoroutineDispatcher()
//    private val scope = TestCoroutineScope(coroutineDispatcher)

    @Before
    fun openDb() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            ParrotDb::class.java
        ).build()
    }

    @Test
    fun testEmptyState() = runBlocking {
        val result = db.eventDao().getAllEvents()
        result.collect {
            assert(it.isEmpty())
        }
    }

    @After
    fun closeDb() {
        db.close()
    }
}