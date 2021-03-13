package com.lek.parrot.shared

import android.app.TimePickerDialog
import android.content.Context
import java.util.Calendar
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE

object TimePickerDialogStarter {
    fun startTimePicker(context: Context, listener: TimePickerDialog.OnTimeSetListener) =
        with(Calendar.getInstance()) {
            TimePickerDialog(
                context,
                listener,
                get(HOUR_OF_DAY),
                get(MINUTE),
                true
            )
        }.show()
}