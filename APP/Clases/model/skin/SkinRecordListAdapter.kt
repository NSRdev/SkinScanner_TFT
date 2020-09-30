package com.example.skinscanner.model.skin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skinscanner.R
import com.example.skinscanner.ui.details.SkinRecordDetails
import com.example.skinscanner.database.AppDatabase
import com.example.skinscanner.utils.MySharedPreferences
import com.example.skinscanner.utils.SkinLesions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

class SkinRecordListAdapter internal constructor(
    context: Context
): RecyclerView.Adapter<SkinRecordListAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var skinRecords = emptyList<SkinRecord>()
    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.skinrecord_image)
        val name: TextView = view.findViewById(R.id.skinrecord_name)
        val date: TextView = view.findViewById(R.id.skinrecord_date)
        val detailsButton: AppCompatButton = view.findViewById(R.id.skinrecord_details)
        val deleteButton: AppCompatButton = view.findViewById(R.id.skinrecord_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.skinrecord_cardview, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var skinRecord = skinRecords[position]
        holder.name.text = context.resources.getString(SkinLesions.names[skinRecord.result] ?: error("N/A"))
        holder.date.text = skinRecord.date

        Glide
            .with(context)
            .load(File(skinRecord.imagePath))
            .centerCrop()
            .into(holder.image)

        holder.detailsButton.setOnClickListener(View.OnClickListener {
            val seeDetailsIntent = Intent(context, SkinRecordDetails::class.java).apply {
                MySharedPreferences(context).put("id", skinRecord.id.toString())
            }
            context.startActivity(seeDetailsIntent)
        })

        holder.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.title_delete_data))
            builder.setMessage(context.getString(R.string.text_confirm_delete_data))
            builder.setIcon(R.drawable.ic_delete)
            builder.setPositiveButton(context.getString(R.string.text_confirm)) { _, _ ->
                runBlocking {
                    launch {
                        AppDatabase.getAppDatabase(context).skinDAO().removeSkinRecord(skinRecord)
                    }
                }
            }
            builder.setNegativeButton(R.string.cancel_button) {_, _ -> }
            builder.show()
        }
    }

    internal fun setSkinRecords(skinRecords: List<SkinRecord>) {
        this.skinRecords = skinRecords
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return skinRecords.size
    }
}