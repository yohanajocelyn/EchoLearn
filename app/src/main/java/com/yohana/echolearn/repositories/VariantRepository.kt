package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.AttemptResponse
import com.yohana.echolearn.models.AttemptSpeakingResponse
import com.yohana.echolearn.models.SpeakingRequest
import com.yohana.echolearn.models.VariantListResponse
import com.yohana.echolearn.models.VariantResponse
import com.yohana.echolearn.services.VariantAPIService
import retrofit2.Call

interface VariantRepository{
    fun getVariants(token: String, songId: Int, type: String): Call<VariantListResponse>
    fun checkAnswerSpeaking(token: String, variantId:Int, answer: String): Call<AttemptSpeakingResponse>
    fun getVariantById(token: String, variantId: Int): Call<VariantResponse>
}

class NetworkVariantRepository(
    private val variantAPIService: VariantAPIService
): VariantRepository{
    override fun getVariants(token: String, songId: Int, type: String): Call<VariantListResponse> {
        return variantAPIService.getVariants(token, songId, type)
    }
    override fun checkAnswerSpeaking(token: String, variantId: Int, answer: String): Call<AttemptSpeakingResponse> {
        return variantAPIService.checkAnswerSpeaking(token, variantId, SpeakingRequest(answer, variantId))
    }

    override fun getVariantById(token: String, variantId: Int): Call<VariantResponse> {
        return variantAPIService.getVariantById(token, variantId)
    }
}