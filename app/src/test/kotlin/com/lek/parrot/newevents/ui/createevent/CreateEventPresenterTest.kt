package com.lek.parrot.newevents.ui.createevent

import androidx.lifecycle.Lifecycle
import com.lek.parrot.BaseTest
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class CreateEventPresenterTest : BaseTest() {

    private val createEventView: CreateEventContract.View = mockk {
        every { showMessageEvent() } just runs
        every { showCallEvent() } just runs
        every { onShowMessageEvent() } just runs
        every { onShowCallEvent() } just runs
    }
    private val mockLifeCycle: Lifecycle = mockk {
        every { addObserver(any()) } just runs
    }

    @BeforeEach
    override fun setUp() {
        super.setUp()
        presenter.attachToView(createEventView, mockLifeCycle)
    }

    private val presenter = CreateEventPresenter()

    @Test
    fun `when message event is selected - presenter calls showMessageEvent`() = dispatcher.runBlockingTest {
        presenter.onShowMessageEvent()
        verify { createEventView.onShowMessageEvent() }
    }

    @Test
    fun `when call event is selected - presenter calls showCallEvent`() = dispatcher.runBlockingTest {
        presenter.onShowCallEvent()
        verify { createEventView.onShowCallEvent() }
    }
}