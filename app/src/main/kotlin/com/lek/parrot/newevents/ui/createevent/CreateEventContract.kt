package com.lek.parrot.newevents.ui.createevent

import com.lek.parrot.core.BaseView

interface CreateEventContract {
    interface View : BaseView {
        fun showMessageEvent()
        fun showCallEvent()
        fun onShowMessageEvent()
        fun onShowCallEvent()
    }

    interface Presenter {
        fun onShowMessageEvent()
        fun onShowCallEvent()
    }
}