package com.lek.parrot.shared

import timber.log.Timber

fun logError(error: Throwable) {
    Timber.e(error)
}

fun logDebug(message: String) {
    Timber.d(message)
}

fun logWarn(message: String) {
    Timber.w(message)
}