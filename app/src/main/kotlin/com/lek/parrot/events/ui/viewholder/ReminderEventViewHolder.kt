package com.lek.parrot.events.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.lek.parrot.R
import com.lek.parrot.databinding.ViewReminderEventListItemBinding
import com.lek.parrot.newevents.domain.DateUtil
import com.lek.parrot.shared.Event

class ReminderEventViewHolder(
    private val binding: ViewReminderEventListItemBinding,
    clickLister: (Event) -> Unit,
    deletedListener: (Event) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var event: Event

    init {
        binding.root.setOnClickListener { clickLister(event) }
        binding.deleteEvent.setOnClickListener { deletedListener(event) }
    }

    fun bindTo(event: Event.ReminderEvent) {
        this.event = event
        with(binding) {
            val context = binding.root.context
            message.text = event.message
            createdAt.text = context.getString(R.string.created_at, DateUtil.getFormattedDateTime(event.createdAt))
            remindAt.text = context.getString(R.string.reminder_at, DateUtil.getFormattedDateTime(event.fireAt))
        }
    }
}