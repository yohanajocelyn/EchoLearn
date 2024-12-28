package com.yohana.echolearn

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.yohana.echolearn.repositories.AuthenticationRepository
import com.yohana.echolearn.repositories.NetworkAuthenticationRepository
import com.yohana.echolearn.repositories.NetworkUserRepository
import com.yohana.echolearn.repositories.UserRepository
import com.yohana.echolearn.services.AuthenticationAPIService
import com.yohana.echolearn.services.UserAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val authenticationRepository: AuthenticationRepository
    val userRepository: UserRepository
}

class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>
): AppContainer {
    private val APIBaseURL = "http://192.168.51.250:3000/"

    private val authenticationRetrofitService: AuthenticationAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(AuthenticationAPIService::class.java)
    }

    private val userRetrofitService: UserAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(UserAPIService::class.java)
    }

    override val authenticationRepository: AuthenticationRepository by lazy {
        NetworkAuthenticationRepository(authenticationRetrofitService)
    }
    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userDataStore, userRetrofitService)
    }

    private fun initRetrofit(): Retrofit{
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)

        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .baseUrl(APIBaseURL)
            .build()
    }
}