package com.smolentsev.pixpicture.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.smolentsev.pixpicture.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed( {
            val mainActivity = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }, 1000)
    }
}