package com.example.deliverymanagement.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.deliverymanagement.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val handler: Handler = Handler()
        handler.postDelayed(Runnable {
            val mInHome =
                Intent(this@SplashActivity, LoginActivity::class.java)
            this@SplashActivity.startActivity(mInHome)
            finish()
        }, 3000)
    }
}