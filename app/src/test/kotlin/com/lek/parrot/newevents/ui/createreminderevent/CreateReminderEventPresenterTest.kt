package com.lek.parrot.newevents.ui.createreminderevent

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.lek.parrot.BaseTest
import com.lek.parrot.shared.CreateEventInteractor
import com.lek.parrot.shared.IStringService
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class CreateReminderEventPresenterTest : BaseTest() {

    private val testString: String = ""
    private val createEventInteractor: CreateEventInteractor = mockk()
    private val mockLifeCycle: Lifecycle = mockk {
        every { addObserver(any()) } just runs
    }
    private val mockAppCompatActivity: AppCompatActivity = mockk {
        every { lifecycle } returns mockLifeCycle
    }
    private val mockInitFlow: Flow<CharSequence> = mockk()

    private val stringService: IStringService = mockk {
        every { getString(any()) } returns testString
    }

    private val testMessage = "message"
    private val view: CreateReminderEventContract.View = mockk {
        every { setDate(any(), any(), any()) } just runs
        every { setTime(any(), any()) } just runs
        every { showTimePicker() } just runs
        every { showError(any()) } just runs
        every { eventDateClick() } returns emptyFlow()
        every { showDatePickerDialog() } just runs
        every { eventTimeClick() } returns emptyFlow()
        every { createEventClicked() } returns emptyFlow()
        every { message() } returns mockInitFlow
        every { showSuccessMessage() } just runs
        every { scheduleNotification(any(), any()) } just runs
        every { onBack() }
    }

    private val presenter = CreateReminderEventPresenter(createEventInteractor, stringService)

    @BeforeEach
    override fun setUp() {
        super.setUp()
        presenter.attachToView(view, mockAppCompatActivity.lifecycle)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `on start - state is updated on view events`() = runBlockingTest {
        presenter.onStart()
        verify(exactly = 0) { createEventInteractor(any()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when all inputs are valid - event is created`() = runBlockingTest {
        every { view.message() } returns flowOf(testMessage)
        every { view.onBack() } just runs
        every { view.createEventClicked() } returns flowOf(Unit)
        every { view.setMessage(any()) } just runs
        every { createEventInteractor(any()) } returns emptyFlow()
        view.setMessage(testMessage)
        presenter.onDateSet(2022, 1, 1)
        presenter.onTimeSet(4, 39)
        view.createEventClicked()
        presenter.onStart()
        verify { createEventInteractor(any()) }
        verify { view.showSuccessMessage() }
        verify { view.scheduleNotification(any(), any()) }
        verify { view.onBack() }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when all inputs are invalid - event is not created`() = runBlockingTest {
        every { view.message() } returns flowOf(testMessage)
        presenter.onStart()
        verify(exactly = 0) { createEventInteractor(any()) }
    }
}