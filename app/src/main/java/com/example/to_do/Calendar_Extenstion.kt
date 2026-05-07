package com.example.to_do

import java.util.Calendar

fun Calendar.year()= get(Calendar.YEAR)
fun Calendar.month()= get(Calendar.MONTH)
fun Calendar.day()= get(Calendar.DAY_OF_MONTH)
fun Calendar.format()= "${day()}/${month()+1}/${year()}"



