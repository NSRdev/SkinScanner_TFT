package com.example.skinscanner.ui.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.skinscanner.R
import com.example.skinscanner.database.AppDatabase
import com.example.skinscanner.model.skin.SkinRecord
import com.example.skinscanner.ui.history.HistoryViewModel
import com.example.skinscanner.utils.MySharedPreferences
import com.example.skinscanner.utils.SkinLesions
import kotlinx.android.synthetic.main.activity_skin_record_details.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File


class SkinRecordDetails : AppCompatActivity() {

    private lateinit var skinRecord: SkinRecord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skin_record_details)

        getSkinRecord()
        configView()
        updateView()
    }

    private fun configView() {
        skinrecord_add_annotation.setOnClickListener {
            startActivityForResult(Intent(this, SkinRecordAnnotations::class.java),
                102)
        }

        more_info_button.setOnClickListener {
            MySharedPreferences(this).put("lesion_id", skinRecord.result)
            startActivity(Intent(this, LesionDescription::class.java))
        }

        details_back_button.setOnClickListener {
            finish()
        }
    }

    private fun updateView() {
        val context = this
        getSkinRecord()

        runBlocking {
            launch {
                skinrecord_annotations.text = skinRecord.annotations
                skinrecord_name.text = context.getString(SkinLesions.names[skinRecord.result] ?: error("N/A"))
                skinrecord_date.text = skinRecord.date
                skinrecord_location.text = skinRecord.imagePath

                Glide
                    .with(applicationContext)
                    .load(File(skinRecord.imagePath))
                    .into(skinrecord_picture)
            }
        }
    }

    private fun getSkinRecord() {
        val skinRecordID = MySharedPreferences(this).get("id").toInt()

        runBlocking {
            launch {
                skinRecord = AppDatabase.getAppDatabase(applicationContext).skinDAO().getSkinRecord(skinRecordID)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 102 && resultCode == Activity.RESULT_OK) updateView()
    }
}
