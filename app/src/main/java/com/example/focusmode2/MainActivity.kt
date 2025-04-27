package com.example.focusmode2

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
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

        val startFocusButton: Button = findViewById(R.id.startFocusButton)
        startFocusButton.setOnClickListener {
            val focusEndTime = clock.getFocusEndTime()
            Toast.makeText(this, "The phone will be in focus mode till $focusEndTime", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateTime(textView: TextView) {
        textView.text = clock.getCurrentTime()
        handler.postDelayed({ updateTime(textView) }, 1000)
    }
}

