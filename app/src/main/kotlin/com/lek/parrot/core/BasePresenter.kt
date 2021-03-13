package com.lek.parrot.core

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

abstract class BasePresenter<View> : ViewModel(), LifecycleObserver {
    private var viewRef: WeakReference<View>? = null
    private var viewLifecycleRef: WeakReference<Lifecycle>? = null

    val view by lazy { viewRef?.get() ?: throw Error("View is null in ${BasePresenter::class.java.name}") }

    fun attachToView(view: View, host: AppCompatActivity) {
        this.viewRef = WeakReference(view)
        this.viewLifecycleRef = WeakReference(host.lifecycle)
        viewLifecycleRef?.get()?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onViewDestroyed() {
        viewRef?.clear()
        viewLifecycleRef?.clear()
    }
}