package com.example.weatherandroidtest.services.http.apiobservable

import android.location.Location
import com.example.weatherandroidtest.models.HourlyResponse
import com.example.weatherandroidtest.services.http.apirouter.HourlyApiRouter
import io.reactivex.Observable
import retrofit2.Retrofit

class HourlyApiObservable(retrofit: Retrofit) : HttpApiObservableBase() {
    private var apiRouter: HourlyApiRouter = retrofit.create(HourlyApiRouter::class.java);

    fun fetchWeatherByHourly(location: Location, unit:String = "metric"): Observable<HourlyResponse> {
        return initBaseObservable(
            this.apiRouter.fetchWeatherHourly(
                lat = "${location.latitude}",
                lon = "${location.longitude}",
                units = unit
            )
        ) as Observable<HourlyResponse>
    }

}