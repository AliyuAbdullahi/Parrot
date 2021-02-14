package com.lek.parrot.notification

import android.content.Context
import androidx.work.ListenableWorker.Result.success
import androidx.work.Worker
import androidx.work.WorkerParameters

const val NOTIFICATION_WORK = "appName_notification_work"
const val NOTIFICATION_TITLE = "Parrot_notification_title"
const val NOTIFICATION_MESSAGE = "Parrot_notification_message"
class NotificationWorker(context: Context, workParams: WorkerParameters) : Worker(context, workParams) {

    override fun doWork(): Result {
        val id = inputData.getLong(NOTIFICATION_ID, 0).toInt()
        val title = inputData.getString(NOTIFICATION_TITLE) ?: ""
        val message = inputData.getString(NOTIFICATION_MESSAGE) ?: ""
        Notifier.sendNotification(applicationContext, id, title, message)

        return success()
    }
}