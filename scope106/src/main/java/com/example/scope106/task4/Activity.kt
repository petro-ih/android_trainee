package com.example.scope106.task4

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.scope106.R
import com.example.scope106.databinding.Layout4Binding

class Activity : AppCompatActivity(), View.OnClickListener {
    var binding: Layout4Binding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Layout4Binding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.editTextInput.setOnClickListener(this)
        binding!!.button.setOnClickListener(this)
    }

    fun startService(v: View?) {
        val input = binding!!.editTextInput.text.toString()
        val serviceIntent = Intent(this, ExampleIntentService::class.java)
        serviceIntent.putExtra("inputExtra", input)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button) {
            startService(v)
        }
    }
}