package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.Attempt
import com.yohana.echolearn.models.AttemptResponse
import com.yohana.echolearn.models.GeneralResponseModel
import com.yohana.echolearn.models.SpeakingRequest
import com.yohana.echolearn.models.VariantListResponse
import com.yohana.echolearn.services.VariantAPIService
import retrofit2.Call

interface VariantRepository {
    fun checkAnswerSpeaking(token: String, variantId:Int, answer: String): Call<AttemptResponse>
    fun getVariants(songId: Int, type: String): Call<VariantListResponse>
}
class NetworkVariantRepository(private val variantAPIService: VariantAPIService): VariantRepository {
    override fun checkAnswerSpeaking(token: String, variantId: Int, answer: String): Call<AttemptResponse> {
        return variantAPIService.checkAnswerSpeaking(token, variantId, SpeakingRequest( answer, variantId))
    }

    override fun getVariants(songId: Int, type: String): Call<VariantListResponse> {
        return variantAPIService.getVariants(songId, type)
    }
}