package com.petro.scope103.task5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.petro.scope103.R;

public class Task5Activity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, Task5Activity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task5);
        if (savedInstanceState == null) {
            // Add the first fragment to the container
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, Task5Fragment.newInstance(1))
                    .commit();
        }
    }
}
