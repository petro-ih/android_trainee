package com.petro.scope101.activity;

import static com.petro.scope101.activity.EnterPasswordActivity.KEY_PASSWORD;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.petro.scope101.R;
import com.petro.scope101.util.TextValidator;
import com.petro.scope101.util.TextWatcherAdapter;

import java.util.Random;

public class EnterEmailActivity extends AppCompatActivity {
    static final String KEY_EMAIL = "KEY_EMAIL";
    private static final int PASSWORD_REQUEST_CODE = 222;
    private EditText email;
    private Button button;

    public static void startForResult(Activity context, int requestCode) {
        Intent starter = new Intent(context, EnterEmailActivity.class);
        context.startActivityForResult(starter, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_email);
        email = findViewById(R.id.email);
        button = findViewById(R.id.button);
        email.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE && button.isEnabled()) {
                button.performClick();
            }
            return false;
        });
        email.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                button.setEnabled(TextValidator.isValidEmail(s.toString()));
            }
        });
        button.setEnabled(TextValidator.isValidEmail(email.getText().toString()));
        button.setOnClickListener(v -> {
            if (new Random().nextBoolean()) {
                EnterPasswordActivity.startForResultExplicit(EnterEmailActivity.this, PASSWORD_REQUEST_CODE);
            } else {
                EnterPasswordActivity.startForResultImplicit(EnterEmailActivity.this, PASSWORD_REQUEST_CODE);
            }
        });
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PASSWORD_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            String password = data.getStringExtra(KEY_PASSWORD);
            Intent intent1 = new Intent();
            intent1.putExtra(KEY_PASSWORD, password);
            intent1.putExtra(KEY_EMAIL, email.getText().toString());
            setResult(RESULT_OK, intent1);
            finish();
        }
    }
}