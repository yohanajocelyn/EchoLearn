package com.yohana.echolearn.services

import com.yohana.echolearn.models.GeneralResponseModel
import com.yohana.echolearn.models.GetUserResponse
import com.yohana.echolearn.models.LeaderboardListResponse
import com.yohana.echolearn.models.UpdateUserRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPIService {
    @DELETE("api/user/logout")
    fun logout(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>

    @GET("api/users/leaderboard")
    fun getUsersByTotalScore(@Header("X-API-TOKEN") token: String): Call<LeaderboardListResponse>

    @GET("api/user/{username}")
    fun getUserById(
        @Header("X-API-TOKEN") token: String,
        @Path("username") username: String
    ): Call<GetUserResponse>

    @PUT("api/user/update/{userId}")
    fun updateUser(
        @Header("X-API-TOKEN") token: String,
        @Path("userId") userId: Int,
        @Body updateUserRequest: UpdateUserRequest
    ):Call<String>

}