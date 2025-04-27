package com.example.focusmode2

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
            val hours = hoursInput.text.toString().toIntOrNull() ?: 0
            val minutes = minutesInput.text.toString().toIntOrNull() ?: 0
            val seconds = secondsInput.text.toString().toIntOrNull() ?: 0

            if (hours > 11 || minutes > 59 || seconds > 59) {
                if (hours > 11){
                    Toast.makeText(this,"Invalid input. Hours must be less than 11.", Toast.LENGTH_LONG).show()
                }else if (minutes > 59){
                    Toast.makeText(this,"Invalid input. Minutes must be less than 60.", Toast.LENGTH_LONG).show()
                }else{
                Toast.makeText(this, "Invalid input. Seconds must be less than 60.", Toast.LENGTH_LONG).show()
                }
            } else {
                val focusEndTime = clock.getFocusEndTime(hours, minutes, seconds)
                Toast.makeText(this, "The phone will be in focus mode till $focusEndTime", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateTime(textView: TextView) {
        textView.text = clock.getCurrentTime()
        handler.postDelayed({ updateTime(textView) }, 1000)
    }
}
