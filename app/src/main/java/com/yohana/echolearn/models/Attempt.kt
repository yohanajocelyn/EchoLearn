package com.yohana.echolearn.models

import java.util.Date

data class Attempt (
    val id: Int,
    val userId: Int,
    val variantId: Int,
    val correctAnswer: String,
    val attemptedAnswer: String,
    val score: Int,
    val attemptedAt: Date,
    val isComplete: Boolean,
)