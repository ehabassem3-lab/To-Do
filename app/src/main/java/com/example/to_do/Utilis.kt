package com.example.to_do
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.to_do.fragmets.task.DatesDummy
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.ArrayList
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun generateCurrentMonthDates(): ArrayList<DatesDummy> {
    val today = LocalDate.now()
    val firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth())
    val lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth())

    val dayAbbrevFormatter = DateTimeFormatter.ofPattern("E", Locale.getDefault())  // "Mon", "Tue" etc.

    val dates = ArrayList<DatesDummy>()

    var current = firstDayOfMonth
    while (!current.isAfter(lastDayOfMonth)) {
        val dayName = current.format(dayAbbrevFormatter)   // Short day name
        val dayNumber = current.dayOfMonth

        dates.add(DatesDummy(dayNumber, dayName))
        current = current.plusDays(1)
    }

    return dates
}