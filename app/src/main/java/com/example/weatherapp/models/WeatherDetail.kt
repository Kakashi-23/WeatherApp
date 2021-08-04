package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

data class WeatherDetail(
        @SerializedName("location")
        var location:LocationInfo,

        @SerializedName("current")
        var current:LocationCurrentInfo
) {
    data class LocationInfo (
            @SerializedName("lat")
            var latitude:Double,
            @SerializedName("lon")
            var longitude:Double
            )

    data class LocationCurrentInfo (
            @SerializedName("temp_c")
            var tempInC:Double,
            @SerializedName("temp_f")
            var tempInF:Double
            )

}