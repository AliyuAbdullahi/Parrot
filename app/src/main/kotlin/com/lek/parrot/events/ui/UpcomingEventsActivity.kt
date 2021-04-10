package com.lek.parrot.events.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.lek.parrot.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingEventsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLifecycle = lifecycle
        setContentView(R.layout.activity_upcoming_events)
    }

    companion object {
        var activityLifecycle: Lifecycle? = null
    }
}