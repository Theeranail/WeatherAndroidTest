package com.example.weatherandroidtest.adapter.viewholder

import android.util.Log
import com.example.weatherandroidtest.models.Rain
import com.example.weatherandroidtest.models.Weather
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class HourlyViewHolderPresenter(viewListener: HourlyViewHolderListener) {
    private var view = viewListener

    fun initTime(time: Long) {
        view.setTextTime(getTimeFromTimestamps(time))
    }

    fun initTemp(temp: Double) {
        view.setTextTemperature("${temp}°")
    }

    fun initRain(rain: Rain?) {
        rain?.let {
            view.setTextRain("${it.h}%")
        }
    }

    fun initDescription(w: Weather) {
        view.setTextDescription(w.description)
    }

    fun initIcon(icon: String) {
        val url = "http://openweathermap.org/img/wn/${icon}@2x.png"
        view.setIcon(url)
    }

    private fun getTimeFromTimestamps(time: Long): String {
        val simple = SimpleDateFormat("hh:mm");
//        simple.setTimeZone(TimeZone.getTimeZone(timeZone));
        val result = Date(time * 1000);
        return simple.format(result)
    }
}