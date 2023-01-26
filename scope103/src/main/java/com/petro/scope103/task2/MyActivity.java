package com.petro.scope103.task2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.petro.scope103.R;

public class MyActivity extends AppCompatActivity implements OnReplaceScreenListener {

    int counter = 0;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, Task2Fragment.newInstance(counter++), "tag1").commit();

        }
    }

    @Override
    public void replace() {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        )
        .replace(R.id.container, Task2Fragment.newInstance(counter), "tag2");
        if (counter != 2) {
            t.addToBackStack(null);
        }
        t.commit();
        counter++;
    }
}
