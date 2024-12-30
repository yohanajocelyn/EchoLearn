package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.GeneralResponseModel
import com.yohana.echolearn.models.SpeakingRequest
import com.yohana.echolearn.services.SpeakingAPIService
import retrofit2.Call

interface SpeakingRepository {
    fun checkAnswer(token: String, variantId:Int, answer: String): Call<GeneralResponseModel>
}
class NetworkSpeakingRepository(private val speakingAPIService: SpeakingAPIService): SpeakingRepository {
    override fun checkAnswer(token: String, variantId: Int, answer: String): Call<GeneralResponseModel> {
        return speakingAPIService.checkAnswer(token, variantId, SpeakingRequest(answer))
    }
}