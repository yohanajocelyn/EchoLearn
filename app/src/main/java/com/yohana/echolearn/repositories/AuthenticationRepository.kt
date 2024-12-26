package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.UserResponse
import com.yohana.echolearn.services.AuthenticationAPIService
import retrofit2.Call

interface AuthenticationRepository{
    fun register(username: String, email: String, password: String): Call<UserResponse>
    fun login(email: String, password: String): Call<UserResponse>
}

class NetworkAuthenticationRepository(
    private val authenticationAPIService: AuthenticationAPIService
): AuthenticationRepository {
    override fun register(username: String, email: String, password: String): Call<UserResponse> {
        TODO("Not yet implemented")
    }

    override fun login(email: String, password: String): Call<UserResponse> {
        TODO("Not yet implemented")
    }

}