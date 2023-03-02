package com.petro.scope104.presentation.details;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.petro.scope104.util.IntentHelper;
import com.petro.scope104.R;
import com.petro.scope104.databinding.ActivityUserDetailsBinding;
import com.petro.scope104.presentation.WorkerUi;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class UserDetailsActivity extends AppCompatActivity {
    private static final String KEY_WORKER = "KEY_WORKER";
    private ActivityUserDetailsBinding binding;

    public static void start(Context context, WorkerUi workerUi, ActivityOptions activityOptions) {
        Intent starter = new Intent(context, UserDetailsActivity.class);
        starter.putExtra(KEY_WORKER, workerUi);
        context.startActivity(starter, activityOptions.toBundle());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SimpleDateFormat formater = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        binding = ActivityUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WorkerUi workerUi = ((WorkerUi) getIntent().getSerializableExtra(KEY_WORKER));

        binding.name.setText(workerUi.getName());
        binding.phoneNumber.setText(workerUi.getPhone());
        binding.username1.setText(workerUi.getUsername());
        binding.email1.setText(workerUi.getEmail());
        String dateText = String.format(Locale.getDefault(), "%s, %d years", formater.format(workerUi.getDob()), workerUi.getAge());
        binding.dob1.setText(dateText);
        binding.nationality1.setText(workerUi.getNatCountry());
        binding.location1.setText(workerUi.getCity());
        String dateText1 = formater.format(workerUi.getRegistered());
        binding.registered.setText(dateText1);
        binding.btnMail.ivIcon.setImageResource(R.drawable.ic_mail);
        binding.btnMail.tvText.setText(R.string.email);
        binding.btnMap.ivIcon.setImageResource(R.drawable.ic_map);
        binding.btnMap.tvText.setText(R.string.map);

        IntentHelper intentHelper = new IntentHelper(this);
        binding.btnPhone.getRoot().setOnClickListener(v -> startActivity(intentHelper.getPhoneIntent(workerUi.getPhone())));
        binding.btnMail.getRoot().setOnClickListener(v -> startActivity(intentHelper.getEmail(workerUi.getEmail(), null, null, null)));
        binding.btnMap.getRoot().setOnClickListener(v -> startActivity(intentHelper.getMaps(workerUi.getCity())));
        binding.profileImage.setTransitionName(getString(R.string.avatarTransition, workerUi.getUsername()));
        binding.name.setTransitionName(getString(R.string.nameTransition, workerUi.getUsername()));
        postponeEnterTransition();
        Glide.with(this).load(workerUi.getAvatarUrlXXL()).circleCrop().addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                startPostponedEnterTransition();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                startPostponedEnterTransition();
                return false;
            }
        }).into(binding.profileImage);
    }

    @Override
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
