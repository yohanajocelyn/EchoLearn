package com.yohana.echolearn.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yohana.echolearn.models.GeneralResponseModel
import com.yohana.echolearn.models.LeaderboardListResponse
import com.yohana.echolearn.models.UserListResponse
import com.yohana.echolearn.services.UserAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Call

interface UserRepository{
    val currentUserToken: Flow<String>
    val currentUsername: Flow<String>

    fun logout(token: String): Call<GeneralResponseModel>
    suspend fun saveUserToken(token: String)
    suspend fun saveUsername(username: String)
    fun getUsersByTotalScore(token: String): Call<LeaderboardListResponse>
}

class NetworkUserRepository(
    private val userDataStore: DataStore<Preferences>,
    private val userAPIService: UserAPIService
): UserRepository{
    private companion object {
        val USER_TOKEN = stringPreferencesKey("token")
        val USERNAME = stringPreferencesKey("username")
    }

    override val currentUserToken: Flow<String> = userDataStore.data.map {
        preferences -> preferences[USER_TOKEN] ?: "Unknown"
    }

    override val currentUsername: Flow<String> = userDataStore.data.map {
        preferences -> preferences[USERNAME] ?: "Unknown"
    }

    override suspend fun saveUserToken(token: String) {
        userDataStore.edit{
            preferences -> preferences[USER_TOKEN] = token
        }
    }

    override suspend fun saveUsername(username: String) {
        userDataStore.edit {
            preferences -> preferences[USERNAME] = username
        }
    }

    override fun logout(token: String): Call<GeneralResponseModel> {
        return userAPIService.logout(token)
    }

    override fun getUsersByTotalScore(token: String): Call<LeaderboardListResponse> {
        return userAPIService.getUsersByTotalScore(token)
    }
}