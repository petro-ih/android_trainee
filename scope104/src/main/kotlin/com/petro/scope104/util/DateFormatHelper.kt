package com.petro.scope104.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateFormatHelper {
    fun formatDob(date: Date?): String {
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val yearsCount =
            (TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - date!!.time) / 365).toInt()
        return String.format(
            Locale.getDefault(),
            "%s, %d years",
            formatter.format(date),
            yearsCount
        )
    }
}