package com.petro.scope101.activity;

import static com.petro.scope101.activity.EnterEmailActivity.KEY_EMAIL;
import static com.petro.scope101.activity.EnterPasswordActivity.KEY_PASSWORD;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.petro.scope101.R;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 111;
    private static final String KEY_SHARED_PREFERENCES = "com.trainee.scope101.PREFERENCE_FILE_KEY";
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.button).setOnClickListener(v -> EnterEmailActivity.startForResult(LoginActivity.this, REQUEST_CODE));
        findViewById(R.id.signout).setOnClickListener(v -> {
            sharedPref.edit().clear().apply();
            showLogin();
        });
        sharedPref = getSharedPreferences(KEY_SHARED_PREFERENCES, MODE_PRIVATE);
        if (sharedPref.getString(KEY_EMAIL, null) != null && sharedPref.getString(KEY_PASSWORD, null) != null) {
            showCredentionals(sharedPref.getString(KEY_EMAIL, null), sharedPref.getString(KEY_PASSWORD, null));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            String password = data.getStringExtra(KEY_PASSWORD);
            String email = data.getStringExtra(KEY_EMAIL);

            sharedPref.edit().putString(KEY_EMAIL, email).putString(KEY_PASSWORD, password).apply();
            showCredentionals(email, password);
        }
    }

    private void showCredentionals(String email, String password) {
        this.<TextView>findViewById(R.id.descriprion).setText(R.string.welcome);
        TextView emailyour = findViewById(R.id.emailyour);
        emailyour.setText(email);
        emailyour.setVisibility(View.VISIBLE);
        TextView passwordyour = findViewById(R.id.passwordyour);
        passwordyour.setText(password);
        passwordyour.setVisibility(View.VISIBLE);
        this.<TextView>findViewById(R.id.email).setVisibility(View.VISIBLE);
        this.<TextView>findViewById(R.id.password).setVisibility(View.VISIBLE);
        this.<Button>findViewById(R.id.button).setVisibility(View.GONE);
        this.<Button>findViewById(R.id.signout).setVisibility(View.VISIBLE);
    }

    private void showLogin() {
        this.<TextView>findViewById(R.id.descriprion).setText(R.string.create_account_in_our_superapp);
        findViewById(R.id.emailyour).setVisibility(View.GONE);
        findViewById(R.id.passwordyour).setVisibility(View.GONE);
        this.<TextView>findViewById(R.id.email).setVisibility(View.GONE);
        this.<TextView>findViewById(R.id.password).setVisibility(View.GONE);
        this.<Button>findViewById(R.id.button).setVisibility(View.VISIBLE);
        this.<Button>findViewById(R.id.signout).setVisibility(View.GONE);
    }
}
