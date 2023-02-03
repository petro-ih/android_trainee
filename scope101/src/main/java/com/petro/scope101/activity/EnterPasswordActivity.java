package com.petro.scope101.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import com.petro.scope101.R;
import com.petro.scope101.util.TextValidator;
import com.petro.scope101.util.TextWatcherAdapter;

public class EnterPasswordActivity extends AppCompatActivity {
    static final String KEY_PASSWORD = "KEY_PASSWORD";
    private EditText password;
    private Button button;
    private TextView lower, symbol, upper, eight, number;
    private GridLayout gridLayout;

    public static void startForResultExplicit(Activity context, int requestCode) {
        Intent starter = new Intent(context, EnterPasswordActivity.class);
        context.startActivityForResult(starter, requestCode);
    }

    public static void startForResultImplicit(Activity context, int requestCode) {
        Intent intent = new Intent(context.getString(R.string.action_enter_password));
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);
        password = findViewById(R.id.passwordEditText);
        button = findViewById(R.id.button);
        lower = findViewById(R.id.lowercase);
        symbol = findViewById(R.id.symbol);
        upper = findViewById(R.id.uppercase);
        eight = findViewById(R.id.eight);
        number = findViewById(R.id.number);
        gridLayout = findViewById(R.id.checkLayout);
        password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE && button.isEnabled()) {
                button.performClick();
            }
            return false;
        });
        password.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                verifyPassword(s.toString());
            }
        });

        verifyPassword(password.getText().toString());
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        button.setOnClickListener(v -> {
            Intent intent1 = new Intent();
            intent1.putExtra(KEY_PASSWORD, password.getText().toString());
            setResult(RESULT_OK, intent1);
            finish();
        });
    }

    private void verifyPassword(String password) {
        boolean isValid = TextValidator.isValidPassword(password);
        button.setEnabled(isValid);
        lower.setEnabled(TextValidator.hasLowercase(password));
        symbol.setEnabled(TextValidator.hasSymbol(password));
        eight.setEnabled(TextValidator.hasEightCharacters(password));
        number.setEnabled(TextValidator.hasNumber(password));
        upper.setEnabled(TextValidator.hasUppercase(password));
        gridLayout.setVisibility(isValid? View.GONE: View.VISIBLE);
    }
}
