package com.example.weatherapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import java.util.*


class DatePickerFragment(): DialogFragment() {

 @RequiresApi(Build.VERSION_CODES.N)
 @NonNull
 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
  val calender = Calendar.getInstance()
  val year =calender.get(Calendar.YEAR)
  val month = calender.get(Calendar.MONTH)
  val date = calender.get(Calendar.DATE)

  return DatePickerDialog(requireActivity(),
          requireActivity() as DatePickerDialog.OnDateSetListener,year,month,date)
 }


 }