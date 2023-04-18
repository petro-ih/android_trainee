package com.example.scope106.task1

import android.os.Bundle
import android.os.Message
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.scope106.R
import com.example.scope106.databinding.LayoutBinding

class Activity : AppCompatActivity(), View.OnClickListener {
    private var looperThread: ExampleLooperThread? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        looperThread = ExampleLooperThread(applicationContext)
        val binding = LayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.start.setOnClickListener(this)
        binding.stop.setOnClickListener(this)
        binding.taskA.setOnClickListener(this)
        binding.taskB.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.start -> looperThread!!.start()
            R.id.stop -> looperThread!!.looper.quit()
            R.id.taskA -> {
                val msg = Message.obtain()
                msg.what = ExampleHandler.TASK_A
                looperThread!!.handler.sendMessage(msg)
            }
            R.id.taskB -> {
                val msg = Message.obtain()
                msg.what = ExampleHandler.TASK_B
                looperThread!!.handler.sendMessage(msg)
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}