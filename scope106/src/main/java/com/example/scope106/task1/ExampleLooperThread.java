package com.example.scope106.task1;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.scope106.task1.ExampleHandler;

public class ExampleLooperThread extends Thread {
    private static final String TAG = "ExampleLooperThread";

    public Looper looper;
    public Handler handler;
    private final Context context;

    public ExampleLooperThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        Looper.prepare();

        looper = Looper.myLooper();

        handler = new ExampleHandler(context);

        Looper.loop();

        Log.d(TAG, "End of run()");
    }
}