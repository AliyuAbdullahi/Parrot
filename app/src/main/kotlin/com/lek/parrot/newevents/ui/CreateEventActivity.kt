package com.lek.parrot.newevents.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import com.lek.parrot.R
import com.lek.parrot.newevents.domain.IEventRepository
import com.lek.parrot.notification.NOTIFICATION_ID
import com.lek.parrot.notification.NOTIFICATION_MESSAGE
import com.lek.parrot.notification.NOTIFICATION_TARGET
import com.lek.parrot.notification.NOTIFICATION_TITLE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateEventActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: IEventRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = Data.Builder().putInt(NOTIFICATION_ID, 0)
            .putString(NOTIFICATION_TITLE, "Call Dad")
            .putString(NOTIFICATION_MESSAGE, "Call your father now")
            .putString(NOTIFICATION_TARGET, "Target")
            .build()
//        NotificationScheduler.scheduleNotification(applicationContext, data)
    }
}