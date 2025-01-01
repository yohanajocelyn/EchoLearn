package com.yohana.echolearn.models

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@SuppressLint("NewApi")
data class AttemptModel (
    val id: Int = 0,
    val userId: Int = 0,
    val variantId: Int = 0,
    val correctAnswer: String = "",
    val attemptedAnswer: String = "",
    val score: Int = 0,
    val attemptedAt: Date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),
    val isComplete: Boolean = true,
){

    fun getDay(): String{
        val formatter = SimpleDateFormat("EEE", Locale.getDefault())
        return formatter.format(attemptedAt)
    }

    fun getDate(): String {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return formatter.format(attemptedAt)
    }
}

data class AttemptResponse(
    val data: AttemptModel
)

data class AttemptListResponse(
    val data: List<AttemptModel>
)

data class AdditionalAttemptDetail(
    val songId: Int = 0,
    val songTitle: String = "",
    val artist: String = "",
    val type: String = "",
)

data class AdditionalAttemptDetailResponse(
    val data: AdditionalAttemptDetail
)