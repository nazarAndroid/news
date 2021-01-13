package com.example.android.mediumtest.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.android.mediumtest.R
import com.example.android.mediumtest.ui.activity.MainActivity
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Handler(Looper.getMainLooper()).postDelayed({
            animation.cancelAnimation()
            var intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
}