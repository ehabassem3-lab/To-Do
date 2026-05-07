package com.example.to_do

import android.view.View
import android.widget.TextView
import com.example.to_do.databinding.DateCardItemBinding
import com.kizitonwose.calendar.view.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {


    private val binding = DateCardItemBinding.bind(view)

    val DayName = binding.DayName
    val DayDate = binding.DayDate

}