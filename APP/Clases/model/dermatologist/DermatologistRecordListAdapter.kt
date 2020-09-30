package com.example.skinscanner.model.dermatologist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skinscanner.utils.GoogleMaps
import com.example.skinscanner.R

class DermatologistRecordListAdapter internal constructor(
    context: Context
): RecyclerView.Adapter<DermatologistRecordListAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var dermatologistRecords = emptyList<DermatologistRecord>()
    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: AppCompatTextView = view.findViewById(R.id.dermatologist_name)
        val location: AppCompatTextView = view.findViewById(R.id.dermatologist_location)
        val rating: AppCompatTextView = view.findViewById(R.id.dermatologist_rating)
        val openInMapsButton: AppCompatButton = view.findViewById(R.id.open_maps_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.dermatologist_cardview, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var dermatologistRecord = dermatologistRecords[position]
        holder.name.text = dermatologistRecord.name
        holder.location.text = dermatologistRecord.location

        val rating = dermatologistRecord.rating
        if (rating == 0.0) holder.rating.text = "N/A"
        else holder.rating.text = rating.toString()

        holder.openInMapsButton.setOnClickListener(View.OnClickListener {
            GoogleMaps()
                .goTo(dermatologistRecord, context)
        })
    }

    internal fun setDermatologistRecords(dermatologistRecords: List<DermatologistRecord>) {
        this.dermatologistRecords = dermatologistRecords
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dermatologistRecords.size
    }


}