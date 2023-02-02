package com.petro.scope104.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;

public class IntentHelper {
    private final Context context;

    public IntentHelper(Context context) {
        this.context = context;
    }

    public Intent getPhoneIntent(String phone) {
        return new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
    }

    public Intent getSMS(String phone, String text) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone, null));
        intent.putExtra("sms_body", text);
        return intent;
    }

    public Intent getSite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        return intent;
    }

    public Intent getEmail(String email, String subject, String message, @Nullable String filePath) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        if (filePath != null)
            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, context.getPackageName() + ".provider", new File(filePath)));
        return intent;
    }

    public Intent getMaps(String address) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        return mapIntent;
    }
}