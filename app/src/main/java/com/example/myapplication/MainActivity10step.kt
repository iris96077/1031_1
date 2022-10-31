package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity10step : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity10step)
        //bread
        val Btn1 = findViewById<View>(R.id.button1) as Button
        Btn1.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity10step, CookieStep1_0::class.java)
            startActivity(intent)
        }

    }
}