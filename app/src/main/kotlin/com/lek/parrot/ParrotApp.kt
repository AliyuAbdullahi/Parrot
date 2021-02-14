package com.lek.parrot

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import java.lang.ref.WeakReference
import timber.log.Timber

@HiltAndroidApp
class ParrotApp : Application() {

    override fun onCreate() {
        super.onCreate()
        contextRef = WeakReference(this)
        plantLoggingTree()
    }

    private fun plantLoggingTree() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        var contextRef: WeakReference<Context>? = null
    }
}