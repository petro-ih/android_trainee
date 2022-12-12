package com.petro.scope102;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.FileNotFoundException;
import java.io.InputStream;

interface GalleryFetcherCallback{
    void onImagePicked(Bitmap bitmap);
    void onFileNotFound();
    void onRequestCancelled();
}

public class GalleryFetcher {

    private static final int RESULT_LOAD_IMG = 5;
    private GalleryFetcherCallback callback;
    private final Activity activity;
    GalleryFetcher(Activity activity){
        this.activity = activity;
    }

    void fetchImage(GalleryFetcherCallback callback){
        this.callback = callback;
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        activity.startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_LOAD_IMG) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = activity.getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    callback.onImagePicked(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    callback.onFileNotFound();
                }

            } else {
                callback.onRequestCancelled();
            }
        }
    }
}
