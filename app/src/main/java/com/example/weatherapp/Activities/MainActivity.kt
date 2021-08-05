package com.example.weatherapp.Activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.Utils.Constants.checkConnection
import com.example.weatherapp.Utils.Event
import com.example.weatherapp.ViewModel.WeatherViewModel
import com.google.android.material.appbar.MaterialToolbar


class MainActivity : AppCompatActivity() {

    lateinit var toolbar: MaterialToolbar
    lateinit var cityName: EditText
    lateinit var showResults: Button
    lateinit var latitude: TextView
    lateinit var longitude: TextView
    lateinit var tempInFah: TextView
    lateinit var tempInCen: TextView
    lateinit var progressBar: ProgressBar
    lateinit var cityNameFromRegistration: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        if (intent!=null){
            cityNameFromRegistration = intent.getStringExtra("cityName").toString()
           cityName.setText( cityNameFromRegistration)
            getWeatherDetails(cityNameFromRegistration)
        }
        cityName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                tempInCen.visibility = View.GONE
                tempInFah.visibility = View.GONE
                latitude.visibility = View.GONE
                longitude.visibility = View.GONE
            }

        })
        showResults.setOnClickListener {
            if (cityName.text.isNullOrEmpty()){
                Toast.makeText(this,"Enter city",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            getWeatherDetails(cityName.text.toString())

        }

    }

    private fun getWeatherDetails(cityName: String) {
        if (checkConnection(this)) {
            progressBar.visibility=View.VISIBLE
            ViewModelProvider(this).get(WeatherViewModel::class.java)
                    .getDetailsFromRepo(cityName)!!.observe(this, Observer {
                        when (it.status) {
                            Event.Status.SUCCESS->{
                                val weatherDetail = it.content!!
                                val current = weatherDetail.current
                                val location = weatherDetail.location
                                tempInCen.visibility = View.VISIBLE
                                tempInFah.visibility = View.VISIBLE
                                latitude.visibility = View.VISIBLE
                                longitude.visibility = View.VISIBLE
                                tempInCen.text = "Temperature in Centigrade : " + current.tempInC
                                tempInFah.text = "Temperature in Fahrenheit : " + current.tempInF
                                latitude.text = "Latitude : " + location.latitude
                                longitude.text = " Longitude : " + location.longitude
                                progressBar.visibility = View.GONE
                            }
                            Event.Status.ERROR->{
                                if (it.message!=null){
                                    progressBar.visibility = View.GONE
                                    Toast.makeText(this@MainActivity,it.message,Toast.LENGTH_SHORT).show()
                                    tempInCen.visibility = View.GONE
                                    tempInFah.visibility = View.GONE
                                    latitude.visibility = View.GONE
                                    longitude.visibility = View.GONE
                                }else{
                                    progressBar.visibility = View.GONE
                                    Toast.makeText(this@MainActivity,"Something went wrong. Try again later",
                                        Toast.LENGTH_SHORT).show()

                                }
                            }
                        }
                })

        }else{
            Toast.makeText(this,"No internet",Toast.LENGTH_SHORT).show()
        }
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