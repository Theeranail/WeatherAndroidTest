package com.example.weatherandroidtest.services.smartlocation

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.provider.Settings
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.nlopez.smartlocation.OnLocationUpdatedListener
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.location.config.LocationAccuracy
import io.nlopez.smartlocation.location.config.LocationParams
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider

//Request context from Activity only
class LocationManager(context: Context) : OnLocationUpdatedListener, LifecycleObserver {
    private val RESULT_CODE_LOCATION = 99;
    private val context: Context = context

    var locationUpdateListener: LocationUpdateListener? = null;

    companion object {
        var crrLatitude = 0.00
        var crrLongitude = 0.00
        var crrLocation: Location? = null;
    }

    override fun onLocationUpdated(location: Location) {
        crrLatitude = location.latitude
        crrLongitude = location.longitude
        crrLocation = location
        locationUpdateListener?.onLocationUpdate(location)
    }

    fun startLocation() {
        when (android.os.Build.VERSION.SDK_INT >= 23) {
            true -> {
                when (checkPermissionLocation()) {
                    true -> initLocation()
                    else -> requestPermission();
                }
            }
            else -> initLocation();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopLocation() {
        SmartLocation.with(context).location().stop();
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull grantResults: IntArray
    ) {
        if (requestCode == RESULT_CODE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (accessFINELocation()) {
                    Log.e("RequestPermissions", "OK")
                    initLocation()
                }
            }
        }
    }

    private fun initLocation() {
        when (isEnabledLocation()) {
            true -> {
                val param = LocationParams.Builder()
                    .setAccuracy(LocationAccuracy.HIGH)
                    .setDistance(50.0f)
                    .build();
                SmartLocation.with(this.context)
                    .location(LocationGooglePlayServicesWithFallbackProvider(context))
                    .config(param)
                    .start(this)
            }
            else -> locationServiceUnavailable();
        }
    }

    private fun isEnabledLocation(): Boolean {
        return SmartLocation.with(context).location().state().locationServicesEnabled();
    }

    private fun locationServiceUnavailable() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("เเจ้งเตือน!")
        builder.setMessage("บริการหาตำเเหน่งมือถือของคุณถูกปิดอยู่!")
        builder.setPositiveButton(
            "เปิด"
        ) { dialog, _ ->
            dialog.dismiss()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
        }
        builder.setNegativeButton(
            "ไม่ต้องการ"
        ) { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    private fun checkPermissionLocation(): Boolean {
        return accessFINELocation() && accessCOARSELocation()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            (context as Activity),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            RESULT_CODE_LOCATION
        )
        return;
    }

    private fun accessFINELocation(): Boolean {
        val afn = Manifest.permission.ACCESS_FINE_LOCATION;
        val rs = ActivityCompat.checkSelfPermission(context, afn)
        return rs === PackageManager.PERMISSION_GRANTED
    }

    private fun accessCOARSELocation(): Boolean {
        val afn = Manifest.permission.ACCESS_COARSE_LOCATION;
        val rs = ActivityCompat.checkSelfPermission(context, afn)
        return rs === PackageManager.PERMISSION_GRANTED
    }

}