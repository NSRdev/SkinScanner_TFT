package com.example.skinscanner.model.dermatologist.gson

import com.google.gson.annotations.SerializedName

data class OpeningHours (
    @SerializedName("open_now") val openNow: Boolean
)