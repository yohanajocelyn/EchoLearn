package com.yohana.echolearn.services

import com.yohana.echolearn.models.GeneralResponseModel
import com.yohana.echolearn.models.UserListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserAPIService {
    @POST("api/logout")
        fun logout(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>

        @GET("api/users/leaderboard")
        fun getUsersByTotalScore(@Header ("X-API-TOKEN") token: String):Call<UserListResponse>
}