package com.lek.parrot.events.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lek.parrot.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingEventsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
    }
}