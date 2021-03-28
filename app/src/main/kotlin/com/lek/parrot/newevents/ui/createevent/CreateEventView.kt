package com.lek.parrot.newevents.ui.createevent

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.lek.parrot.databinding.ViewCreateEventBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateEventView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr), CreateEventContract.View {

    @Inject
    lateinit var presenter: CreateEventContract.Presenter

    private val binding = ViewCreateEventBinding.inflate(LayoutInflater.from(context), this, true)

    override fun onFinishInflate() {
        super.onFinishInflate()
        (presenter as CreateEventPresenter).attachToView(this, (context as AppCompatActivity).lifecycle)
    }

    override fun showMessageEvent() {
        presenter.onShowMessageEvent()
    }

    override fun onShowMessageEvent() {
        binding.createMessageEvent.visibility = View.VISIBLE
        binding.createCallEvent.visibility = View.GONE
    }

    override fun onShowCallEvent() {
        binding.createCallEvent.visibility = View.VISIBLE
        binding.createMessageEvent.visibility = View.GONE
    }

    override fun showCallEvent() {
        presenter.onShowCallEvent()
    }

    override fun showError(errorMessage: String) {
        // no ops
    }
}