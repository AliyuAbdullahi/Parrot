package com.lek.parrot.newevents.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.lek.parrot.databinding.ViewCreateEventBinding
import com.lek.parrot.shared.DatePickerDialogStarter
import com.lek.parrot.shared.TimePickerDialogStarter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.android.widget.textChanges
import reactivecircus.flowbinding.common.InitialValueFlow
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

    private val binding = ViewCreateEventBinding.inflate(LayoutInflater.from(context), this, true)

    override fun onFinishInflate() {
        super.onFinishInflate()
        (presenter as CreateMessageEventPresenter).attachToView(
            this,
            context as CreateEventActivity
        )
    }

    override fun receiverNumber(): InitialValueFlow<CharSequence> =
        binding.receiverNumber.textChanges()

    override fun date(): Flow<Unit> = binding.eventDate.clicks()

    override fun time(): Flow<Unit> = binding.eventTime.clicks()

    override fun onAddEventClicked() = binding.addEvent.clicks()

    override fun setReceiverName(name: String) {
        binding.receiverName.apply { setText(name) }
    }

    override fun setReceiverNumber(number: String) {
        binding.receiverNumber.apply { setText(number) }
    }

    override fun setDate(date: String) {
        binding.eventDate.text = date
    }

    override fun setTime(time: String) {
        binding.eventTime.text = time
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

    override fun phoneNumber(): InitialValueFlow<CharSequence> = binding.receiverNumber.textChanges()

    override fun receiverName(): InitialValueFlow<CharSequence> = binding.receiverName.textChanges()

    override fun message(): InitialValueFlow<CharSequence> = binding.eventMessage.textChanges()

    override fun showSuccessMessage() {
        Toast.makeText(context, "You have successfully added an event", Toast.LENGTH_SHORT).show()
    }
}
