package com.yohana.echolearn.viewmodels

import android.annotation.SuppressLint
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
import com.google.gson.Gson
import com.yohana.echolearn.EchoLearnApplication
import com.yohana.echolearn.models.AttemptDetail
import com.yohana.echolearn.models.AttemptDetailResponse
import com.yohana.echolearn.models.AttemptSongDetail
import com.yohana.echolearn.models.AttemptVariantDetail
import com.yohana.echolearn.models.ErrorModel
import com.yohana.echolearn.repositories.AttemptRepository
import com.yohana.echolearn.uistates.AttemptDataStatusUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AttemptViewModel(
    private val attemptRepository: AttemptRepository
): ViewModel() {

    private val _attempts = MutableStateFlow<List<AttemptDetail>>(emptyList())
    val attempts: StateFlow<List<AttemptDetail>> = _attempts

    var dataStatus: AttemptDataStatusUIState by mutableStateOf(AttemptDataStatusUIState.Start)
        private set

    @SuppressLint("SimpleDateFormat")
    fun getAttemptDetail(token: String) {
        viewModelScope.launch {
            try {
                dataStatus = AttemptDataStatusUIState.Loading

                val call = attemptRepository.getAttemptDetail(token)
                call.enqueue(object: Callback<AttemptDetailResponse>{
                    override fun onResponse(
                        call: Call<AttemptDetailResponse>,
                        res: Response<AttemptDetailResponse>
                    ) {
                        if(res.isSuccessful){
                            res.body()!!.data.let { attemptsList ->
                                val processedAttempts = attemptsList.map { attempt ->
                                    AttemptDetail(
                                        id = attempt.id,
                                        score = attempt.score,
                                        attemptedAt = attempt.attemptedAt, // Assuming attemptedAt is in the correct Date format
                                        isComplete = attempt.isComplete,
                                        variant = AttemptVariantDetail(
                                            type = attempt.variant.type
                                        ),
                                        song = AttemptSongDetail(
                                            title = attempt.song.title,
                                            artist = attempt.song.artist
                                        )
                                    )
                                }
                                _attempts.value = processedAttempts
                            }
                        }else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            dataStatus = AttemptDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(p0: Call<AttemptDetailResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        dataStatus = AttemptDataStatusUIState.Failed(t.localizedMessage!!)
                    }

                })
            }catch(error: IOException){
                dataStatus = AttemptDataStatusUIState.Failed(error.localizedMessage!!)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EchoLearnApplication)
                val attemptRepository = application.container.attemptRepository
                AttemptViewModel(attemptRepository = attemptRepository)
            }
        }
    }
}