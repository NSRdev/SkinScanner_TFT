package com.example.skinscanner.ui.scanner

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.skinscanner.R
import com.example.skinscanner.database.AppDatabase
import com.example.skinscanner.model.skin.SkinRecord
import com.example.skinscanner.ui.details.SkinRecordDetails
import com.example.skinscanner.ui.history.HistoryViewModel
import com.example.skinscanner.utils.*
import kotlinx.android.synthetic.main.fragment_scanner.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Result.Companion.success


class ScannerFragment : Fragment() {

    private lateinit var camera: Camera
    private lateinit var root: View
    private lateinit var file: File
    private lateinit var path: String
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var alertMessages: AlertMessages

    private val requiredPermissions = arrayOf(
        "android.permission.CAMERA",
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.READ_EXTERNAL_STORAGE"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_scanner, container, false)
        alertMessages = AlertMessages(root.context)

        Permissions.requestPermissions(requiredPermissions, activity!!, root.context)

        configView()

        return root
    }

    private fun configView() {
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        camera = Camera(root.context)
        file = camera.createPictureFile()

        val takePictureButton = root.findViewById<AppCompatButton>(R.id.take_picture)
        takePictureButton.setOnClickListener {
            val intent = camera.createIntent(file)
            startActivityForResult(intent, 100)
        }

        val uploadPictureButton = root.findViewById<AppCompatButton>(R.id.upload_picture)
        uploadPictureButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == 101 && data != null) {
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = root.context.contentResolver.query(
                    data.data!!, filePathColumn,
                    null, null, null
                )
                if (cursor != null) {
                    cursor.moveToFirst()
                    path = cursor.getString(cursor.getColumnIndex(filePathColumn[0]))
                    cursor.close()
                }

            } else if (requestCode == 100) {
                path = file.absolutePath
            }
            process()
        }
    }

    private fun process() {
        if (ConnectionDetector(root.context).verifyNetwork()) {
            val result = ApiRequest().execute(path).get();
            if (result.isNotEmpty()) {
                val skinRecord = SkinRecord(0, path, result,
                    SimpleDateFormat("dd/MM/yyyy - HH:mm").format(Date()), "")
                historyViewModel.insert(skinRecord)
                alertMessages.success()
            } else {
                alertMessages.serverError()
            }
        } else {
            alertMessages.internetError()
        }

    }
}
