package com.petro.scope103.task6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.petro.scope103.R;

public class Task6Activity extends AppCompatActivity {
public static void start(Context context) {
    Intent starter = new Intent(context, Task6Activity.class);
    context.startActivity(starter);
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task6);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, Task6Fragment.newInstance())
                    .commit();
        }
    }
}