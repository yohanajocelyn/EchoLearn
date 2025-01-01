package com.yohana.echolearn.services

import com.yohana.echolearn.models.AttemptDetailResponse
import com.yohana.echolearn.models.AttemptListResponse
import com.yohana.echolearn.models.AttemptResponse
import com.yohana.echolearn.models.GeneralResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AttemptAPIService {
    @POST("api/attempt")
    fun createAttempt(
        @Header("X-API-TOKEN") token: String,
        @Body createMap: HashMap<String, String>
    ): Call<GeneralResponseModel>

    @GET("api/attempt/{attemptId}")
    fun getAttempt(
        @Header("X-API-TOKEN") token: String,
        @Path("attemptId") attemptId: Int
    ): Call<AttemptResponse>

    @GET("api/attempts")
    fun getAttempts(
        @Header("X-API-TOKEN") token: String
    ): Call<AttemptListResponse>

    @GET("api/attempts-detail")
    fun getAttemptDetail(
        @Header("X-API-TOKEN") token: String,
    ): Call<AttemptDetailResponse>
}