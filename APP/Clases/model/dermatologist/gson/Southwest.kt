package com.example.skinscanner.model.dermatologist.gson

import com.google.gson.annotations.SerializedName

data class Southwest (
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)