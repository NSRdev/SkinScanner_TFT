package com.example.skinscanner.model.dermatologist.gson

import com.google.gson.annotations.SerializedName

data class Viewport (
    @SerializedName("northeast") val northeast: Northeast,
    @SerializedName("southwest") val southwest: Southwest
)