package com.lek.parrot.core

import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.lek.parrot.shared.logError
import java.lang.ref.WeakReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class BasePresenter<View : BaseView> : ViewModel(), LifecycleObserver {
    private var viewRef: WeakReference<View>? = null
    private var viewLifecycleRef: WeakReference<Lifecycle>? = null

    val view by lazy {
        viewRef?.get() ?: throw Error("View is null in ${BasePresenter::class.java.name}")
    }

    @CallSuper
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

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onViewDestroyed() {
        viewRef?.clear()
        viewLifecycleRef?.clear()
    }

    fun Flow<*>.handleError(fallbackMessage: String) = catch { error ->
        logError(error)
        view.showError(error.message ?: fallbackMessage)
    }
}

inline fun <reified T> Any.to(): T {
    if (this !is T) throw ClassCastException("Can't cast Any to ${T::class.java.name}")
    return this
}