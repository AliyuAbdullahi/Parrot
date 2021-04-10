package com.lek.parrot.events.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.lek.parrot.databinding.ViewEventsListviewBinding
import com.lek.parrot.events.ui.adapter.EventListAdapter
import com.lek.parrot.newevents.domain.ScreenType
import com.lek.parrot.newevents.ui.createreminderevent.CreateEventFragment
import com.lek.parrot.shared.Event
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import reactivecircus.flowbinding.android.view.clicks
import timber.log.Timber

@AndroidEntryPoint
class EventsListView @JvmOverloads constructor(
    @ActivityContext context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr), EventsListContract.View {

    @Inject
    lateinit var presenter: EventsListContract.Presenter

    private val binding =
        ViewEventsListviewBinding.inflate(LayoutInflater.from(context), this, true)

    private val eventClickedListener = { event: Event ->
        Toast.makeText(context, event.eventId, Toast.LENGTH_SHORT).show()
    }

    private val eventDeletedListener = { event: Event ->
        Toast.makeText(context, event.eventId, Toast.LENGTH_SHORT).show()
    }

    private val activity: UpcomingEventsActivity get() = context as UpcomingEventsActivity

    private val listAdapter = EventListAdapter(eventClickedListener, eventDeletedListener)

    init {
        binding.eventsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        (presenter as EventsListPresenter).attachToView(this, activity.lifecycle)
        binding.motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                Timber.d("Started $p1 Ended $p2")
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {}
        })
    }

    override fun openMessageEvent(): Flow<Unit> = binding.createMessageEvent.clicks()

    override fun openCallEvent(): Flow<Unit> = binding.createCallEvent.clicks()

    override fun startCreateMessageEvent() = CreateEventFragment.show(
        (context as AppCompatActivity).supportFragmentManager,
        ScreenType.MESSAGE
    )

    override fun openReminderEventClick() = binding.createReminder.clicks()

    override fun openReminderEvent() = CreateEventFragment.show(
        (context as AppCompatActivity).supportFragmentManager,
        ScreenType.REMINDER
    )

    override fun startCreateCallEvent() = CreateEventFragment.show(
        (context as AppCompatActivity).supportFragmentManager,
        ScreenType.CALL
    )

    override fun setTitle(title: String) {
        binding.title.text = title
    }

    override fun showEmptyState(emptyStateMessage: String) {
        binding.emptyStateMessage.text = emptyStateMessage
    }

    override fun showEvents(events: List<Event>) {
        listAdapter.submitList(events)
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}