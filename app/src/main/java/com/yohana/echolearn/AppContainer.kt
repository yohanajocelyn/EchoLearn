package com.yohana.echolearn

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.yohana.echolearn.repositories.AttemptRepository
import com.yohana.echolearn.repositories.AuthenticationRepository
import com.yohana.echolearn.repositories.NetworkAttemptRepository
import com.yohana.echolearn.repositories.NetworkAuthenticationRepository
import com.yohana.echolearn.repositories.NetworkSongRepository
import com.yohana.echolearn.repositories.NetworkUserRepository
import com.yohana.echolearn.repositories.NetworkVariantRepository
import com.yohana.echolearn.repositories.SongRepository
import com.yohana.echolearn.repositories.UserRepository
import com.yohana.echolearn.repositories.VariantRepository
import com.yohana.echolearn.services.AttemptAPIService
import com.yohana.echolearn.services.AuthenticationAPIService
import com.yohana.echolearn.services.SongAPIService
import com.yohana.echolearn.services.UserAPIService
import com.yohana.echolearn.services.VariantAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val authenticationRepository: AuthenticationRepository
    val userRepository: UserRepository
    val songRepository: SongRepository
    val variantRepository: VariantRepository
    val attemptRepository: AttemptRepository
}

class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>
): AppContainer {
    //daffa's ip address
//    private val APIBaseURL = "http://192.168.51.250:3000/"

    //yohana's ip address
    private val APIBaseURL = "http://192.168.18.100:3000/"


    private val authenticationRetrofitService: AuthenticationAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(AuthenticationAPIService::class.java)
    }

    private val userRetrofitService: UserAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(UserAPIService::class.java)
    }

    private val songRetrofitService: SongAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(SongAPIService::class.java)
    }

    private val variantRetrofitService: VariantAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(VariantAPIService::class.java)
    }

    private val attemptRetrofitService: AttemptAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(AttemptAPIService::class.java)
    }

    override val authenticationRepository: AuthenticationRepository by lazy {
        NetworkAuthenticationRepository(authenticationRetrofitService)
    }
    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userDataStore, userRetrofitService)
    }
    override val songRepository: SongRepository by lazy {
        NetworkSongRepository(songRetrofitService)
    }
    override val variantRepository: VariantRepository by lazy {
        NetworkVariantRepository(variantRetrofitService)
    }
    override val attemptRepository: AttemptRepository by lazy {
        NetworkAttemptRepository(attemptRetrofitService)
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