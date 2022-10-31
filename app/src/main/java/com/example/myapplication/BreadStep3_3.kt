package com.example.myapplication

import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton

class BreadStep3_3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bread_step33)

        val nextPageBtn = findViewById<View>(R.id.button) as Button
        nextPageBtn.setOnClickListener{
            val intent = Intent()
            intent.setClass(this@BreadStep3_3, BreadStep3_4::class.java)
            startActivity(intent)
        }
        val itemBtn = findViewById<View>(R.id.button0) as Button
        itemBtn.setOnClickListener{
            val intent = Intent()
            intent.setClass(this@BreadStep3_3, BreadStep3_0::class.java)
            startActivity(intent)
        }
        //聲音在這裡
        var voice = findViewById<ImageButton>(R.id.imageButton)
        var sp = SoundPool(10, AudioManager.STREAM_SYSTEM, 5)
        var music = sp.load(this, R.raw.healthy1, 1)
        voice.setOnClickListener(View.OnClickListener { sp.play(music, 1f, 1f, 0, 0, 1f) })
    }
}