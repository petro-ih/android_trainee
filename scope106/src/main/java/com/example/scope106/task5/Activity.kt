package com.example.scope106.task5

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.scope106.R
import com.example.scope106.databinding.Layout5Binding

class Activity : AppCompatActivity(), View.OnClickListener {
    var binding: Layout5Binding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Layout5Binding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.editTextInput.setOnClickListener(this)
        binding!!.button.setOnClickListener(this)
    }

    fun enqueueWork(v: View?) {
        val input = binding!!.editTextInput.text.toString()
        val serviceIntent = Intent(this, ExampleJobIntentService::class.java)
        serviceIntent.putExtra("inputExtra", input)
        ExampleJobIntentService.Companion.enqueueWork(this, serviceIntent)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button) {
            enqueueWork(v)
        }
    }
}