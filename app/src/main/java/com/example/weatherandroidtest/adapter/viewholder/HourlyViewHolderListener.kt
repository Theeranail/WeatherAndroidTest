package com.example.weatherandroidtest.adapter.viewholder

import android.widget.TextView

interface HourlyViewHolderListener {
    fun setTextTime(time:String);
    fun setTextTemperature(temperature:String)
    fun setTextRain(rain:String)
    fun setIcon(iconPath:String)
    fun setTextDescription(description:String)
}