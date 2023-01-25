package com.petro.scope103.task3;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.petro.scope103.R;

public class Task3Fragment extends Fragment {
    private static final String KEY_COLOR = "color";
    private int bgColor = 0;

    public static Task3Fragment newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt("index", index);
        Task3Fragment fragment = new Task3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void changeBackground(int color) {
        bgColor = color;
        if (getView() != null) {
            getView().setBackground(new ColorDrawable(color));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task3_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.task3TextView);
        textView.setText(String.valueOf(getArguments().getInt("index") + 1));

        if (savedInstanceState != null && bgColor == 0) {
            bgColor = savedInstanceState.getInt(KEY_COLOR);
        }

        changeBackground(bgColor);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COLOR, bgColor);
    }
}
