package com.example.skinscanner.model.dermatologist.gson

import com.google.gson.annotations.SerializedName

data class Response (
    @SerializedName("html_attributions") val htmlAttributions: List<Object>,
    @SerializedName("results") val results: List<Result>,
    @SerializedName("status") val status: String
)