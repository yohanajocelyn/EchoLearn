package com.yohana.echolearn.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.gson.Gson
import com.yohana.echolearn.EchoLearnApplication
import com.yohana.echolearn.models.AttemptResponse
import com.yohana.echolearn.models.AttemptSpeakingResponse
import com.yohana.echolearn.models.ErrorModel
import com.yohana.echolearn.models.GeneralResponseModel
import com.yohana.echolearn.models.SongModel
import com.yohana.echolearn.models.SongResponse
import com.yohana.echolearn.models.VariantListResponse
import com.yohana.echolearn.models.VariantModel
import com.yohana.echolearn.repositories.SongRepository
import com.yohana.echolearn.repositories.UserRepository
import com.yohana.echolearn.repositories.VariantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.Locale

class SpeakingViewModel(
    private val userRepository: UserRepository,
    private val songRepository: SongRepository,
    private val variantRepository: VariantRepository
) : ViewModel() {
    private val _recognizedText = MutableStateFlow("")
    val recognizedText: StateFlow<String> get() = _recognizedText

    private var speechRecognizer: SpeechRecognizer? = null

    private val _variants = MutableStateFlow<List<VariantModel>>(emptyList())
    val variants: StateFlow<List<VariantModel>> = _variants

    private val _variant = MutableStateFlow<VariantModel>(VariantModel())
    val variant: StateFlow<VariantModel> = _variant
    private val _answerResponse = MutableStateFlow<AttemptSpeakingResponse>(AttemptSpeakingResponse())
    val answerResponse: StateFlow<AttemptSpeakingResponse> get() = _answerResponse

    private val _isAnswerProcessed = MutableStateFlow(false)
    val isAnswerProcessed: StateFlow<Boolean> = _isAnswerProcessed

    private val _song = MutableStateFlow<SongModel>(SongModel())
    val song: StateFlow<SongModel> get() = _song
    fun askSpeechInput(context: Context, activity: Activity) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Talk Something")

            (context as Activity).startActivityForResult(intent, 102)
        }
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            _recognizedText.value = result?.get(0).toString()
        }
    }

    override fun onCleared() {
        super.onCleared()
        speechRecognizer?.destroy()
    }


    fun getSong(songId: Int) {
        viewModelScope.launch {
            try {
                val call = songRepository.getSongById(songId)
                call.enqueue(object : Callback<SongResponse> {
                    override fun onResponse(call: Call<SongResponse>, res: Response<SongResponse>) {
                        if (res.isSuccessful) {
                            _song.value = res.body()!!.data
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                        }
                    }

                    override fun onFailure(p0: Call<SongResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                    }
                })

            } catch (error: IOException) {
                Log.d("register-error", "REGISTER ERROR: ${error.localizedMessage}")
            }
        }
    }
    fun resetAnswerProcessed() {
        _isAnswerProcessed.value = false
    }
    fun checkAnswerSpeaking(token: String, id: Int, answer: String) {
        viewModelScope.launch {
            try {
                val call = variantRepository.checkAnswerSpeaking(
                    token = token,
                    variantId = id,
                    answer = answer
                )
                _isAnswerProcessed.value = true
                call.enqueue(object : Callback<AttemptSpeakingResponse > {
                    override fun onResponse(
                        call: Call<AttemptSpeakingResponse>,
                        res: Response<AttemptSpeakingResponse>
                    ) {
                        if (res.isSuccessful) {
                            Log.d("answer-response", "ANSWER RESPONSE: ${res.body()}")
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                        }
                    }

                    override fun onFailure(p0: Call<AttemptSpeakingResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                    }
                })

            } catch (error: IOException) {
                Log.d("register-error", "REGISTER ERROR: ${error.localizedMessage}")
            }
        }
    }
    //get variants
    fun getVariants(songId: Int, type: String) {
        viewModelScope.launch {
            try {
                val call = variantRepository.getVariants(songId, type)
                call.enqueue(object : Callback<VariantListResponse> {
                    override fun onResponse(
                        call: Call<VariantListResponse>,
                        res: Response<VariantListResponse>
                    ) {
                        if (res.isSuccessful) {
                            _variants.value = res.body()!!.data
                            randomizedVariants()
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                        }
                    }

                    override fun onFailure(p0: Call<VariantListResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                    }
                })

            } catch (error: IOException) {
                Log.d("register-error", "REGISTER ERROR: ${error.localizedMessage}")
            }
        }
    }

    fun randomizedVariants(): VariantModel {
        val randomIndex = (0 until _variants.value.size).random()
        _variant.value = _variants.value[randomIndex]
        return _variant.value
    }



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EchoLearnApplication)
                val userRepository = application.container.userRepository
                val songRepository = application.container.songRepository
                val variantRepository = application.container.variantRepository
                SpeakingViewModel(userRepository, songRepository, variantRepository)
            }
        }
    }
}