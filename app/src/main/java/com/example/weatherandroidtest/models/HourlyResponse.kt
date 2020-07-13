package com.example.weatherandroidtest.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HourlyResponse(
    @SerializedName("lat")
    var lat: Double,
    @SerializedName("lon")
    var lon: Double,
    @SerializedName("timezone")
    var timezone: String,
    @SerializedName("timezone_offset")
    var timezoneOffset: Long,
    @SerializedName("current")
    var current: Current,
//    @SerializedName("daily")
//    var daily: ArrayList<Daily> = ArrayList(),
    @SerializedName("hourly")
    var hourly: ArrayList<Hourly> = ArrayList()
) : Serializable {
}

data class Current(
    @SerializedName("dt")
    var dt: Long,
    @SerializedName("sunrise")
    var sunrise: Long,
    @SerializedName("sunset")
    var sunset: Long,
    @SerializedName("temp")
    var temp: Double,
    @SerializedName("humidity")
    var humidity: Int,
    @SerializedName("weather")
    var weathers: ArrayList<Weather> = ArrayList()
) : Serializable {

}

data class Hourly(
    @SerializedName("dt")
    var dt: Long,
    @SerializedName("temp")
    var temp: Double,
    @SerializedName("humidity")
    var humidity: Int,
    @SerializedName("wind_speed")
    var windSpeed: Double,
    @SerializedName("wind_deg")
    var windDeg: Double,
    @SerializedName("weather")
    var weathers: ArrayList<Weather> = ArrayList(),
    var rain: Rain?
) : Serializable {

}

data class Daily(
    @SerializedName("dt")
    var dt: Long,
    @SerializedName("sunrise")
    var sunrise: Long,
    @SerializedName("sunset")
    var sunset: Long,
    @SerializedName("temp")
    var temp: Temp,
    @SerializedName("humidity")
    var humidity: Int,
    @SerializedName("wind_speed")
    var windSpeed: Double,
    @SerializedName("wind_deg")
    var windDeg: Double,
    @SerializedName("weather")
    var weathers: ArrayList<Weather> = ArrayList(),
    @SerializedName("rain")
    var rain: Double = 0.00
) : Serializable {

}

data class Temp(
    @SerializedName("day")
    var day: Double,
    @SerializedName("min")
    var min: Double,
    @SerializedName("max")
    var max: Double,
    @SerializedName("night")
    var night: Double,
    @SerializedName("eve")
    var eve: Double,
    @SerializedName("morn")
    var morn: Double
) : Serializable {

}

data class Rain(
    @SerializedName("1h")
    var h: Double
) : Serializable {

}