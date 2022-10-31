package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity9Video : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity9_video)

        val nextPageBtn0 = findViewById<View>(R.id.button1) as Button
        nextPageBtn0.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity9Video, MainActivityVideo4::class.java)
            startActivity(intent)
        }
        val nextPageBtn1 = findViewById<View>(R.id.button) as Button
        nextPageBtn1.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity9Video, MainActivityVideo41::class.java)
            startActivity(intent)
        }
        val nextPageBtn2 = findViewById<View>(R.id.button13) as Button
        nextPageBtn2.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity9Video, MainActivityVideo42::class.java)
            startActivity(intent)
        }
    }
}