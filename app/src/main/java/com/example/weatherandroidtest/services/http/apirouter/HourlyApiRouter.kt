package com.example.weatherandroidtest.services.http.apirouter

import com.example.weatherandroidtest.models.HourlyResponse
import com.example.weatherandroidtest.models.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface HourlyApiRouter {
    @GET("data/2.5/onecall")
    fun fetchWeatherHourly(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String = "daily",
        @Query("appid") appid: String = "8f70f6a7662a576accf7cceb86b0c53b"
    ): Observable<HourlyResponse>
}