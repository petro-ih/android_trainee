package com.petro.scope102;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class BaseDialog extends DialogFragment {
    private static final int DIALOG_WIDTH = 1000;

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }
        Point screenSize = new Point();
        requireActivity().getWindowManager().getDefaultDisplay().getSize(screenSize);
        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
        if (screenSize.x > DIALOG_WIDTH) {
            lp.width = screenSize.x - screenSize.x / 10;
        } else {
            lp.width = screenSize.x;
        }
        getDialog().getWindow().setAttributes(lp);

        getDialog().setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
