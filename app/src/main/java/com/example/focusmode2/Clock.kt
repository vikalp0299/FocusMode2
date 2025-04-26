package com.example.focusmode2

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Clock {
    fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
