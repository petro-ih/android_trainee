package com.petro.scope103.task1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.petro.scope103.R;
import com.petro.scope103.task3.Task3Activity;
import com.petro.scope103.task4.Task4Activity;
import com.petro.scope103.task5.Task5Activity;
import com.petro.scope103.task6.Task6Activity;

public class MyActivity extends AppCompatActivity implements FragmentFirst.OnSendTextListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        findViewById(R.id.btnTask2).setOnClickListener(v -> {
            com.petro.scope103.task2.MyActivity.start(this);
        });
        findViewById(R.id.btnTask3).setOnClickListener(v -> {
            Task3Activity.start(this);
        });
        findViewById(R.id.btnTask4).setOnClickListener(v -> {
            Task4Activity.start(this);
        });
        findViewById(R.id.btnTask5).setOnClickListener(v -> {
            Task5Activity.start(this);
        });
        findViewById(R.id.btnTask6).setOnClickListener(v -> {
            Task6Activity.start(this);
        });
    }

    @Override
    public void sendText(String text) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment2);
        if (fragment instanceof FragmentSecond) {
            ((FragmentSecond) fragment).setText(text);
        }
    }
}
