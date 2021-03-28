package com.lek.parrot.newevents.ui.createmessageevent

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
import com.lek.parrot.databinding.ViewCreateMessageEventBinding
import com.lek.parrot.newevents.ui.CreateEventActivity
import com.lek.parrot.notification.NotificationScheduler
import com.lek.parrot.shared.DatePickerDialogStarter
import com.lek.parrot.shared.TimePickerDialogStarter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.android.widget.textChanges
import timber.log.Timber

@AndroidEntryPoint
class CreateMessageEventView @JvmOverloads constructor(
    @ActivityContext context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributes, defStyleAttr),
    CreateMessageEventContract.View,
    TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var presenter: CreateMessageEventContract.Presenter

    private val binding = ViewCreateMessageEventBinding.inflate(LayoutInflater.from(context), this, true)

    private val activity: CreateEventActivity get() = context as CreateEventActivity

    override fun onFinishInflate() {
        super.onFinishInflate()
        (presenter as CreateMessageEventPresenter).attachToView(
            this,
            activity.lifecycle
        )
    }

    override fun receiverNumber(): Flow<CharSequence> = binding.receiverNumber.textChanges().skipInitialValue()

    override fun date(): Flow<Unit> = binding.eventDate.clicks()

    override fun time(): Flow<Unit> = binding.eventTime.clicks()

    override fun onAddEventClicked() = binding.addEvent.clicks()

    override fun setReceiverName(name: String) {
        binding.receiverName.apply { setText(name) }
    }

    override fun setMessage(message: String) {
        binding.eventMessage.apply { setText(message) }
    }

    override fun setReceiverNumber(number: String) {
        binding.receiverNumber.apply { setText(number) }
    }

    override fun setDate(day: Int, month: Int, year: Int) {
        binding.eventDate.text = context.getString(R.string.date, day, month, year)
    }

    override fun setTime(hour: Int, minute: Int) {
        binding.eventTime.text = context.getString(R.string.hour_minute, hour, minute)
    }

    override fun showDatePickerDialog() {
        DatePickerDialogStarter.startDatePicker(context, this)
    }

    override fun showTimePicker() {
        TimePickerDialogStarter.startTimePicker(context, this)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Timber.d("Time set $hourOfDay, $minute")
        presenter.onTimeSet(hourOfDay, minute)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        presenter.onDateSet(year, month, dayOfMonth)
    }

    override fun showAddMessageError(s: String) {
        binding.eventMessage.error = s
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun phoneNumber(): Flow<CharSequence> = binding.receiverNumber.textChanges().skipInitialValue()

    override fun receiverName(): Flow<CharSequence> = binding.receiverName.textChanges().skipInitialValue()

    override fun message(): Flow<CharSequence> = binding.eventMessage.textChanges().skipInitialValue()

    override fun showSuccessMessage() {
        Toast.makeText(context, "You have successfully added an event", Toast.LENGTH_SHORT).show()
    }

    override fun scheduleNotification(data: Data, delay: Long) {
        NotificationScheduler.scheduleNotification(context, data, delay)
    }

    override fun onBack() {
        activity.onBackPressed()
    }
}
