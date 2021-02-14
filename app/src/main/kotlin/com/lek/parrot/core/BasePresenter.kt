package com.lek.parrot.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

abstract class BasePresenter<View> : ViewModel(), LifecycleObserver {
    private var viewRef: WeakReference<View>? = null
    private var viewLifecycleRef: WeakReference<Lifecycle>? = null

    fun attachToView(view: View, lifecycle: Lifecycle) {
        this.viewRef = WeakReference(view)
        this.viewLifecycleRef = WeakReference(lifecycle)
        viewLifecycleRef?.get()?.addObserver(this)
    }

    fun view(): View? = viewRef?.get()

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