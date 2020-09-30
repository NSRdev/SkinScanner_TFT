package com.example.skinscanner.model.dermatologist.gson

import com.google.gson.annotations.SerializedName

data class Northeast (
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)