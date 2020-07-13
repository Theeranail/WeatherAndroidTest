package com.example.weatherandroidtest.koinmodule

import com.example.weatherandroidtest.MainActivity
import com.example.weatherandroidtest.presenter.MainPresenter
import com.example.weatherandroidtest.services.http.HttpClient
import com.example.weatherandroidtest.services.http.apiobservable.WeatherApiObservable
import com.example.weatherandroidtest.services.smartlocation.LocationManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single { LocationManager(get()) }
    single { HttpClient(androidContext()) }
//    single { WeatherApiObservable(get<HttpClient>().buildRestRetrofit()) }
    factory { WeatherApiObservable(get()) }
    scope(named<MainActivity>()) {
        scoped {
            MainPresenter(context = get(), mainView = get())
        }
    }
}