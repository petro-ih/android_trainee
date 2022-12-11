package com.petro.scope102;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class EnterPhoneDialogueFragment extends BaseDialog implements View.OnClickListener {
    public static final String TAG = "Enter Phone";
    private IntentHelper intentHelper;
    private View rootView;
    private EditText numberEditText;
    private EditText messageEditText;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_sms, null);
        intentHelper = new IntentHelper(requireActivity());
        numberEditText = (EditText) rootView.findViewById(R.id.dialog_sms_number);
        rootView.findViewById(R.id.dialog_sms_send_button).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_sms_send_button) {
            requireActivity().startActivity(intentHelper.getPhoneIntent(numberEditText.getText().toString()));
            this.dismiss();
        }
    }
}

