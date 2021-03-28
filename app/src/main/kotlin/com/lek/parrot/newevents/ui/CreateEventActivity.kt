package com.lek.parrot.newevents.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lek.parrot.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        processIntent(binding)
    }

    private fun processIntent(binding: ActivityMainBinding) {
        if (intent.hasExtra(CREATE_EVENT_TYPE)) {
            when (createEventTypeFromOrdinal(intent.getIntExtra(CREATE_EVENT_TYPE, UNKNOWN_SCREEN))) {
                CreateEventType.MESSAGE -> binding.createEventView.showMessageEvent()
                CreateEventType.CALL -> binding.createEventView.showCallEvent()
            }
        }
    }

    companion object {
        const val CREATE_EVENT_TYPE = "CREATE_EVENT_TYPE"
        const val UNKNOWN_SCREEN = -1
        fun start(context: Context, eventType: CreateEventType) =
            context.startActivity(
                Intent(context, CreateEventActivity::class.java).apply {
                    putExtra(CREATE_EVENT_TYPE, eventType.ordinal)
                }
            )
    }
}