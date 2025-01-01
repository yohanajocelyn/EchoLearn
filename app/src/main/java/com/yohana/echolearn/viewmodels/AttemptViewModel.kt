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
import com.google.gson.Gson
import com.yohana.echolearn.EchoLearnApplication
import com.yohana.echolearn.models.AdditionalAttemptDetail
import com.yohana.echolearn.models.AttemptListResponse
import com.yohana.echolearn.models.AttemptModel
import com.yohana.echolearn.models.ErrorModel
import com.yohana.echolearn.models.VariantListResponse
import com.yohana.echolearn.repositories.AttemptRepository
import com.yohana.echolearn.uistates.AttemptDataStatusUIState
import com.yohana.echolearn.uistates.AuthenticationStatusUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AttemptViewModel(
    private val attemptRepository: AttemptRepository
): ViewModel() {

    private val _attempts = MutableStateFlow<List<AttemptModel>>(emptyList())
    val attempts: StateFlow<List<AttemptModel>> = _attempts

    private val _additionalData = MutableStateFlow<AdditionalAttemptDetail>(AdditionalAttemptDetail())
    val additionalData: StateFlow<AdditionalAttemptDetail> = _additionalData

    var dataStatus: AttemptDataStatusUIState by mutableStateOf(AttemptDataStatusUIState.Start)
        private set

    fun getAttempts(token: String) {
        viewModelScope.launch {
            try {
                dataStatus = AttemptDataStatusUIState.Loading

                val call = attemptRepository.getAttempts(token)
                call.enqueue(object: Callback<AttemptListResponse>{
                    override fun onResponse(
                        call: Call<AttemptListResponse>,
                        res: Response<AttemptListResponse>
                    ) {
                        if(res.isSuccessful){
                            _attempts.value = res.body()!!.data
                        }else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            dataStatus = AttemptDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(p0: Call<AttemptListResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        dataStatus = AttemptDataStatusUIState.Failed(t.localizedMessage!!)
                    }

                })
            }catch(error: IOException){
                dataStatus = AttemptDataStatusUIState.Failed(error.localizedMessage!!)
            }
        }
    }

    fun getAdditionalData(token: String, attemptId: Int){
        viewModelScope.launch {

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