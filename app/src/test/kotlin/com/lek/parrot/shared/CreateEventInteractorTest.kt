package com.lek.parrot.shared

import app.cash.turbine.test
import com.lek.parrot.mockEvent
import com.lek.parrot.newevents.domain.IEventRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class CreateEventInteractorTest {

    private val repository: IEventRepository = mockk {
        coEvery { createEvent(any()) } just runs
    }
    private val testDispatcher = TestCoroutineDispatcher()

    private val interactor = CreateEventInteractor(repository, testDispatcher)
    private val event = mockEvent()

    @ExperimentalTime
    @Test
    fun `on invoke - interactor creates event`() = testDispatcher.runBlockingTest {
        interactor(event).test {
            expectComplete()
            coVerify { repository.createEvent(event) }
        }
    }
}