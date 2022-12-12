package com.petro.scope102;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

interface ContactCallBack {
    void onContactPicked(Contact contact);

    void onPermissionDenied();
}

public class ContactFetcher {
    private static final int CONTACT_PICK_CODE = 2;
    private static final int CONTACT_PERMISSION_CODE = 1;
    private ContactCallBack callBack;
    private final Activity activity;

    ContactFetcher(Activity activity) {
        this.activity = activity;
    }

    public void fetchContact(ContactCallBack callBack) {
        this.callBack = callBack;
        if (checkContactPermission()) {
            //permission granted, pick contact
            pickContactIntent();
        } else {
            //permission not granted, request
            requestContactPermission();
        }
    }

    private void requestContactPermission() {
        //permissions to request
        String[] permission = {Manifest.permission.READ_CONTACTS};

        ActivityCompat.requestPermissions(activity, permission, CONTACT_PERMISSION_CODE);
    }

    private boolean checkContactPermission() {
        //check if contact permission was granted or not
        boolean result = ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_CONTACTS) == (PackageManager.PERMISSION_GRANTED
        );
        return result;  //true if permission granted, false if not
    }

    private void pickContactIntent() {
        //intent to pick contact
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        activity.startActivityForResult(intent, CONTACT_PICK_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CONTACT_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted, can pick contact now
                pickContactIntent();
            } else {
                //permission denied
                callBack.onPermissionDenied();
            }
        }
    }

    @SuppressLint("Range")
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            //calls when user click a contact from list

            if (requestCode == CONTACT_PICK_CODE) {
                Cursor cursor1, cursor2;
                Uri uri = data.getData();
                cursor1 = activity.getContentResolver().query(uri, null, null, null, null);


                if (cursor1.moveToFirst()) {
                    Contact contact = new Contact();
                    //get contact details
                    String contactId = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
                    String contactName = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String contactThumnail = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                    String idResults = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    int idResultHold = Integer.parseInt(idResults);


                    contact.name = contactName;
                    if (idResultHold == 1) {
                        cursor2 = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                                null,
                                null
                        );
                        //a contact may have multiple phone numbers
                        while (cursor2.moveToNext()) {
                            //get phone number
                            String contactNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            //set details
                            contact.phones.add(contactNumber);
                        }
                        cursor2.close();
                    }

                    callBack.onContactPicked(contact);
                    cursor1.close();
                }
            }
        }
    }
}

class Contact {
    final ArrayList<String> phones = new ArrayList<>();
    String name;

    @Override
    public String toString() {
        return name + " " + String.join(", ", phones);
    }
}

