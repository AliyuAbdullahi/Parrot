package com.lek.parrot.newevents.ui.createevent

import com.lek.parrot.core.BasePresenter

class CreateEventPresenter : BasePresenter<CreateEventContract.View>(), CreateEventContract.Presenter {

    override fun onShowMessageEvent() {
        view.onShowMessageEvent()
    }

    override fun onShowCallEvent() {
        view.onShowCallEvent()
    }
}