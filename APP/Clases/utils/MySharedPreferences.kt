package com.example.skinscanner.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class MySharedPreferences(val context: Context) {

    val sharedPreferences: SharedPreferences? = context.getSharedPreferences("Places", MODE_PRIVATE)

    fun get(key: String): String {
        return sharedPreferences?.getString(key, "-")!!
    }

    fun put(key: String, value: String) {
        sharedPreferences?.edit()?.putString(key, value)?.apply()
    }
}