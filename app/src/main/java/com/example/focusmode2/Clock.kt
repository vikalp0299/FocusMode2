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

    fun getFocusEndTime(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR, 1)
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}

