package com.lek.parrot.shared

import android.content.Context

class StringService(private val context: Context) : IStringService {
    override fun getString(res: Int): String = context.getString(res)
}