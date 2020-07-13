package com.example.weatherandroidtest.services.http.apirouter

import com.example.weatherandroidtest.models.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface WeatherApiRouter {
    @GET("data/2.5/weather")
    fun fetchWeatherByLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units:String = "metric",
        @Query("appid") appid: String = "8f70f6a7662a576accf7cceb86b0c53b"
    ): Observable<WeatherResponse>

    @GET("data/2.5/weather")
    fun fetchWeatherByCityName(
        @Query("q") cityName: String,
        @Query("units") units:String = "metric",
        @Query("appid") appid: String = "8f70f6a7662a576accf7cceb86b0c53b"
    ):Observable<WeatherResponse>
}