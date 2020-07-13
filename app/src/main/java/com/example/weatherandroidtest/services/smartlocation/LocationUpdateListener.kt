package com.example.weatherandroidtest.services.smartlocation

import android.location.Location

interface LocationUpdateListener {
    fun onLocationUpdate(location: Location);
}