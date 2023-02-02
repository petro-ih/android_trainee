package com.petro.scope104.ui.list;

import android.app.ActivityOptions;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.petro.scope104.databinding.ActivityScope104Binding;
import com.petro.scope104.ui.details.UserDetailsActivity;
import com.petro.scope104.ui.WorkerUi;

public class Activity extends AppCompatActivity implements WorkerListFragment.WorkerListInteractions {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.petro.scope104.databinding.ActivityScope104Binding binding = ActivityScope104Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewPager viewPager = binding.pager;
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onItemClick(WorkerUi workerUi, ActivityOptions activityOptions) {
        UserDetailsActivity.start(this, workerUi, activityOptions);
    }
}

