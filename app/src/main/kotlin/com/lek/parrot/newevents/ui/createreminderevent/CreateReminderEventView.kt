package com.lek.parrot.newevents.ui.createreminderevent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.work.Data
import com.lek.parrot.R
import com.lek.parrot.databinding.ViewCreateReminderEventBinding
import com.lek.parrot.events.ui.UpcomingEventsActivity
import com.lek.parrot.newevents.domain.ScreenType
import com.lek.parrot.newevents.ui.OnBackListener
import com.lek.parrot.notification.NotificationScheduler
import com.lek.parrot.shared.DatePickerDialogStarter
import com.lek.parrot.shared.TimePickerDialogStarter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.android.widget.textChanges

@AndroidEntryPoint
class CreateReminderEventView @JvmOverloads constructor(
    @ActivityContext context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributes, defStyleAttr),
    CreateReminderEventContract.View,
    TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var presenter: CreateReminderEventContract.Presenter

    private var onBackListener: OnBackListener? = null

    private val binding =
        ViewCreateReminderEventBinding.inflate(LayoutInflater.from(context), this, true)

    override fun eventDate(eventDate: String) {
        binding.eventDate.text = eventDate
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        requireNotNull(UpcomingEventsActivity.activityLifecycle).let {
            (presenter as CreateReminderEventPresenter).attachToView(
                this,
                it
            )
        }
    }

    override fun eventTime(eventTime: String) {
        binding.eventTime.text = eventTime
    }

    override fun setMessage(message: String) {
        binding.message.apply {
            setText(message)
        }
    }

    override fun eventDateClick(): Flow<Unit> = binding.eventDate.clicks()

    override fun eventTimeClick(): Flow<Unit> = binding.eventTime.clicks()

    override fun createEventClicked(): Flow<Unit> = binding.addEvent.clicks()

    override fun message(): Flow<CharSequence> = binding.message.textChanges()

    override fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun setDate(day: Int, month: Int, year: Int) {
        binding.eventDate.text = context?.getString(R.string.date, day, month, year) ?: ""
    }

    override fun setTime(hour: Int, minute: Int) {
        binding.eventTime.text = context?.getString(R.string.hour_minute, hour, minute) ?: ""
    }

    override fun showDatePickerDialog() {
        context?.let { DatePickerDialogStarter.startDatePicker(it, this) }
    }

    override fun showTimePicker() {
        context?.let { TimePickerDialogStarter.startTimePicker(it, this) }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        presenter.onTimeSet(hourOfDay, minute)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        presenter.onDateSet(year, month, dayOfMonth)
    }

    override fun showSuccessMessage() {
        Toast.makeText(context, "Reminder set successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onBack() {
        onBackListener?.onBack(ScreenType.REMINDER)
    }

    override fun scheduleNotification(data: Data, delay: Long) {
        context?.let { NotificationScheduler.scheduleNotification(it, data, delay) }
    }

    fun setOnBackListener(onBackListener: OnBackListener) {
        this.onBackListener = onBackListener
    }
}