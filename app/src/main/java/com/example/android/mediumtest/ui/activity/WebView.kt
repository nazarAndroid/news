package com.example.android.mediumtest.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.mediumtest.R
import kotlinx.android.synthetic.main.activity_web_view.*


class WebView : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)


        webView.settings.javaScriptEnabled = true;
        webView.loadUrl("http://www.google.com");
    }
}