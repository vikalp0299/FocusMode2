package com.example.focusmode2

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Calendar

class Clock {
    fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun getFocusEndTime(hours: Int, minutes: Int, seconds: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR, hours)
        calendar.add(Calendar.MINUTE, minutes)
        calendar.add(Calendar.SECOND, seconds)

        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}
