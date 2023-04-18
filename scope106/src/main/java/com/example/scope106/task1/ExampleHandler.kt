package com.example.scope106.task1

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast

class ExampleHandler(private val context: Context) : Handler() {
    private val uiThreadHandler = Handler(Looper.getMainLooper())
    override fun handleMessage(msg: Message) {
        when (msg.what) {
            TASK_A -> {
                toast("Executing Task A started")
                try {
                    Thread.sleep(5000)
                } catch (e: InterruptedException) {
                    throw RuntimeException(e)
                }
                toast("Executing Task A completed")
            }
            TASK_B -> {
                toast("Executing Task B started")
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    throw RuntimeException(e)
                }
                toast("Executing Task B completed")
            }
        }
    }

    private fun toast(message: String) {
        uiThreadHandler.post { Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
    }

    companion object {
        private const val TAG = "ExampleHandler"
        const val TASK_A = 1
        const val TASK_B = 2
    }
}