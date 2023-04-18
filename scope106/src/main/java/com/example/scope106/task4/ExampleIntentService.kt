package com.example.scope106.task4

import android.app.IntentService
import android.app.Notification
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.scope106.R

class ExampleIntentService : IntentService("ExampleIntentService") {
    private var wakeLock: PowerManager.WakeLock? = null

    init {
        setIntentRedelivery(true)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "ExampleApp:Wakelock"
        )
        wakeLock.acquire()
        Log.d(TAG, "Wakelock acquired")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification: Notification =
                NotificationCompat.Builder(this, App.Companion.CHANNEL_ID)
                    .setContentTitle("Example IntentService")
                    .setContentText("Running...")
                    .setSmallIcon(R.drawable.ic_android)
                    .build()
            startForeground(1, notification)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent")
        val input = intent!!.getStringExtra("inputExtra")
        for (i in 0..9) {
            Log.d(TAG, "$input - $i")
            SystemClock.sleep(1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        wakeLock!!.release()
        Log.d(TAG, "Wakelock released")
    }

    companion object {
        private const val TAG = "ExampleIntentService"
    }
}