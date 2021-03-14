package com.lek.parrot.newevents.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.lek.parrot.shared.CreateEventInteractor
import com.lek.parrot.shared.IStringService
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class CreateMessageEventPresenterTest {

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

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

    private val testPhoneNumber = "084331"
    private val testMessage = "message"
    private val testName = "testName"

    private val view: CreateMessageEventContract.View = mockk {
        every { receiverNumber() } returns mockInitFlow
        every { date() } returns flowOf(Unit)
        every { time() } returns flowOf(Unit)
        every { onAddEventClicked() } returns emptyFlow()
        every { setReceiverName(any()) } just runs
        every { setReceiverNumber(any()) } just runs
        every { setDate(any()) } just runs
        every { setTime(any()) } just runs
        every { showDatePickerDialog() } just runs
        every { showTimePicker() } just runs
        every { showAddMessageError(any()) } just runs
        every { showError(any()) } just runs
        every { phoneNumber() } returns mockInitFlow
        every { receiverName() } returns mockInitFlow
        every { message() } returns mockInitFlow
        every { showSuccessMessage() } just runs
    }

    private val presenter = CreateMessageEventPresenter(createEventInteractor, stringService)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        presenter.attachToView(view, mockAppCompatActivity)
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
    fun `when all inputs are valid - event is created`() = runBlockingTest {
        every { view.phoneNumber() } returns flowOf(testPhoneNumber)
        every { view.receiverName() } returns flowOf(testName)
        every { view.message() } returns flowOf(testMessage)
        every { view.onAddEventClicked() } returns flowOf(Unit)
        every { createEventInteractor(any()) } returns emptyFlow()
        presenter.onDateSet(2022, 1, 1)
        presenter.onTimeSet(4, 39)
        view.onAddEventClicked()
        presenter.onStart()
        verify { createEventInteractor(any()) }
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