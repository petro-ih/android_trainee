package com.petro.scope104.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

class IntentHelper(private val context: Context) {
    fun getPhoneIntent(phone: String?): Intent {
        return Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
    }

    fun getEmail(email: String?, subject: String?, message: String?, filePath: String?): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        if (filePath != null) intent.putExtra(
            Intent.EXTRA_STREAM, FileProvider.getUriForFile(
                context, context.packageName + ".provider", File(filePath)
            )
        )
        return intent
    }

    fun getMaps(address: String?): Intent {
        val gmmIntentUri = Uri.parse("geo:0,0?q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        return mapIntent
    }
}