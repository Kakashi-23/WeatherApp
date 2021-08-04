package com.example.weatherapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.weatherapp.R
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.Utils.RetrofitClient
import com.example.weatherapp.models.WeatherDetail
import com.example.weatherapp.service.Api
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: MaterialToolbar
    lateinit var cityName: EditText
    lateinit var showResults: Button
    lateinit var latitude: TextView
    lateinit var longitude: TextView
    lateinit var tempInFah: TextView
    lateinit var tempInCen: TextView
    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        showResults.setOnClickListener {
            if (cityName.text.isNullOrEmpty()){
                Toast.makeText(this,"Enter city",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            progressBar.visibility =View.VISIBLE
            getWeatherDetails(cityName.text.toString())

        }

    }

    private fun getWeatherDetails(cityName: String) {
        val request = RetrofitClient.buildWeatherService(Api::class.java)
        val call = request.getWeatherDetail(Constants.key,cityName,Constants.aqi)
        call.enqueue(object: Callback<WeatherDetail>{
            override fun onResponse(call: Call<WeatherDetail>, response: Response<WeatherDetail>) {
                if (!response.isSuccessful) {
                    progressBar.visibility= View.GONE
                    Toast.makeText(
                            this@MainActivity, "Something went wrong try again later",
                            Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                val weatherDetail = response.body()
                if (weatherDetail!=null){

                    val current = weatherDetail.current
                    val location = weatherDetail.location
                    tempInCen.visibility = View.VISIBLE
                    tempInFah.visibility = View.VISIBLE
                    latitude.visibility = View.VISIBLE
                    longitude.visibility = View.VISIBLE
                    tempInCen.text ="Temperature in Centigrade : " + current.tempInC
                    tempInFah.text = "Temperature in Fahrenheit : " + current.tempInF
                    latitude.text = "Latitude : " + location.latitude
                    longitude.text = " Longitude : " + location.longitude
                    progressBar.visibility= View.GONE
                }else{
                    progressBar.visibility= View.GONE
                    Toast.makeText(
                            this@MainActivity, "Something went wrong try again later",
                            Toast.LENGTH_SHORT
                    ).show()
                    return
                }

            }

            override fun onFailure(call: Call<WeatherDetail>, t: Throwable) {
                progressBar.visibility= View.GONE
                Toast.makeText(this@MainActivity, "Something went wrong try again later", Toast.LENGTH_SHORT).show()

            }

        })


    }

    fun init(){
        toolbar = findViewById(R.id.toolBar)
        cityName=findViewById(R.id.cityName)
        showResults= findViewById(R.id.showResults)
        latitude = findViewById(R.id.latitude)
        longitude= findViewById(R.id.longitude)
        tempInCen=findViewById(R.id.tempCen)
        tempInFah=findViewById(R.id.tempFah)
        progressBar = findViewById(R.id.progressBar)
    }
}