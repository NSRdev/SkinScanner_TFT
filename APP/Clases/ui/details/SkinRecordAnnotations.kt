package com.example.skinscanner.ui.details

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.example.skinscanner.R
import com.example.skinscanner.database.AppDatabase
import com.example.skinscanner.model.skin.SkinRecord
import com.example.skinscanner.utils.MySharedPreferences
import kotlinx.android.synthetic.main.activity_skinrecord_annotations.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SkinRecordAnnotations : AppCompatActivity() {
    private lateinit var skinRecord: SkinRecord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skinrecord_annotations)
        val skinRecordID = MySharedPreferences(this).get("id").toInt()

        runBlocking {
            launch {
                skinRecord = AppDatabase
                    .getAppDatabase(applicationContext)
                    .skinDAO()
                    .getSkinRecord(skinRecordID)
            }
        }

        configView()
    }

    private fun configView() {
        skinrecord_annotation_input.setText(skinRecord.annotations)
        skinrecord_characters_count.text = skinRecord.annotations.length.toString() + " / 240"

        skinrecord_annotation_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                skinrecord_characters_count.text = s!!.length.toString() + " / 240"
            }
        })

        skinrecord_save_annotation.setOnClickListener {
            runBlocking {
                launch {
                    skinRecord.annotations = skinrecord_annotation_input.text.toString()
                    AppDatabase.getAppDatabase(it.context).skinDAO().update(skinRecord)
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }

        annotations_back_button.setOnClickListener {
            finish()
        }
    }
}
