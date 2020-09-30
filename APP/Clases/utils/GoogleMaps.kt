package com.example.skinscanner.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.skinscanner.model.dermatologist.DermatologistRecord

class GoogleMaps {
    fun goTo(dermatologistRecord: DermatologistRecord, context: Context) {
        val uri = Uri.parse("geo:${dermatologistRecord.lat},${dermatologistRecord.lng}?q=${dermatologistRecord.name}")
        val googleMapsIntent = Intent(Intent.ACTION_VIEW, uri)
        googleMapsIntent.setPackage("com.google.android.apps.maps")

        if (googleMapsIntent.resolveActivity(context.packageManager) != null)
            context.startActivity(googleMapsIntent)
    }
}