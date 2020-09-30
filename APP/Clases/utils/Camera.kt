package com.example.skinscanner.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.StrictMode
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.core.widget.ContentLoadingProgressBar
import com.example.skinscanner.model.skin.SkinRecord
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Camera(val context: Context) {

    fun createIntent(file: File): Intent {
        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        var takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (takePictureIntent.resolveActivity(context.packageManager) != null) run {
            val picturePath = file.absolutePath
            val pictureURI = FileProvider.getUriForFile(context,
                "com.example.skinscanner.provider", file)

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureURI)
            takePictureIntent.putExtra("path", picturePath)
        }
        return takePictureIntent
    }

    fun createPictureFile(): File {
        val picturesFolder = "/storage/emulated/0/Pictures"
        val skinScannerDirectory: File = File("$picturesFolder/SkinScanner")

        if (!skinScannerDirectory.exists()) skinScannerDirectory.mkdir()

        return File("$skinScannerDirectory/" +
                    System.currentTimeMillis() + "_SkinScanner.jpg"
        )
    }
}