package com.petro.scope104;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.petro.scope104.databinding.ActivityUserDetailsBinding;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class UserDetailsActivity extends AppCompatActivity {
    private static final String KEY_WORKER = "KEY_WORKER";
    private ActivityUserDetailsBinding binding;

    public static void start(Context context, WorkerUi workerUi) {
        Intent starter = new Intent(context, UserDetailsActivity.class);
        starter.putExtra(KEY_WORKER, workerUi);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SimpleDateFormat formater = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        binding = ActivityUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WorkerUi workerUi = ((WorkerUi) getIntent().getSerializableExtra(KEY_WORKER));

        Glide
                .with(this)
                .load(workerUi.getAvatarUrlXXL())
                .circleCrop()
                .into(binding.profileImage);
        binding.name.setText(workerUi.getName());
        binding.phoneNumber.setText(workerUi.getPhone());
        binding.username1.setText(workerUi.getUsername());
        binding.email1.setText(workerUi.getEmail());
        String dateText = String.format(Locale.getDefault(),"%s, %d years", formater.format(workerUi.getDob()), workerUi.getAge());
        binding.dob1.setText(dateText);
        binding.nationality1.setText(workerUi.getNat());
        binding.location1.setText(workerUi.getCity());
        String dateText1 = formater.format(workerUi.getRegistered());
        binding.registered.setText(dateText1);
        binding.btnMail.ivIcon.setImageResource(R.drawable.ic_mail);
        binding.btnMail.tvText.setText(R.string.email);
        binding.btnMap.ivIcon.setImageResource(R.drawable.ic_map);
        binding.btnMap.tvText.setText(R.string.map);

        IntentHelper intentHelper = new IntentHelper(this);
        binding.btnPhone.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentHelper.getPhoneIntent(workerUi.getPhone()));
            }
        });
        binding.btnMail.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentHelper.getEmail(workerUi.getEmail(), null, null, null));
            }
        });

    }

    @Override
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
