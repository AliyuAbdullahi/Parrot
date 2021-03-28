package com.lek.parrot.newevents.ui

import android.content.Context

class CreateMessageEventStarter : ICreateEventStarter {
    override fun startMessageEvent(context: Context) {
        CreateEventActivity.start(context, CreateEventType.MESSAGE)
    }

    override fun startCallEvent(context: Context) {
        CreateEventActivity.start(context, CreateEventType.CALL)
    }
}