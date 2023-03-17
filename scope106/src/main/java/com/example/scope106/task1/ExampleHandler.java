package com.example.scope106.task1;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class ExampleHandler extends Handler {
    private static final String TAG = "ExampleHandler";

    public static final int TASK_A = 1;
    public static final int TASK_B = 2;
    private final Context context;
    private final Handler uiThreadHandler = new Handler(Looper.getMainLooper());

    public ExampleHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case TASK_A:
                toast("Executing Task A started");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                toast("Executing Task A completed");
                break;
            case TASK_B:
                toast("Executing Task B started");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                toast("Executing Task B completed");
                break;
        }
    }

    private void toast(String message) {
        uiThreadHandler.post(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }
}