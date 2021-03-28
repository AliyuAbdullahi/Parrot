package com.lek.parrot.newevents.ui.createcallevent

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
internal class CreateCallEventPresenterTest : BaseTest() {

    private val testString: String = ""
    private val createEventInteractor: CreateEventInteractor = mockk()
    private val mockLifeCycle: Lifecycle = mockk {
        every { addObserver(any()) } just runs
    }

    private val mockInitFlow: Flow<CharSequence> = mockk()

    private val stringService: IStringService = mockk {
        every { getString(any()) } returns testString
    }

    private val testPhoneNumber = "084331"
    private val testName = "testName"
    private val view: CreateCallEventContract.View = mockk {
        every { receiverNumber() } returns mockInitFlow
        every { date() } returns flowOf(Unit)
        every { time() } returns flowOf(Unit)
        every { onAddEventClicked() } returns emptyFlow()
        every { setReceiverName(any()) } just runs
        every { setReceiverNumber(any()) } just runs
        every { setDate(any(), any(), any()) } just runs
        every { setTime(any(), any()) } just runs
        every { showDatePickerDialog() } just runs
        every { showTimePicker() } just runs
        every { showError(any()) } just runs
        every { phoneNumber() } returns mockInitFlow
        every { receiverName() } returns mockInitFlow
        every { showSuccessMessage() } just runs
        every { scheduleNotification(any(), any()) } just runs
        every { onBack() }
    }

    private val presenter = CreateCallEventPresenter(createEventInteractor, stringService)

    @BeforeEach
    override fun setUp() {
        super.setUp()
        presenter.attachToView(view, mockLifeCycle)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `on start - state is updated on view events`() = runBlockingTest {
        presenter.onStart()
        verify { view.showDatePickerDialog() }
        verify { view.showTimePicker() }
        verify(exactly = 0) { createEventInteractor(any()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when all inputs are valid - event is created`() = dispatcher.runBlockingTest {
        every { view.phoneNumber() } returns flowOf(testPhoneNumber)
        every { view.receiverName() } returns flowOf(testName)
        every { view.onAddEventClicked() } returns flowOf(Unit)
        every { view.onBack() } just runs
        every { createEventInteractor(any()) } returns flowOf(Unit)
        presenter.onDateSet(2050, 12, 12)
        presenter.onTimeSet(4, 39)
        presenter.onStart()
        view.onAddEventClicked()
        dispatcher.advanceTimeBy(1)
        verify { createEventInteractor(any()) }
        verify { view.showSuccessMessage() }
        verify { view.scheduleNotification(any(), any()) }
        verify { view.onBack() }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when all inputs are invalid - event is not created`() = runBlockingTest {
        every { view.phoneNumber() } returns flowOf(testPhoneNumber)
        every { view.receiverName() } returns flowOf(testName)
        presenter.onStart()
        verify(exactly = 0) { createEventInteractor(any()) }
    }
}