package com.petro.scope102;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {
    private static final String ACTION = "com.petro.scope102.MyJavaBroadcastAction";
    private BroadcastReceiver br;
    private final IntentHelper intentHelper = new IntentHelper(this);
    private final ContactFetcher contactFetcher = new ContactFetcher(this);
    private final GalleryFetcher galleryFetcher = new GalleryFetcher(this);
    private final CameraFetcher cameraFetcher = new CameraFetcher(this);
    private final String phone = "+351914391816";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image_view);
        findViewById(R.id.BroadcastReceiverProgram2).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.petro.scope102.MyBroadcastAction");
            intent.setClass(this, MyBroadcastReceiver.class);
            intent.putExtra("message", "Hello!");
            sendBroadcast(intent);
        });
        findViewById(R.id.BroadcastReceiverProgram1).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(ACTION);
            intent.putExtra("message", "Ola!");
            sendBroadcast(intent);
        });
        findViewById(R.id.BroadcastReceiverProgram3).setOnClickListener(v -> {
            new EnterPhoneDialogueFragment().show(getSupportFragmentManager(), EnterPhoneDialogueFragment.TAG);
            /* startActivity(intentHelper.getPhoneIntent(phone));*/
        });
        findViewById(R.id.BroadcastReceiverProgram4).setOnClickListener(v -> {
            startActivity(intentHelper.getSMS(phone, "pisichka"));
        });
        findViewById(R.id.BroadcastReceiverProgram5).setOnClickListener(v -> {
            startActivity(intentHelper.getSite("https://www.icegay.tv/"));
        });
        findViewById(R.id.BroadcastReceiverProgram6).setOnClickListener(v -> {
            Intent intent = intentHelper.getEmail("petruust@gmail.com", "piska", "pipiska", null);
            startActivity(Intent.createChooser(intent, "Choose app"));
        });
        findViewById(R.id.BroadcastReceiverProgram7).setOnClickListener(v -> {
            contactFetcher.fetchContact(new ContactCallBack() {
                @Override
                public void onContactPicked(Contact contact) {
                    Toast.makeText(MainActivity.this, contact.toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onPermissionDenied() {
                    Toast.makeText(MainActivity.this, "Permission denied!", Toast.LENGTH_LONG).show();

                }
            });
        });
        findViewById(R.id.BroadcastReceiverProgram8).setOnClickListener(v -> {
            galleryFetcher.fetchImage(new GalleryFetcherCallback() {
                @Override
                public void onImagePicked(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }

                @Override
                public void onFileNotFound() {
                    Toast.makeText(MainActivity.this, "File not found!", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onRequestCancelled() {
                    Toast.makeText(MainActivity.this, "Request cancelled!", Toast.LENGTH_LONG).show();

                }
            });
        });
        findViewById(R.id.BroadcastReceiverProgram9).setOnClickListener(v -> {
            cameraFetcher.fetchImage(new CameraFetcherCallback() {
                @Override
                public void onImagePicked(String imagePath) {
                    imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
                }

                @Override
                public void onRequestCancelled() {
                    Toast.makeText(MainActivity.this, "Request cancelled!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(Exception exception) {
                    Toast.makeText(MainActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        });
        findViewById(R.id.BroadcastReceiverProgram10).setOnClickListener(v -> {
                cameraFetcher.fetchImage(new CameraFetcherCallback() {
                    @Override
                    public void onImagePicked(String imagePath) {
                        Intent intent = intentHelper.getEmail("petruust@gmail.com", "piska", "pipiska", imagePath);
                        startActivity(Intent.createChooser(intent, "Choose app to send email"));
                    }

                    @Override
                    public void onRequestCancelled() {

                    }

                    @Override
                    public void onError(Exception exception) {

                    }
                });
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        contactFetcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        contactFetcher.onActivityResult(requestCode, resultCode, data);
        galleryFetcher.onActivityResult(requestCode, resultCode, data);
        cameraFetcher.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        br = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ACTION);
        registerReceiver(br, filter, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(br);
    }
}
