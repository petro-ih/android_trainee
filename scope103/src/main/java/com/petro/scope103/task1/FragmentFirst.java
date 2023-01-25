package com.petro.scope103.task1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.petro.scope103.R;

public class FragmentFirst extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public interface OnSendTextListener{
        void sendText(String text);
    }
    OnSendTextListener onSendTextListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnSendTextListener){
            onSendTextListener = (OnSendTextListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.Button).setOnClickListener(v -> {
            EditText editText = view.findViewById(R.id.EditText);
            String text = editText.getText().toString();
            onSendTextListener.sendText(text);
        });
    }
}
