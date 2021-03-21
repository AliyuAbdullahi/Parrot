package com.lek.parrot

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ParrotApp : Application() {

    override fun onCreate() {
        super.onCreate()
        plantLoggingTree()
    }

    private fun plantLoggingTree() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}