package com.example.home.screen.plan

import android.util.Log
import android.webkit.JavascriptInterface

class ArchilogicInterface(private val callback: (Any?) -> Unit) {
    @JavascriptInterface
    fun onCallback(data: Any) {
        // Handle the callback data
        Log.d("a", "onCallback: $data")
        callback.invoke(data)
    }
}