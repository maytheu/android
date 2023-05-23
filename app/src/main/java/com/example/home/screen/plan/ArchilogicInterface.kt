package com.example.home.screen.plan

import android.webkit.JavascriptInterface

class ArchilogicInterface(private val callback: () -> Unit) {
    @JavascriptInterface
    fun onCallback(data: String) {
        // Handle the callback data
        callback.invoke()
    }
}