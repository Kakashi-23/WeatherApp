package com.example.weatherapp.service

import com.example.weatherapp.models.PincodeDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("pincode/{pincode}")
    fun getPinCodeDetails(@Path("pincode") pincode:Int):Call<ArrayList<PincodeDetails>>
}