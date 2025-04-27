package com.example.focusmode2

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.CountDownTimer
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent

class MainActivity : AppCompatActivity() {
    private val clock = Clock()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val currentTimeTextView: TextView = findViewById(R.id.currentTimeTextView)
        updateTime(currentTimeTextView)

        val hoursInput: EditText = findViewById(R.id.hoursInput)
        val minutesInput: EditText = findViewById(R.id.minutesInput)
        val secondsInput: EditText = findViewById(R.id.secondsInput)
        val startFocusButton: Button = findViewById(R.id.startFocusButton)

        startFocusButton.setOnClickListener {
            val hoursText = hoursInput.text.toString()
            val minutesText = minutesInput.text.toString()
            val secondsText = secondsInput.text.toString()

            val hours = if (hoursText.isEmpty()) 0 else hoursText.toIntOrNull() ?: -1
            val minutes = if (minutesText.isEmpty()) 0 else minutesText.toIntOrNull() ?: -1
            val seconds = if (secondsText.isEmpty()) 0 else secondsText.toIntOrNull() ?: -1

            val errors = mutableListOf<String>()

            if (hoursText.contains(".") || minutesText.contains(".") || secondsText.contains(".")) {
                errors.add("Inputs must be whole numbers.")
            }
            if (hours < 0 || minutes < 0 || seconds < 0) {
                errors.add("Inputs must be non-negative.")
            }
            if (hours > 11) {
                errors.add("Hours must be less than 11.")
            }
            if (minutes > 59) {
                errors.add("Minutes must be less than 60.")
            }
            if (seconds > 59) {
                errors.add("Seconds must be less than 60.")
            }

            if (errors.isNotEmpty()) {
                Toast.makeText(this, errors.joinToString("\n"), Toast.LENGTH_LONG).show()
            } else {
                val totalMillis = (hours * 3600 + minutes * 60 + seconds) * 1000L
                val intent = Intent(this, TimerActivity::class.java)
                intent.putExtra("TIMER_DURATION", totalMillis)
                startActivity(intent)
            }
        }
    }

    private fun updateTime(textView: TextView) {
        textView.text = clock.getCurrentTime()
        handler.postDelayed({ updateTime(textView) }, 1000)
    }
}
