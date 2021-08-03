package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import java.util.*

class RegistrationActivity : AppCompatActivity() {

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

    val genderSpinnerValues= arrayOf("Male","Female","Other")
   // lateinit var genderDropdown:AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        init()
        buttonClickFunctions()
        dobSpinner()


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
    //    genderDropdown=findViewById(R.id.genderDropDown)
        dateIcon.setColorFilter(R.color.black)
        gender.inputType=InputType.TYPE_NULL
        dob.inputType=InputType.TYPE_NULL
       // TODO pincode  check color change

    }
    private fun buttonClickFunctions(){
        checkButton.setOnClickListener {
            // TODO get Pincode api result
            Toast.makeText(this,"here",Toast.LENGTH_SHORT).show()

        }
        registerButton.setOnClickListener {
            checkEnteredData()
           Toast.makeText(this,"Entered data passes",Toast.LENGTH_SHORT).show()
        }

        dateIcon.setOnClickListener {
            // TODO date selection dialog
        }
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
    }

    private fun dobSpinner(){
        val dropdownAdapter = ArrayAdapter(this,
                R.layout.support_simple_spinner_dropdown_item,genderSpinnerValues)
       gender.setAdapter(dropdownAdapter)
       gender.threshold= Int.MAX_VALUE
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
}