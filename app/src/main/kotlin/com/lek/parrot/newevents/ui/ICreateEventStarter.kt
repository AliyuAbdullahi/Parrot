package com.lek.parrot.newevents.ui

import android.content.Context

interface ICreateEventStarter {
    fun startMessageEvent(context: Context)
    fun startCallEvent(context: Context)
}