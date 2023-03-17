package com.example.scope106.task1;

import static com.example.scope106.task1.ExampleHandler.TASK_A;
import static com.example.scope106.task1.ExampleHandler.TASK_B;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.scope106.R;
import com.example.scope106.databinding.LayoutBinding;

public class Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private ExampleLooperThread looperThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        looperThread = new ExampleLooperThread(getApplicationContext());
        LayoutBinding binding = LayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.start.setOnClickListener(this);
        binding.stop.setOnClickListener(this);
        binding.taskA.setOnClickListener(this);
        binding.taskB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start :
                looperThread.start();
                break;
            case R.id.stop :
                looperThread.looper.quit();
                break;
            case R.id.taskA : {
                Message msg = Message.obtain();
                msg.what = TASK_A;
                looperThread.handler.sendMessage(msg);
            }
            break;
            case R.id.taskB : {
                Message msg = Message.obtain();
                msg.what = TASK_B;
                looperThread.handler.sendMessage(msg);
            }
            break;

        }
    }
}

