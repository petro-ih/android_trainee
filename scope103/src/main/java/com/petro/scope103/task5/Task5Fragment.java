package com.petro.scope103.task5;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.petro.scope103.ColorHelper;
import com.petro.scope103.R;

public class Task5Fragment extends Fragment {
    private static final String KEY_NESTING_LEVEL = "KEY_NESTING_LEVEL";
    private int fibonacciNum = 1;
    private int currentNum = 1;

    public static Task5Fragment newInstance(int number) {
        Bundle args = new Bundle();
        args.putInt(KEY_NESTING_LEVEL, number);
        Task5Fragment fragment = new Task5Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setNumber(int number) {
        TextView tv = getView().findViewById(R.id.text);
        tv.setText(String.valueOf(number));
        Task5Fragment f = ((Task5Fragment) getParentFragment());
        if (f != null) {
            f.setNumber(currentNum);
        }
        this.currentNum = number;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task5_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackground(new ColorDrawable(ColorHelper.generateColor()));
        int nestingLevel = getArguments().getInt(KEY_NESTING_LEVEL);
        if (savedInstanceState == null && nestingLevel < 6) {
            getChildFragmentManager().beginTransaction().add(R.id.nested_container, newInstance(nestingLevel + 1)).commit();
        }
        Button button = view.findViewById(R.id.button);
        button.setVisibility(nestingLevel == 6 ? View.VISIBLE : View.GONE);
        button.setOnClickListener(view1 -> {
            int f = fibonacci(fibonacciNum++);
            setNumber(f);
        });
    }

    private int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}

