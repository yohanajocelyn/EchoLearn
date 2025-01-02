package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.AttemptResponse
import com.yohana.echolearn.models.SpeakingRequest
import com.yohana.echolearn.models.VariantListResponse
import com.yohana.echolearn.services.VariantAPIService
import retrofit2.Call

interface VariantRepository{
    fun getVariants(songId: Int, type: String): Call<VariantListResponse>
    fun checkAnswerSpeaking(token: String, variantId:Int, answer: String): Call<AttemptResponse>
}

class NetworkVariantRepository(
    private val variantAPIService: VariantAPIService
): VariantRepository{
    override fun getVariants(songId: Int, type: String): Call<VariantListResponse> {
        return variantAPIService.getVariants(songId, type)
    }
    override fun checkAnswerSpeaking(token: String, variantId: Int, answer: String): Call<AttemptResponse> {
        return variantAPIService.checkAnswerSpeaking(token, variantId, SpeakingRequest(answer, variantId))
    }
}