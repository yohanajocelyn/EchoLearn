package com.yohana.echolearn.services

import com.yohana.echolearn.models.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticationAPIService {
    @POST("api/user/register")
    fun register(@Body registerMap: HashMap<String, String>): Call<UserResponse>

    @POST("api/user/login")
    fun login(@Body loginMap: HashMap<String, String>): Call<UserResponse>

    @GET("api/default-profile-pictures")
    suspend fun getDefaultProfilePictures(): List<String>
}