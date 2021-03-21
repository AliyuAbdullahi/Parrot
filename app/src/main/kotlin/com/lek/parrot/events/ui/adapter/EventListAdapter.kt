package com.lek.parrot.events.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lek.parrot.databinding.ViewCallEventListItemBinding
import com.lek.parrot.databinding.ViewMessageEventListItemBinding
import com.lek.parrot.databinding.ViewReminderEventListItemBinding
import com.lek.parrot.events.ui.viewholder.CallEventViewHolder
import com.lek.parrot.events.ui.viewholder.MessageEventViewHolder
import com.lek.parrot.events.ui.viewholder.ReminderEventViewHolder
import com.lek.parrot.shared.Event

private const val MESSAGE_EVENT = 1
private const val CALL_EVENT = 2
private const val REMINDER_EVENT = 3

class EventListAdapter(
    private val clickListener: (Event) -> Unit,
    private val deletedListener: (Event) -> Unit
) : ListAdapter<Event, RecyclerView.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            MESSAGE_EVENT -> {
                val binding = ViewMessageEventListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                MessageEventViewHolder(binding, clickListener, deletedListener)
            }
            CALL_EVENT -> {
                val binding = ViewCallEventListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CallEventViewHolder(binding, clickListener, deletedListener)
            }
            REMINDER_EVENT -> {
                val binding = ViewReminderEventListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ReminderEventViewHolder(binding, clickListener, deletedListener)
            }
            else -> throw Error("Unsupported View Type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MESSAGE_EVENT -> (holder as MessageEventViewHolder).bindTo(getItem(position) as Event.MessageEvent)
            CALL_EVENT -> (holder as CallEventViewHolder).bindTo(getItem(position) as Event.CallEvent)
            REMINDER_EVENT -> (holder as ReminderEventViewHolder).bindTo(getItem(position) as Event.ReminderEvent)
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is Event.MessageEvent -> MESSAGE_EVENT
        is Event.CallEvent -> CALL_EVENT
        is Event.ReminderEvent -> REMINDER_EVENT
    }
}

private val DIFF_UTIL = object : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.eventId == newItem.eventId
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }
}