package com.example.weatherandroidtest.view

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weatherandroidtest.models.Daily
import com.example.weatherandroidtest.models.Hourly

interface HourlyViewListener {
    fun txtCityName():TextView;
    fun rcListHourly():RecyclerView
    fun swipeRefresh(): SwipeRefreshLayout
    fun setSwipeRefresh(boolean: Boolean)
    fun setAdapter(arrayList: ArrayList<Hourly>)
    fun txtUnit():TextView;
}