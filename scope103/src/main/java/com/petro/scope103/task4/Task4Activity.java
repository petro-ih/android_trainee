package com.petro.scope103.task4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.petro.scope103.R;
import com.petro.scope103.task1.FragmentFirst;
import com.petro.scope103.task1.FragmentSecond;
import com.petro.scope103.task3.Task3Activity;

public class Task4Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4);
        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container1, new Task4Fragment1(), "tag1")
                    .add(R.id.container2, new Task4Fragment2(), "tag2")
                    .add(R.id.container3, new Task4Fragment2(), "tag3")
                    .add(R.id.container4, new Task4Fragment2(), "tag4")
                    .commit();

        }
    }
    public static void start(Context context) {
        Intent starter = new Intent(context, Task4Activity.class);
        context.startActivity(starter);
    }
}
