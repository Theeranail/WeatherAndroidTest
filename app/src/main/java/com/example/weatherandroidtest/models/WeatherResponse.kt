package com.example.weatherandroidtest.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

data class WeatherResponse(
    @SerializedName("coord")
    var coord: Coord,
    @SerializedName("weather")
    var weather: ArrayList<Weather> = ArrayList(),
    @SerializedName("base")
    var base: String = "",
    @SerializedName("main")
    var main: Main? = null,
    @SerializedName("visibility")
    var visibility: Int,
    @SerializedName("wind")
    var wind: Wind? = null,
    @SerializedName("dt")
    var dt: Long = 0,
    @SerializedName("sys")
    var sys: Sys? = null,
    @SerializedName("timezone")
    var timezone: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("cod")
    var cod: Int
) : Serializable {

}

data class Coord(
    @SerializedName("lon")
    var lon: Double,
    @SerializedName("lat")
    var lat: Double
) : Serializable {

}

data class Weather(
    @SerializedName("id")
    var id: Int,
    @SerializedName("main")
    var main: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("icon")
    var icon: String = ""
) : Serializable {

}

data class Main(
    @SerializedName("temp")
    var temp: Double,
    @SerializedName("pressure")
    var pressure: Int,
    @SerializedName("humidity")
    var humidity: Int,
    @SerializedName("temp_min")
    var tempMin: Double,
    @SerializedName("temp_max")
    var tempMax: Double
) : Serializable {

}

data class Wind(
    @SerializedName("speed")
    var speed: Double,
    @SerializedName("deg")
    var deg: Int,
    @SerializedName("gust")
    var gust: Int = 0

) : Serializable {

}

data class Clouds(
    @SerializedName("all")
    var all: Int
) : Serializable {

}

data class Sys(
    @SerializedName("type")
    var type: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("country")
    var country: String,
    @SerializedName("sunrise")
    var sunrise: Int,
    @SerializedName("sunset")
    var sunset: Int
) : Serializable {

}