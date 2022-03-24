package com.Sort.Activitys.Workplace

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.Sort.R
import kotlinx.android.synthetic.main.activity_workplace_details.*

class WorkplaceDetails : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workplace_details)

        window.statusBarColor = parseColor("#D6E7C1")

        }

    }

