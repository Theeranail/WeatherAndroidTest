package com.example.weatherandroidtest.view

import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weatherandroidtest.models.WeatherResponse

interface MainViewListener {
    fun switchFC(): Switch
    fun textCityName(): TextView;
    fun txtTemperature(): TextView;
    fun txtMoisture(): TextView
    fun searchInput(): EditText
    fun swipeRefresh():SwipeRefreshLayout
    fun setSwipeRefresh(boolean: Boolean)
    fun txtDateCurrent():TextView
    fun intentHourly(weather:WeatherResponse,unit:String)
    fun txtDescription():TextView
    fun setIcClouds(iconPath:String)
}