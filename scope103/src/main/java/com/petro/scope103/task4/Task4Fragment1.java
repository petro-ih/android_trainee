package com.petro.scope103.task4;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.petro.scope103.ColorHelper;
import com.petro.scope103.R;

public class Task4Fragment1 extends Fragment {
    public static final String ACTION_SET_COLOR = "ACTION_SET_COLOR";
    public static final String KEY_COLOR = "KEY_COLOR";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task4_fragment1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.generateColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(ACTION_SET_COLOR);
                intent.putExtra(KEY_COLOR, ColorHelper.generateColor());
                requireContext().sendBroadcast(intent);
            }
        });
    }
}
