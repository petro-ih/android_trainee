package com.petro.scope103.task1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.petro.scope103.R;

public class MyActivitySecond extends AppCompatActivity implements FragmentFirst.OnSendTextListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my1);
        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container1, new FragmentFirst(), "tag1")
                    .add(R.id.container2, new FragmentSecond(), "tag2")
                    .commit();

        }
    }

    @Override
    public void sendText(String text) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("tag2");
        if (fragment instanceof FragmentSecond) {
            ((FragmentSecond) fragment).setText(text);
        }
    }

}
