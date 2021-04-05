package com.lek.parrot.newevents.ui

import com.lek.parrot.newevents.domain.ScreenType

interface OnBackListener {
    fun onBack(screenType: ScreenType)
}