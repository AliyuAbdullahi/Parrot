package com.lek.parrot.shared

import androidx.annotation.StringRes

interface IStringService {
    fun getString(@StringRes res: Int): String
}