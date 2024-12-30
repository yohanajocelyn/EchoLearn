package com.yohana.echolearn.services

import com.yohana.echolearn.models.GeneralResponseModel
import com.yohana.echolearn.models.SpeakingRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SpeakingAPIService {
    @POST("api/todo-list/{id}")
    fun checkAnswer(@Header("X-API-TOKEN") token: String, @Path("id") variantId: Int, @Body speakingRequest: SpeakingRequest): Call<GeneralResponseModel>

}