package com.petro.scope104;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class Activity extends AppCompatActivity implements WorkerListFragment.WorkerListInteractions {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scope104);
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));

    }

    @Override
    public void onItemClick(WorkerUi workerUi) {
        UserDetailsActivity.start(this, workerUi);
    }
}
