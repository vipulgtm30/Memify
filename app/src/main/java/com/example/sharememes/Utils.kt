package com.example.sharememes

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Utils {

    fun View.visible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun View.enable(isEnabled: Boolean) {
        setEnabled(isEnabled)
        alpha = if (isEnabled) 1f else 0.5f
    }

    fun Context.toast(text: String?) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    fun Context.showPermissionRequestDialog(
        title: String,
        body: String,
        callback: () -> Unit
    ) {
        AlertDialog.Builder(this).also {
            it.setTitle(title)
            it.setMessage(body)
            it.setPositiveButton("Ok") { _, _ ->
                callback()
            }
        }.create().show()
    }

}