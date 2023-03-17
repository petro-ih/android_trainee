package com.example.scope106.task4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.scope106.R;
import com.example.scope106.databinding.Layout4Binding;

public class Activity extends AppCompatActivity implements View.OnClickListener {
    Layout4Binding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Layout4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.editTextInput.setOnClickListener(this);
        binding.button.setOnClickListener(this);
    }

    public void startService(View v) {
        String input = binding.editTextInput.getText().toString();

        Intent serviceIntent = new Intent(this, ExampleIntentService.class);
        serviceIntent.putExtra("inputExtra", input);

        ContextCompat.startForegroundService(this, serviceIntent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            startService(v);
        }
    }
}
