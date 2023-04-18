package com.example.scope106.task2

import android.os.Bundle
import android.os.Message
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.scope106.R
import com.example.scope106.databinding.Layout2Binding

class Activity : AppCompatActivity(), View.OnClickListener {
    private val handlerThread = ExampleHandlerThread()
    private val runnable1 = ExampleRunnable1()
    private val token = Any()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = Layout2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.doWorkBttn.setOnClickListener(this)
        binding.removeBttn.setOnClickListener(this)
        handlerThread.start()
    }

    private fun doWork(view: View?) {
        val msg = Message.obtain(handlerThread.handler)
        msg.what = ExampleHandlerThread.Companion.EXAMPLE_TASK
        msg.arg1 = 23
        msg.obj = "Obj String"
        //msg.setData();
        msg.sendToTarget()
        //handlerThread.getHandler().sendEmptyMessage(1);
        handlerThread.handler?.postAtTime(runnable1, token, SystemClock.uptimeMillis())
        handlerThread.handler?.post(runnable1)
        //handlerThread.getHandler().post(new ExampleRunnable1());
        //handlerThread.getHandler().postAtFrontOfQueue(new ExampleRunnable2());
    }

    private fun removeMessages(view: View?) {
        handlerThread.handler?.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        handlerThread.quit()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.doWorkBttn -> doWork(v)
            R.id.removeBttn -> removeMessages(v)
        }
    }

    internal class ExampleRunnable1 : Runnable {
        override fun run() {
            for (i in 0..3) {
                Log.d(TAG, "Runnable1: $i")
                SystemClock.sleep(1000)
            }
        }
    }

    internal class ExampleRunnable2 : Runnable {
        override fun run() {
            for (i in 0..3) {
                Log.d(TAG, "Runnable2: $i")
                SystemClock.sleep(1000)
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}