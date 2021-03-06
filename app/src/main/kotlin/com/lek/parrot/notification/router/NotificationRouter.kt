package com.lek.parrot.notification.router

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lek.parrot.events.ui.UpcomingEventsActivity
import com.lek.parrot.notification.NOTIFICATION_TARGET

class NotificationRouter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        intent?.extras?.let {
            if (it.containsKey(NOTIFICATION_TARGET)) {
                if (it[NOTIFICATION_TARGET] == "MainActivity") {
                    startActivity(Intent(this, UpcomingEventsActivity::class.java))
                }
            }
        } ?: super.onNewIntent(intent)
    }
}