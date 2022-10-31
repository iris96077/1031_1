package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity10_1step : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity10_1_step)
        //bread
        val Btn1 = findViewById<View>(R.id.button1) as Button
        Btn1.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity10_1step, BreadStep1_0::class.java)
            startActivity(intent)
        }
        val Btn2 = findViewById<View>(R.id.button2) as Button
        Btn2.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity10_1step, BreadStep2_0::class.java)
            startActivity(intent)
        }
        val Btn3 = findViewById<View>(R.id.button3) as Button
        Btn3.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity10_1step, BreadStep3_0::class.java)
            startActivity(intent)
        }
    }
}