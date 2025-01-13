package com.yohana.echolearn.viewmodels

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.google.gson.Gson
import com.yohana.echolearn.EchoLearnApplication
import com.yohana.echolearn.models.AttemptSpeakingResponse
import com.yohana.echolearn.models.ErrorModel
import com.yohana.echolearn.models.SongModel
import com.yohana.echolearn.models.SongResponse
import com.yohana.echolearn.models.VariantListResponse
import com.yohana.echolearn.models.VariantModel
import com.yohana.echolearn.repositories.SongRepository
import com.yohana.echolearn.repositories.UserRepository
import com.yohana.echolearn.repositories.VariantRepository
import com.yohana.echolearn.uistates.StringDataStatusUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.Locale

class SpeakingViewModel(
    private val userRepository: UserRepository,
    private val songRepository: SongRepository,
    private val variantRepository: VariantRepository,
) : ViewModel() {
    private val _recognizedText = MutableStateFlow("")
    val recognizedText: StateFlow<String> get() = _recognizedText

    private var speechRecognizer: SpeechRecognizer? = null

    private val _variants = MutableStateFlow<List<VariantModel>>(emptyList())
    val variants: StateFlow<List<VariantModel>> = _variants

    private val _variant = MutableStateFlow<VariantModel>(VariantModel())
    val variant: StateFlow<VariantModel> = _variant

    private var _answerResponse =
        MutableStateFlow<AttemptSpeakingResponse>(AttemptSpeakingResponse())
    val answerResponse: StateFlow<AttemptSpeakingResponse> get() = _answerResponse

    private val _isAnswerProcessed = MutableStateFlow(false)
    val isAnswerProcessed: StateFlow<Boolean> = _isAnswerProcessed

    var audio: MediaPlayer by mutableStateOf(MediaPlayer())
        private set

    var isContinue: Boolean by mutableStateOf(false)
        private set
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    var createStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set
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

    init {
        // Tambahkan Disposable untuk membersihkan resource
        addDisposableHandle {
            speechRecognizer?.destroy()
            audio.release()
        }
    }

    private fun addDisposableHandle(onDispose: () -> Unit) {
        viewModelScope.coroutineContext.job.invokeOnCompletion {
            onDispose()
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun getSong(songId: Int, token: String, navController: NavController) {
        viewModelScope.launch {
            try {
                val call = songRepository.getSongById(songId)
                call.enqueue(object : Callback<SongResponse> {
                    override fun onResponse(call: Call<SongResponse>, res: Response<SongResponse>) {
                        if (res.isSuccessful) {
                            val song = res.body()?.data
                            if (song?.fileName.isNullOrBlank()) {
                                Log.e("APIResponseError", "Received invalid song URL")
                            } else {
                                if (song != null) {
                                    _song.value = song
                                }
                                initializeAudio(token, navController)
                            }
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

    fun updateIsPlaying() {
        if (_isPlaying.value
        ) {
            this.audio.pause()
        } else {
            this.audio.start()
        }
        _isPlaying.value = !_isPlaying.value

    }

    @SuppressLint("NewApi")
    fun initializeAudio(token: String, navController: NavController) {
        this.audio = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(_song.value.fileName)
            setOnErrorListener { mp, what, extra ->
                Log.e("MediaPlayerError", "Error occurred: $what, Extra: $extra")
                false
            }
            setOnPreparedListener {

                _isPlaying.value = false
            }
            prepareAsync()
        }
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
                call.enqueue(object : Callback<AttemptSpeakingResponse> {
                    override fun onResponse(
                        call: Call<AttemptSpeakingResponse>,
                        res: Response<AttemptSpeakingResponse>
                    ) {
                        if (res.isSuccessful) {
                            _answerResponse.value = res.body()!!
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
    fun getVariants(token: String, songId: Int, type: String, navController: NavController) {
        viewModelScope.launch {
            try {
                val call = variantRepository.getVariants(token, songId, type)
                call.enqueue(object : Callback<VariantListResponse> {
                    override fun onResponse(
                        call: Call<VariantListResponse>,
                        res: Response<VariantListResponse>
                    ) {
                        if (res.isSuccessful) {
                            _variants.value = res.body()!!.data
                            if (_variants.value.isNotEmpty()) {
                                randomizedVariants()
                            } else {
                                navController.popBackStack()
                            }
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
    override fun onCleared() {
        super.onCleared()
        speechRecognizer?.destroy()
        audio.release()
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

    fun resetViewModel() {
        _recognizedText.value = ""
        _variants.value = emptyList()
        _variant.value = VariantModel()
        _answerResponse.value = AttemptSpeakingResponse()
        _isAnswerProcessed.value = false
        _song.value = SongModel()
    }

}

