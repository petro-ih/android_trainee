package com.petro.scope103.task6;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.petro.scope103.R;

public class Task6Fragment extends Fragment {

    public static Task6Fragment newInstance() {
        Task6Fragment fragment = new Task6Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    int petro = 888;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        setRetainInstance(true);
        super.onCreate(savedInstanceState);
        Log.d("Task6Fragment", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Task6Fragment", "onCreateView");
        return inflater.inflate(R.layout.task6_fragment, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("Task6Fragment", "onAttach");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Task6Fragment", "onViewCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Task6Fragment", "onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Task6Fragment", "onResume");

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Task6Fragment", "onSaveInstanceState");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Task6Fragment", "onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Task6Fragment", "onStop");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Task6Fragment", "onDestroyView");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Task6Fragment", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Task6Fragment", "onDetach");
    }
}