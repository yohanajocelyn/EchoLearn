package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.GeneralResponseModel
import com.yohana.echolearn.services.AttemptAPIService
import retrofit2.Call

interface AttemptRepository{
    fun createAttempt(
        token: String,
        variantId: String,
        correctAnswer: String,
        attemptedAnswer: String,
        score: String,
        attemptedAt: String,
        isComplete: String
    ): Call<GeneralResponseModel>
}

class NetworkAttemptRepository(
    private val attemptAPIService: AttemptAPIService
): AttemptRepository{
    override fun createAttempt(
        token: String,
        variantId: String,
        correctAnswer: String,
        attemptedAnswer: String,
        score: String,
        attemptedAt: String,
        isComplete: String
    ): Call<GeneralResponseModel> {
        var createMap = HashMap<String, String>()

        createMap["variantId"] = variantId
        createMap["correctAnswer"] = correctAnswer
        createMap["attemptedAnswer"] = attemptedAnswer
        createMap["score"] = score
        createMap["attemptedAt"] = attemptedAt
        createMap["isComplete"] = isComplete

        return attemptAPIService.createAttempt(token, createMap)
    }
}