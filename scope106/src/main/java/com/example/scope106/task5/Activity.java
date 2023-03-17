package com.example.scope106.task5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.scope106.R;
import com.example.scope106.databinding.Layout5Binding;

public class Activity extends AppCompatActivity implements View.OnClickListener {
    Layout5Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Layout5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.editTextInput.setOnClickListener(this);
        binding.button.setOnClickListener(this);
    }

    public void enqueueWork(View v) {
        String input = binding.editTextInput.getText().toString();

        Intent serviceIntent = new Intent(this, ExampleJobIntentService.class);
        serviceIntent.putExtra("inputExtra", input);

        ExampleJobIntentService.enqueueWork(this, serviceIntent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            enqueueWork(v);
        }
    }
}
