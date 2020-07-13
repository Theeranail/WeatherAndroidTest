package com.example.weatherandroidtest.services.http.apiobservable

import android.location.Location
import com.example.weatherandroidtest.models.WeatherResponse
import com.example.weatherandroidtest.services.http.apirouter.WeatherApiRouter
import io.reactivex.Observable
import retrofit2.Retrofit

class WeatherApiObservable(retrofit: Retrofit) : HttpApiObservableBase() {
    private var apiRouter: WeatherApiRouter = retrofit.create(WeatherApiRouter::class.java);

    fun fetchWeatherByLocation(location: Location,unit:String = "metric"): Observable<WeatherResponse> {
        return initBaseObservable(
            this.apiRouter.fetchWeatherByLocation(
                lat = "${location.latitude}",
                lon = "${location.longitude}",
                units = unit
            )
        ) as Observable<WeatherResponse>
    }

    fun fetchWeatherByCityName(cityName: String,unit:String = "metric"): Observable<WeatherResponse> {
        return initBaseObservable(this.apiRouter.fetchWeatherByCityName(cityName,units = unit)) as Observable<WeatherResponse>
    }
}