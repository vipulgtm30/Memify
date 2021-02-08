package com.example.Memify

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.HandlerCompat.postDelayed
import com.example.sharememes.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val textview = findViewById<TextView>(R.id.textView)

        val typeface = ResourcesCompat.getFont(
                this,
                R.font.alexandra
        )
        textview.setTypeface(typeface)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) //this 3 millisecond time

    }
}