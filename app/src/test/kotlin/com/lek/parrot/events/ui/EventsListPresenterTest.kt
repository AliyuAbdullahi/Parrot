package com.lek.parrot.events.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.lek.parrot.core.invoke
import com.lek.parrot.events.domain.EventsListInteractor
import com.lek.parrot.mockEvent
import com.lek.parrot.shared.IStringService
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class EventsListPresenterTest {
    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    private val interactor: EventsListInteractor = mockk()
    private val stringService: IStringService = mockk()
    private val view: EventsListContract.View = mockk()

    private val mockLifeCycle: Lifecycle = mockk {
        every { addObserver(any()) } just runs
    }
    private val mockAppCompatActivity: AppCompatActivity = mockk {
        every { lifecycle } returns mockLifeCycle
    }

    private val presenter = EventsListPresenter(interactor, stringService)

    private val testEvent = mockEvent()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        val testString = "testString"
        every { stringService.getString(any()) } returns testString
        presenter.attachToView(view, mockAppCompatActivity)
    }

    @Test
    fun `on create - view title is set`() = runBlockingTest {
        every { view.setTitle(any()) } just runs
        presenter.onCreate()
        verify { view.setTitle(any()) }
    }

    @Test
    fun `when there is list - view display items`() = runBlockingTest {
        every { interactor() } returns flowOf(listOf(testEvent))
        every { view.showEvents(any()) } just runs
        every { view.openMessageEvent() } returns emptyFlow()
        presenter.onStart()
        verify { view.showEvents(listOf(testEvent)) }
    }

    @Test
    fun `on empty list - view display empty message`() {
        every { interactor() } returns flowOf(listOf())
        every { view.showEvents(any()) } just runs
        every { view.openMessageEvent() } returns emptyFlow()
        every { view.showEmptyState(any()) } just runs
        presenter.onStart()
        verify(exactly = 0) { view.showEvents(any()) }
        verify { view.showEmptyState(any()) }
    }
}