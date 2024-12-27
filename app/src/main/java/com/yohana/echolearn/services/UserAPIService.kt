package com.yohana.echolearn.services

import com.yohana.echolearn.models.GeneralResponseModel
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface UserAPIService {
    @POST("api/logout")
        fun logout(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>
}