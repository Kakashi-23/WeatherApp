package com.example.weatherapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.Repository.Repository
import com.example.weatherapp.Utils.Event
import com.example.weatherapp.models.WeatherDetail

class WeatherViewModel():ViewModel (){

  fun getDetailsFromRepo(cityName:String):MutableLiveData<Event<WeatherDetail>>{
        val repository=Repository()
       return repository.getData(cityName)
    }
}