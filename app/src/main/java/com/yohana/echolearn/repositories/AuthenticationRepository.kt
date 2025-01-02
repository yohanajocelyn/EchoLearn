package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.UserResponse
import com.yohana.echolearn.services.AuthenticationAPIService
import retrofit2.Call

interface AuthenticationRepository{
    fun register(username: String, email: String, password: String, profilePicture: String): Call<UserResponse>
    fun login(email: String, password: String): Call<UserResponse>
    
    suspend fun getDefaultProfilePictures(): List<String>
}

class NetworkAuthenticationRepository(
    private val authenticationAPIService: AuthenticationAPIService
): AuthenticationRepository {
    override fun register(username: String, email: String, password: String, profilePicture: String): Call<UserResponse> {
        var registerMap = HashMap<String, String>()

        registerMap["email"] = email
        registerMap["username"] = username
        registerMap["password"] = password
        registerMap["profilePicture"] = profilePicture

        return authenticationAPIService.register(registerMap)
    }

    override fun login(email: String, password: String): Call<UserResponse> {
        var loginMap = HashMap<String, String>()

        loginMap["email"] = email
        loginMap["password"] = password

        return authenticationAPIService.login(loginMap)
    }

    override suspend fun getDefaultProfilePictures(): List<String> {
        return authenticationAPIService.getDefaultProfilePictures()
    }
}