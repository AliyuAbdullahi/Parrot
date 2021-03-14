package com.lek.parrot.shared

import android.app.DatePickerDialog
import android.content.Context
import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH
import java.util.Calendar.YEAR

object DatePickerDialogStarter {

    fun startDatePicker(context: Context, listener: DatePickerDialog.OnDateSetListener) =
        with(Calendar.getInstance()) {
            DatePickerDialog(
                context,
                listener,
                get(YEAR),
                get(MONTH),
                get(DAY_OF_MONTH)
            )
        }.show()
}