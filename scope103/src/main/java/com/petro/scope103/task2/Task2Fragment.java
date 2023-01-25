package com.petro.scope103.task2;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.petro.scope103.R;

import java.util.Random;

public class Task2Fragment extends Fragment {
    OnReplaceScreenListener onReplaceScreenListener;
    int color;

    public static Task2Fragment newInstance(int index) {
        Task2Fragment f = new Task2Fragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnReplaceScreenListener){
            onReplaceScreenListener = (OnReplaceScreenListener) context;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("color", color);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task2_fragment_1, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            Random rnd = new Random();
            color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        } else {
            color = savedInstanceState.getInt("color");
        }
        view.setBackgroundColor(color);

        Button button = view.findViewById(R.id.knopka);
        TextView textView = view.findViewById(R.id.textView);
        Bundle args = getArguments();
        int index = args.getInt("index", 0);
        textView.setText(String.valueOf(index));
        button.setVisibility(index >= 4 ? View.GONE : View.VISIBLE);
        button.setOnClickListener(v -> {
            onReplaceScreenListener.replace();
        });
    }
}
