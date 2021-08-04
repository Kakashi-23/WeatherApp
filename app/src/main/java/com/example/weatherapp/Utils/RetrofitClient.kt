package com.example.weatherapp.Utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val client = OkHttpClient.Builder().build()
    private val pincodeRetrofit= Retrofit.Builder()
        .baseUrl(Constants.PINCODE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    private val weatherRetrofit= Retrofit.Builder().baseUrl(Constants.WEATHER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();

    public fun<T> buildPincodeService(service:Class<T>):T{
        return pincodeRetrofit.create(service)
    }
    public fun<T> buildWeatherService(service:Class<T>):T{
        return weatherRetrofit.create(service)
    }


}