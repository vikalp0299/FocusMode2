package com.example.focusmode2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import android.app.ActivityManager

class TimerActivity : AppCompatActivity() {
    private var countdownTimer: CountDownTimer? = null
    private var preTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        // Enable lock task mode
        startLockTask()

        // Disable back press
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(this@TimerActivity, "You cannot leave until the timer runs down.", Toast.LENGTH_SHORT).show()
            }
        })

        val timerTextView: TextView = findViewById(R.id.timerTextView)
        val totalMillis = intent.getLongExtra("TIMER_DURATION", 0L)

        startPreTimer(timerTextView, totalMillis)
    }

    private fun startPreTimer(timerTextView: TextView, totalMillis: Long) {
        val countdownTextView: TextView = findViewById(R.id.countdownTextView)
        timerTextView.text = "Focus mode is starting in"
        timerTextView.visibility = TextView.VISIBLE
        preTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                countdownTextView.text = "$seconds"
            }

            override fun onFinish() {
                timerTextView.visibility = TextView.GONE
                startTimer(totalMillis, countdownTextView)
            }
        }.start()
    }

    private fun startTimer(totalMillis: Long, timerTextView: TextView) {
        countdownTimer = object : CountDownTimer(totalMillis, 1000) {
            @SuppressLint("DefaultLocale")
            override fun onTick(millisUntilFinished: Long) {
                val hours = millisUntilFinished / 3600000
                val minutes = (millisUntilFinished % 3600000) / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                timerTextView.text = String.format("%02d:%02d:%02d", hours.toInt(), minutes.toInt(), seconds.toInt())
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                timerTextView.text = "00:00:00"
                Toast.makeText(this@TimerActivity, "Focus mode ended!", Toast.LENGTH_LONG).show()

                // Disable lock task mode
                stopLockTask()

                val intent = Intent(this@TimerActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countdownTimer?.cancel()
        preTimer?.cancel()

        // Ensure lock task mode is disabled if the activity is destroyed
        stopLockTask()
    }
}
