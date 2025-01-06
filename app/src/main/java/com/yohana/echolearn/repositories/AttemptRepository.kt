package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.AttemptDetailResponse
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
    fun getAttemptDetail(token: String): Call<AttemptDetailResponse>
    fun updateAttempt(token: String, attemptId: Int, score: String, attemptedAnswer: String, isComplete: String, attemptedAt: String): Call<GeneralResponseModel>
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

    override fun getAttemptDetail(token: String): Call<AttemptDetailResponse> {
        return attemptAPIService.getAttemptDetail(token = token)
    }

    override fun updateAttempt(
        token: String,
        attemptId: Int,
        score: String,
        attemptedAnswer: String,
        isComplete: String,
        attemptedAt: String
    ): Call<GeneralResponseModel> {
        val updateMap = HashMap<String, String>()

        updateMap["score"] = score
        updateMap["attemptedAnswer"] = attemptedAnswer
        updateMap["isComplete"] = isComplete
        updateMap["attemptedAt"] = attemptedAt

        return attemptAPIService.updateAttempt(token = token, attemptId = attemptId, updateMap = updateMap)
    }
}