package com.yohana.echolearn.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.yohana.echolearn.EchoLearnApplication
import com.yohana.echolearn.models.ErrorModel
import com.yohana.echolearn.models.GeneralResponseModel
import com.yohana.echolearn.models.GetUserResponse
import com.yohana.echolearn.models.LeaderboardListResponse
import com.yohana.echolearn.models.LeaderboardResponse
import com.yohana.echolearn.models.ToGetUserResponse
import com.yohana.echolearn.repositories.UserRepository
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.uistates.StringDataStatusUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ProfileViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _users = MutableStateFlow<List<LeaderboardResponse>>(emptyList())
    val users: StateFlow<List<LeaderboardResponse>> = _users

    private val _user = MutableStateFlow<GetUserResponse>(GetUserResponse())
    val user: StateFlow<GetUserResponse> = _user

    var isLoading: Boolean by mutableStateOf(true)
        private set

    fun getUserByUsername(token: String, username: String) {
        viewModelScope.launch {
            try {
                val call = userRepository.getUserByUsername(token, username)
                call.enqueue(object : Callback<ToGetUserResponse> {
                    override fun onResponse(
                        call: Call<ToGetUserResponse>,
                        res: Response<ToGetUserResponse>
                    ) {
                        if (res.isSuccessful) {
                            val mappedUser = res.body()!!.data.let {
                                GetUserResponse(
                                    id = it.id,
                                    username = it.username,
                                    email = it.email,
                                    profilePicture = it.profilePicture,
                                    token = it.token,
                                    totalScore = it.totalScore
                                )
                            }
                            _user.value = mappedUser
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                        }
                    }

                    override fun onFailure(p0: Call<ToGetUserResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                    }
                })

            } catch (error: IOException) {
                Log.d("register-error", "REGISTER ERROR: ${error.localizedMessage}")
            }finally {
                isLoading = false
            }
        }
    }

    fun getUsersByTotalScore(token: String) {
        viewModelScope.launch {
            try {
                val call = userRepository.getUsersByTotalScore(token)
                call.enqueue(object : Callback<LeaderboardListResponse> {
                    override fun onResponse(
                        call: Call<LeaderboardListResponse>,
                        res: Response<LeaderboardListResponse>
                    ) {
                        if (res.isSuccessful) {
                            _users.value = res.body()!!.data
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                        }
                    }

                    override fun onFailure(p0: Call<LeaderboardListResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                    }
                })

            } catch (error: IOException) {
                Log.d("register-error", "REGISTER ERROR: ${error.localizedMessage}")
            }
        }
    }

    fun logout(token: String, navController: NavHostController) {
        viewModelScope.launch {
            try {
                val call = userRepository.logout(token)
                call.enqueue(object : Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        res: Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            saveUsernameToken("Unknown", "Unknown")
                            Log.d("logout-success", "LOGOUT SUCCESS: ${res.body()!!.data}")
                            navController.navigate(route = PagesEnum.Starter.name) {
                                popUpTo(0) { inclusive = true }
                            }

                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("logout-error", "LOGOUT ERROR: ${errorMessage}")
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        Log.d("logout-failure", "LOGOUT FAILURE: ${t.localizedMessage}")
                    }
                })
            } catch (error: IOException) {
                Log.d("logout-error", "LOGOUT ERROR: ${error.localizedMessage}")
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EchoLearnApplication)
                val userRepository = application.container.userRepository
                ProfileViewModel(userRepository)
            }
        }
    }

    fun saveUsernameToken(token: String, username: String) {
        viewModelScope.launch {
            userRepository.saveUserToken(token)
            userRepository.saveUsername(username)
        }
    }
}