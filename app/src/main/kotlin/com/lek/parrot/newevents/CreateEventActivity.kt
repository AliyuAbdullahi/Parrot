package com.lek.parrot.newevents

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.work.Data
import com.lek.parrot.R
import com.lek.parrot.newevents.domain.Event
import com.lek.parrot.newevents.domain.IEventRepository
import com.lek.parrot.notification.NOTIFICATION_ID
import com.lek.parrot.notification.NOTIFICATION_MESSAGE
import com.lek.parrot.notification.NOTIFICATION_TARGET
import com.lek.parrot.notification.NOTIFICATION_TITLE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CreateEventActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: IEventRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            repository.createEvent(
                Event.ReminderEvent(
                    "Call me back please",
                    System.currentTimeMillis(),
                    System.currentTimeMillis(),
                    false
                )
            )

            repository.getEvents()
                .catch { exception -> Timber.e("ERROR - ${exception.message}") }
                .collectLatest {
                    Toast.makeText(
                        this@CreateEventActivity,
                        "Events created size ${it.size}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
        val data = Data.Builder().putInt(NOTIFICATION_ID, 0)
            .putString(NOTIFICATION_TITLE, "Call Dad")
            .putString(NOTIFICATION_MESSAGE, "Call your father now")
            .putString(NOTIFICATION_TARGET, "Target")
            .build()
//        NotificationScheduler.scheduleNotification(applicationContext, data)
    }
}