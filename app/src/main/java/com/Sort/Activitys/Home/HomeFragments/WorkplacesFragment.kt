package com.Sort.Activitys.Home.HomeFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.Sort.R

class WorkplacesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val toolbar_title = activity?.findViewById<TextView>(R.id.new_workplace_toolbar_textview_title)
        toolbar_title?.text = "Workplaces"

        return inflater.inflate(R.layout.fragment_workplaces, container, false)
    }

}