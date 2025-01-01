package com.yohana.echolearn.services

import com.yohana.echolearn.models.GeneralResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AttemptAPIService {
    @POST("api/attempt")
    fun createAttempt(
        @Header("X-API-TOKEN") token: String,
        @Body createMap: HashMap<String, String>
    ): Call<GeneralResponseModel>
}