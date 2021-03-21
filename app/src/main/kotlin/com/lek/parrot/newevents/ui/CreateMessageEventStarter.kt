package com.lek.parrot.newevents.ui

import android.content.Context

class CreateMessageEventStarter : ICreateMessageEventStarter {
    override fun startMessageEvent(context: Context) {
        CreateEventActivity.start(context)
    }
}