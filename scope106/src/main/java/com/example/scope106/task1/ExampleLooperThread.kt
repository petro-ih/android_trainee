package com.example.scope106.task1

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log

class ExampleLooperThread(private val context: Context) : Thread() {
    var looper: Looper? = null
    var handler: Handler? = null
    override fun run() {
        Looper.prepare()
        looper = Looper.myLooper()
        handler = ExampleHandler(context)
        Looper.loop()
        Log.d(TAG, "End of run()")
    }

    companion object {
        private const val TAG = "ExampleLooperThread"
    }
}