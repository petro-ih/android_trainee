package com.petro.scope103.task3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.petro.scope103.ColorHelper;
import com.petro.scope103.R;

import java.util.Random;

public class Task3Activity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, Task3Activity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new DemoCollectionPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        Button button = findViewById(R.id.task3Button);
        button.setOnClickListener(v -> {
            for (int a = 1; a < viewPager.getAdapter().getCount(); a += 2) {
                int color = ColorHelper.generateColor();

                Task3Fragment f = (Task3Fragment) viewPager.getAdapter().instantiateItem(viewPager, a);
                if (f != null) {
                    f.changeBackground(color);
                }
            }
        });
    }
}
