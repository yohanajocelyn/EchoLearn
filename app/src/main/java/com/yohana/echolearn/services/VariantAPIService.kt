package com.yohana.echolearn.services

import com.yohana.echolearn.models.AttemptResponse
import com.yohana.echolearn.models.AttemptSpeakingResponse
import com.yohana.echolearn.models.SpeakingRequest
import com.yohana.echolearn.models.VariantListResponse
import com.yohana.echolearn.models.VariantResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface VariantAPIService {
    @POST("api/user/speaking/{variantId}")
    fun checkAnswerSpeaking(
        @Header("X-API-TOKEN") token: String,
        @Path("variantId") variantId: Int,
        @Body speakingRequest: SpeakingRequest
    ): Call<AttemptSpeakingResponse>


    @GET("api/variants/{songId}/{type}")
    fun getVariants(
        @Header("X-API-TOKEN") token: String,
        @Path("songId") songId: Int,
        @Path("type") type: String
    ): Call<VariantListResponse>

    @GET("api/variants/attempt/{variantId}")
    fun getVariantById(
        @Header("X-API-TOKEN") token: String,
        @Path("variantId") variantId: Int
    ): Call<VariantResponse>
}