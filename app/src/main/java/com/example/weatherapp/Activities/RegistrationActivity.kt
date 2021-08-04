package com.example.weatherapp.Activities

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.weatherapp.R
import com.example.weatherapp.Utils.DatePickerFragment
import com.example.weatherapp.Utils.RetrofitClient
import com.example.weatherapp.models.PincodeDetails
import com.example.weatherapp.service.Api
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegistrationActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener{

    lateinit var toolbar:MaterialToolbar
    lateinit var mobileNo:EditText
    lateinit var fullName:EditText
    lateinit var gender:AutoCompleteTextView
    lateinit var dob:EditText
    lateinit var addressLine1:EditText
    lateinit var addressLine2:EditText
    lateinit var pincode:EditText
    lateinit var dateIcon:ImageView
    lateinit var checkButton:Button
    lateinit var registerButton:Button
    lateinit var district:TextView
    lateinit var state:TextView
    lateinit var progressBar: ProgressBar
    val genderSpinnerValues= arrayOf("Male","Female","Other")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        init()
        dobSpinner()
       registerButton.setOnClickListener {
           checkEnteredData()
       }

       dateIcon.setOnClickListener {
           val datePicker = DatePickerFragment()
           datePicker.show(supportFragmentManager,"date picker")
       }

       pincode.addTextChangedListener(object : TextWatcher {
           override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

           }

           override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
           }

           @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
           override fun afterTextChanged(p0: Editable?) {
               if (pincode.text.length<6){
                   pincode.error="Pin-code must be 6 digit long"
                   checkButton.isEnabled=false
                   checkButton.setBackgroundResource(R.color._grey)
                   return
               }
               checkButton.isEnabled=true
               checkButton.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.black_light)))
               checkButton.setOnClickListener {
                   progressBar.visibility=View.VISIBLE
                   val PINCODE = pincode.text.toString().toInt();
                   getPincodeDetails(PINCODE)
               }
           }

       })

    }

    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        mobileNo=findViewById(R.id.mobileNo)
        fullName=findViewById(R.id.fullName)
        gender=findViewById(R.id.gender)
        dob=findViewById(R.id.dob)
        addressLine1=findViewById(R.id.address_line_1)
        addressLine2=findViewById(R.id.address_line_2)
        pincode=findViewById(R.id.pincode)
        checkButton=findViewById(R.id.check_pincode)
        dateIcon=findViewById(R.id.calender_icon)
        registerButton=findViewById(R.id.register)
        district=findViewById(R.id.district)
        state=findViewById(R.id.state)
        progressBar=findViewById(R.id.animationView)
        dateIcon.setColorFilter(R.color.black)


    }
    private fun checkEnteredData() {
        if (mobileNo.text.isNullOrBlank()){
            mobileNo.error = "Enter Mobile No"
            return
        }else if (fullName.text.isNullOrBlank()){
            fullName.error = "Enter Name"
            return
        }else if (gender.text.isNullOrBlank()){
            gender.error = "Select gender"
            return
        }else if (dob.text.isNullOrBlank()){
            dob.error = "Enter Date of Birth"
            return
        }else if (addressLine1.text.isNullOrBlank()){
            addressLine1.error = "Enter address"
            return
        }else if (pincode.text.isNullOrBlank()){
            pincode.error = "Enter Pin-code"
            return
        }else if (addressLine1.text.toString().length<3){
            addressLine1.error = "Address should be minimun 3 letters"
            return
        }else if (mobileNo.text.toString().length<10){
            mobileNo.error = "Enter correct Mobile No"
            return
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun dobSpinner(){
        val dropdownAdapter = ArrayAdapter(this,
            R.layout.support_simple_spinner_dropdown_item,genderSpinnerValues)
       gender.setAdapter(dropdownAdapter)
      // gender.threshold= Int.MAX_VALUE
        gender.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               gender.setText(genderSpinnerValues[p2])
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")

            }

        }
        gender.setOnClickListener {
            gender.showDropDown()

        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, date: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR,year)
        calendar.set(Calendar.MONTH,month)
        calendar.set(Calendar.DATE,date)
        val finalDate = DateFormat.getDateInstance().format(calendar.time)
        dob.setText(finalDate)
    }

    private fun getPincodeDetails(pincode:Int){

        val request = RetrofitClient.buildPincodeService(Api::class.java)
        val call = request.getPinCodeDetails(pincode)
        call.enqueue(object : Callback<ArrayList<PincodeDetails>> {
            override fun onResponse(
                call: Call<ArrayList<PincodeDetails>>,
                response: Response<ArrayList<PincodeDetails>>
            ) {
                if (!response.isSuccessful) {
                    progressBar.visibility=View.GONE
                    Toast.makeText(
                        this@RegistrationActivity, "Something went wrong try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                val body = response.body()
                if (body!=null){
                    val mainData = body.get(0)
                    if (mainData.status.equals("Error")){
                        progressBar.visibility=View.GONE
                        Toast.makeText(
                            this@RegistrationActivity, mainData.message ,Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    val postOffice = mainData.postOfficeDetails.get(0)
                    state.text="State : "+postOffice.state
                    district.text="District : "+postOffice.district
                    progressBar.visibility=View.GONE

                }else{
                    progressBar.visibility=View.GONE
                    Toast.makeText(
                        this@RegistrationActivity, "Something went wrong try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<ArrayList<PincodeDetails>>, t: Throwable) {
                progressBar.visibility=View.GONE
                Toast.makeText(
                    this@RegistrationActivity, t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}