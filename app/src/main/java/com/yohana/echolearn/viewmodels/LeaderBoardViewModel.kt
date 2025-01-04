package com.yohana.echolearn.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.gson.Gson
import com.yohana.echolearn.EchoLearnApplication
import com.yohana.echolearn.models.ErrorModel
import com.yohana.echolearn.models.LeaderboardListResponse
import com.yohana.echolearn.models.LeaderboardResponse
import com.yohana.echolearn.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.io.IOException

class LeaderBoardViewModel(private val userRepository: UserRepository) : ViewModel(){
    private val _users = MutableStateFlow<List<LeaderboardResponse>>(emptyList())
    val users: StateFlow<List<LeaderboardResponse>> = _users

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EchoLearnApplication)
                val userRepository = application.container.userRepository
                LeaderBoardViewModel(userRepository)
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



}