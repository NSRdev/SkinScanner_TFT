package com.example.skinscanner.ui.details

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import com.example.skinscanner.R
import com.example.skinscanner.utils.MySharedPreferences
import com.example.skinscanner.utils.SkinLesions
import kotlinx.android.synthetic.main.activity_lesion_description.*

class LesionDescription : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesion_description)

        configView()
    }

    private fun configView() {
        val id = MySharedPreferences(this).get("lesion_id")
        lesion_title.text = this.resources.getString(SkinLesions.names[id] ?: error("N/A"))
        lesion_description.text = this.resources.getString(SkinLesions.descriptions[id] ?: error("N/A"))
        lesion_symptoms.text = this.resources.getString(SkinLesions.symptoms[id] ?: error("N/A"))

        lesion_back_button.setOnClickListener {
            finish()
        }

        visit_mayoclinic.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mayoclinic.org"))
            startActivity(i)
        }
    }
}
