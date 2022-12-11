package com.petro.scope102;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

interface CameraFetcherCallback {
    void onImagePicked(String imagePath);

    void onRequestCancelled();

    void onError(Exception exception);
}

public class CameraFetcher {

    private static final int CAMERA_REQUEST_CODE = 112;
    private final Activity activity;
    private CameraFetcherCallback callback;
    private String imageFilePath;

    CameraFetcher(Activity activity) {
        this.activity = activity;
    }

    void fetchImage(CameraFetcherCallback callback) {
        this.callback = callback;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException exception) {
                // Error occurred while creating the File
                exception.printStackTrace();
                callback.onError(exception);
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        }
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */);

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                // BitMap is data structure of image file which store the image in memory
                callback.onImagePicked(imageFilePath);
            } else {
                callback.onRequestCancelled();
            }
        }
    }
}
