package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.request.target.CustomTarget

class MainActivity4 : AppCompatActivity() {

    lateinit var img:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        img = findViewById(R.id.img2)
        GlideApp.with(this)
            .asBitmap()
            .load(R.drawable.bkg)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap,
                                             transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                    img.setImageBitmap(resource)


                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        val nextPageBtn = findViewById<View>(R.id.button13) as Button
        nextPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity4, MainActivity7::class.java)
            startActivity(intent)
        }
        val nextPageBtn0 = findViewById<View>(R.id.button) as Button
        nextPageBtn0.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity4, MainActivity9Video::class.java)
            startActivity(intent)
        }
        val nextPageBtn1 = findViewById<View>(R.id.button1) as Button
        nextPageBtn1.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity4, MainActivity10_1step::class.java)
            startActivity(intent)
        }
    }
}