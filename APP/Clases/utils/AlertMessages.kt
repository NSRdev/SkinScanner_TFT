package com.example.skinscanner.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.skinscanner.R

class AlertMessages(private val context: Context) {
    fun serverError() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.title_error))
        builder.setMessage(context.getString(R.string.text_connection_failed))
        builder.setIcon(R.drawable.ic_fail)
        builder.setPositiveButton(context.getString(R.string.ok)) { _, _ -> }
        builder.show()
    }

    fun internetError() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.title_error))
        builder.setMessage(context.getString(R.string.text_no_network))
        builder.setIcon(R.drawable.ic_signal_off)
        builder.setPositiveButton(context.getString(R.string.ok)) { _, _ -> }
        builder.show()
    }


    fun success() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.title_success))
        builder.setMessage(context.getString(R.string.text_succesfully_analized))
        builder.setIcon(R.drawable.ic_done)
        builder.setPositiveButton(context.getString(R.string.ok)) { _, _ -> }
        builder.show()
    }
}