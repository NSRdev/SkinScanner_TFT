package com.example.skinscanner.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Gps(private val context: Context) {
    private var locationManager : LocationManager? = null
    private var locationListener: LocationListener

    init {
        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager?
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
            }
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }
            override fun onProviderEnabled(provider: String?) {
            }
            override fun onProviderDisabled(provider: String?) {
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun requestLocation(): Location {
        runBlocking {
            launch {
                locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L,
                    0f, locationListener)
            }
        }
        return locationManager!!.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
    }
}