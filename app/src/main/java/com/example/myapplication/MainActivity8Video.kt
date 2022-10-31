package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity8Video : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity8_video)

        val nextPageBtn0 = findViewById<View>(R.id.button1) as Button
        nextPageBtn0.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity8Video, MainActivityVideo32::class.java)
            startActivity(intent)
        }

    }
}