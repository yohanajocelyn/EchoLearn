package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.AdditionalAttemptDetailResponse
import com.yohana.echolearn.models.AttemptListResponse
import com.yohana.echolearn.models.AttemptResponse
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
    fun getAttempt(token: String, attemptId: Int): Call<AttemptResponse>
    fun getAttempts(token: String): Call<AttemptListResponse>
    fun getAdditionalAttemptData(token: String, attemptId: Int): Call<AdditionalAttemptDetailResponse>
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

    override fun getAttempt(token: String, attemptId: Int): Call<AttemptResponse> {
        return attemptAPIService.getAttempt(token = token, attemptId = attemptId)
    }

    override fun getAttempts(token: String): Call<AttemptListResponse> {
        return attemptAPIService.getAttempts(token = token)
    }

    override fun getAdditionalAttemptData(
        token: String,
        attemptId: Int
    ): Call<AdditionalAttemptDetailResponse> {
        return attemptAPIService.getAdditionalAttemptData(token = token, attemptId = attemptId)
    }
}