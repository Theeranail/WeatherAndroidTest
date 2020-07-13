package com.example.weatherandroidtest.presenter

import android.content.Context
import android.location.Location
import android.view.inputmethod.EditorInfo
import com.example.weatherandroidtest.models.WeatherResponse
import com.example.weatherandroidtest.services.http.HttpApiListener
import com.example.weatherandroidtest.services.http.HttpApiObserver
import com.example.weatherandroidtest.services.http.HttpClient
import com.example.weatherandroidtest.services.http.apiobservable.WeatherApiObservable
import com.example.weatherandroidtest.view.MainViewListener
import java.text.SimpleDateFormat
import java.util.*

class MainPresenter(context: Context, mainView: MainViewListener) :
    HttpApiListener<WeatherResponse> {
    private val context = context;
    private val mainView = mainView;
    private var dataResponse: WeatherResponse? = null;

    override fun onRetrieveDone(apiResponse: WeatherResponse) {
        this.mainView.setSwipeRefresh(false)
        this.dataResponse = apiResponse;
        this.setDataToView(apiResponse)
    }

    override fun onRetrieveError(errorMsg: String) {
        this.mainView.setSwipeRefresh(false)
    }

    fun fetchWeatherByCityName(cityName: String) {
        this.mainView.setSwipeRefresh(true)
        val http = HttpClient(this.context)
        val observable = WeatherApiObservable(http.buildRestRetrofit())
        val unit = getUnitFormSwitch(this.mainView.switchFC().isChecked)
        observable.fetchWeatherByCityName(cityName = cityName, unit = unit)
            .subscribe(HttpApiObserver(this@MainPresenter))
    }

    fun fetchWeatherByLocation(location: Location) {
        if (this.mainView.searchInput().text.isNullOrEmpty()) {
            this.mainView.setSwipeRefresh(true)
            val http = HttpClient(this.context)
            val observable = WeatherApiObservable(http.buildRestRetrofit())
            val unit = getUnitFormSwitch(this.mainView.switchFC().isChecked)
            observable.fetchWeatherByLocation(location, unit = unit)
                .subscribe(HttpApiObserver(this@MainPresenter))
        } else {
            fetchWeatherByCityName(this.mainView.searchInput().text.toString())
        }
    }

    fun initOnEditorAction(actionId: Int): Boolean {
        return when (actionId) {
            EditorInfo.IME_ACTION_SEARCH -> {
                this.fetchWeatherByCityName(mainView.searchInput().text.toString())
                true
            }
            EditorInfo.IME_ACTION_DONE -> {
                this.fetchWeatherByCityName(mainView.searchInput().text.toString())
                true
            }
            else -> false
        }
    }

    fun setLabelUnitSwitch(unitSwitch: Boolean) {
        this.mainView.switchFC().text = if (unitSwitch) "°F" else "°C"
    }

    fun switchTemperatureUnitValue(unitSwitch: Boolean) {
        val temperature = this.mainView.txtTemperature().text.toString().toDoubleOrNull()
        val unit = when (unitSwitch) {
            true -> calculatorCelsiusToFahrenheit(temperature)
            else -> calculatorFahrenheitToCelsius(temperature)
        }
        this.mainView.txtTemperature().text = "${String.format("%.2f", unit)}"
    }

    fun intentHourly() {
        this.dataResponse?.let {
            this.mainView.intentHourly(it, getUnitFormSwitch(this.mainView.switchFC().isChecked))
        }
    }

    private fun setDataToView(weather: WeatherResponse) {
        this.mainView.textCityName().text = weather.name
        weather.main?.let {
            this.mainView.txtTemperature().text = "${it.temp}"
            this.mainView.txtMoisture().text = "${it.humidity} %"
        }
        this.mainView.txtDateCurrent().text = calculatorFromMilliseconds(weather.dt)
        this.mainView.txtDescription().text = weather.weather.get(0).description
        val url = "http://openweathermap.org/img/wn/${weather.weather.get(0).icon}@2x.png"
        this.mainView.setIcClouds(url)
    }

    private fun getUnitFormSwitch(unitSwitch: Boolean): String {
        return if (unitSwitch) "imperial" else "metric"
    }

    private fun calculatorCelsiusToFahrenheit(celsius: Double?): Double {
        return if (celsius != null) (1.80 * celsius) + 32 else 0.00
    }

    private fun calculatorFahrenheitToCelsius(fahrenheit: Double?): Double {
        return if (fahrenheit != null) (fahrenheit - 32) / 1.80 else 0.00
    }

    private fun calculatorFromMilliseconds(miliSec: Long): String {
        val simple = SimpleDateFormat("dd MMM yyyy hh:mm");
        val result = Date(miliSec * 1000);
        return simple.format(result)
    }


}