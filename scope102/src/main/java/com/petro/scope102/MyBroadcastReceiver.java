package com.petro.scope102;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String toast = intent.getStringExtra("message");
        Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
    }
}
