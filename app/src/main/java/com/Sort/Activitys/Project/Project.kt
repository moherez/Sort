package com.Sort.Activitys.Project

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.Sort.R

class Project : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)
        window.statusBarColor = Color.parseColor("#3F8F42")
    }
}