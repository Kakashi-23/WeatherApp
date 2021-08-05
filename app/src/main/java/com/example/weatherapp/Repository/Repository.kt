package com.example.weatherapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.Utils.Event
import com.example.weatherapp.Utils.RetrofitClient
import com.example.weatherapp.models.WeatherDetail
import com.example.weatherapp.service.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    var weatherDetails=MutableLiveData<Event<WeatherDetail>>()
    private fun getWeatherDetail(cityName:String){
        val request = RetrofitClient.buildWeatherService(Api::class.java)
        val call = request.getWeatherDetail(Constants.key,cityName, Constants.aqi)
        call.enqueue(object : Callback<WeatherDetail>{
            override fun onResponse(call: Call<WeatherDetail>, response: Response<WeatherDetail>) {
               if (response.isSuccessful){
                val weatherDetail = response.body()
                if (weatherDetail!=null){
                    weatherDetails.value= Event.success(weatherDetail)
                }
               }else{
                   weatherDetails.value= Event.error(null)
               }
            }

            override fun onFailure(call: Call<WeatherDetail>, t: Throwable) {
                weatherDetails.value= Event.error(t.localizedMessage)
            }

        })
    }

    fun getData(cityName:String):MutableLiveData<Event<WeatherDetail>>{
        getWeatherDetail(cityName)
        return weatherDetails
    }
}