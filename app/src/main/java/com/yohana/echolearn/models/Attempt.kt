package com.yohana.echolearn.models

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@SuppressLint("NewApi")
data class Attempt (
    val variantId: Int = 0,
    val correctAnswer: String = "",
    val attemptedAnswer: String = "",
    val score: Int = 0,
    val attemptedAt: Date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),
    val isComplete: Boolean = true,
){

    fun getDay(): String{
        val formatter = SimpleDateFormat("EEEE", Locale.getDefault())
        return formatter.format(attemptedAt)
    }

    fun getDate(): String {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return formatter.format(attemptedAt)
    }
}