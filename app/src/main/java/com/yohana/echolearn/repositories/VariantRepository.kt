package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.GeneralResponseModel
import com.yohana.echolearn.models.SpeakingRequest
import com.yohana.echolearn.services.VariantAPIService
import retrofit2.Call

interface VariantRepository {
    fun checkAnswerSpeaking(token: String, variantId:Int, answer: String): Call<GeneralResponseModel>
    fun getVariants(songId: Int, type: String): Call<List<String>>
}
class NetworkVariantRepository(private val variantAPIService: VariantAPIService): VariantRepository {
    override fun checkAnswerSpeaking(token: String, variantId: Int, answer: String): Call<GeneralResponseModel> {
        return variantAPIService.checkAnswerSpeaking(token, variantId, SpeakingRequest(answer))
    }

    override fun getVariants(songId: Int, type: String): Call<List<String>> {
        return variantAPIService.getVariants(songId, type)
    }
}