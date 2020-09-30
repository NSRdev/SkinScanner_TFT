package com.example.skinscanner.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object Permissions {
    fun requestPermissions(requiredPermissions: Array<String>,
                           fragmentActivity: FragmentActivity, context: Context) {

        for (perm in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(fragmentActivity, requiredPermissions, 101)
        }
    }
}