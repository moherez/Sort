package com.Sort.Activitys.Task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import com.Sort.R
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat

@Suppress("DEPRECATION")
class CreateTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        val picker =
                MaterialTimePicker.Builder().apply {
                    setTimeFormat(TimeFormat.CLOCK_12H)
                    setHour(12)
                    setMinute(10)
                    setTitleText("Select Appointment time")
                    setTheme(R.style.CustomCalendarDatePickerTheme)

                }.build()

        val isSystem24Hour = is24HourFormat(this)
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        MaterialTimePicker.Builder().setInputMode(INPUT_MODE_KEYBOARD)


        picker.show(supportFragmentManager,"tag")

        picker.addOnPositiveButtonClickListener {
            // call back code
        }
        picker.addOnNegativeButtonClickListener {
            // call back code
        }
        picker.addOnCancelListener {
            // call back code
        }
        picker.addOnDismissListener {
            // call back code
        }


    }

}