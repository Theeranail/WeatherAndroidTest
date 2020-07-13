package com.example.weatherandroidtest.presenter

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import com.example.weatherandroidtest.models.HourlyResponse
import com.example.weatherandroidtest.models.WeatherResponse
import com.example.weatherandroidtest.services.http.HttpApiListener
import com.example.weatherandroidtest.services.http.HttpApiObserver
import com.example.weatherandroidtest.services.http.HttpClient
import com.example.weatherandroidtest.services.http.apiobservable.HourlyApiObservable
import com.example.weatherandroidtest.view.HourlyViewListener

class HourlyPresenter(context: Context, viewListener: HourlyViewListener) :
    HttpApiListener<HourlyResponse> {
    private val context = context;
    private val view = viewListener;

    override fun onRetrieveDone(apiResponse: HourlyResponse) {
        this.view.setSwipeRefresh(false)
        this.setDataView(apiResponse)
    }

    override fun onRetrieveError(errorMsg: String) {
        this.view.setSwipeRefresh(false)
    }

    fun initDataIntent(extra: Bundle?) {
        extra?.let {
            val weather = it.getSerializable("WeatherResponse") as WeatherResponse
            val unit = it.getString("unit")

            this.view.txtCityName().text = weather.name
            this.view.txtUnit().text = getUnitTypeToText(getUnitFromIntent(unit))
            val location = getLocationFromIntent(weather.coord.lat, weather.coord.lon)

            this.fetchWeatherHourly(location, getUnitFromIntent(unit))
        }
    }

    private fun fetchWeatherHourly(location: Location, unit: String) {
        this.view.setSwipeRefresh(true)
        val http = HttpClient(this.context)
        val observable = HourlyApiObservable(http.buildRestRetrofit())
        observable.fetchWeatherByHourly(location, unit)
            .subscribe(HttpApiObserver(this@HourlyPresenter))
    }

    private fun setDataView(hourlyResponse: HourlyResponse) {
        this.view.setAdapter(hourlyResponse.hourly)
    }

    private fun getUnitTypeToText(unit: String): String {
        return if (unit.equals("metric")) "°C" else "°F";
    }

    private fun getUnitFromIntent(unit: String?): String {
        return if (unit.isNullOrEmpty()) "metric" else unit
    }

    private fun getLocationFromIntent(lat: Double, lon: Double): Location {
        val location = Location(LocationManager.GPS_PROVIDER)
        location.latitude = lat
        location.longitude = lon
        return location;
    }


}