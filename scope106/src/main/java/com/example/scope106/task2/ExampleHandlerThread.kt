package com.example.scope106.task2

import android.annotation.SuppressLint
import android.os.*
import android.util.Log

class ExampleHandlerThread :
    HandlerThread("ExampleHandlerThread", Process.THREAD_PRIORITY_BACKGROUND) {
    var handler: Handler? = null
        private set

    @SuppressLint("HandlerLeak")
    override fun onLooperPrepared() {
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    EXAMPLE_TASK -> {
                        Log.d(TAG, "Example Task, arg1: " + msg.arg1 + ", obj: " + msg.obj)
                        var i = 0
                        while (i < 4) {
                            Log.d(TAG, "handleMessage: $i")
                            SystemClock.sleep(1000)
                            i++
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "ExampleHandlerThread"
        const val EXAMPLE_TASK = 1
    }
}