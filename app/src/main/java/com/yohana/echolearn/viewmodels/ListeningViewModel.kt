package com.yohana.echolearn.viewmodels

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.text.Editable.Factory
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.gson.Gson
import com.yohana.echolearn.EchoLearnApplication
import com.yohana.echolearn.R
import com.yohana.echolearn.models.ErrorModel
import com.yohana.echolearn.models.SongModel
import com.yohana.echolearn.models.SongResponse
import com.yohana.echolearn.models.VariantListResponse
import com.yohana.echolearn.models.VariantModel
import com.yohana.echolearn.repositories.SongRepository
import com.yohana.echolearn.repositories.VariantRepository
import com.yohana.echolearn.uistates.ListeningUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ListeningViewModel(
    private val songRepository: SongRepository,
    private val variantRepository: VariantRepository
): ViewModel() {

    private val _song = MutableStateFlow<SongModel>(SongModel())
    val song: StateFlow<SongModel> = _song

    private val _variant = MutableStateFlow<VariantModel>(VariantModel())
    val variant: StateFlow<VariantModel> = _variant

    private val _listeningUIState = MutableStateFlow<ListeningUIState>(ListeningUIState())
    val listeningUIState: StateFlow<ListeningUIState> = _listeningUIState

    var isPlaying: Boolean by mutableStateOf(false)
        private set

    var lines: List<String> by mutableStateOf(emptyList())
        private set

    var userAnswers: List<String> by mutableStateOf(emptyList())
        private set

    var blankPositions: List<Int> by mutableStateOf(emptyList())
        private set

    var answers: List<String> by mutableStateOf(emptyList())
        private set

    var audio: MediaPlayer by mutableStateOf(MediaPlayer())
        private set

    fun setSong(songId: Int){
        viewModelScope.launch {
            try {
                val call = songRepository.getSongById(songId)
                call.enqueue(object: Callback<SongResponse> {
                    override fun onResponse(
                        call: Call<SongResponse>,
                        res: Response<SongResponse>
                    ) {
                        if (res.isSuccessful) {
                            _song.value = res.body()!!.data
                            initializeAudio()
                        }else{
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
            }catch (error: IOException) {
                Log.d("get-error", "GET ERROR: ${error.localizedMessage}")
            }
        }
    }

    fun setVariants(songId: Int, type: String){
        viewModelScope.launch {
            try{
                val call = variantRepository.getVariants(songId, type)
                call.enqueue(object: Callback<VariantListResponse>{
                    override fun onResponse(
                        call: Call<VariantListResponse>,
                        res: Response<VariantListResponse>
                    ) {
                        if(res.isSuccessful){
                            val variants: List<VariantModel> = res.body()!!.data
                            val randomVariant = variants.random()
                            _variant.value = randomVariant
                            initializeData()
                        }else{
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
            }catch (error: IOException){
                Log.d("get-error", "GET ERROR: ${error.localizedMessage}")
            }
        }
    }

    fun initializeAudio(){
        this.audio = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(_song.value.fileName) // Use the actual remote URL
            setOnErrorListener { mp, what, extra ->
                Log.e("MediaPlayerError", "Error occurred: $what, Extra: $extra")
                true
            }
            prepareAsync() // Use async preparation for remote URLs
        }
    }

    fun initializeData() {
        // Split the lyrics correctly using "\n"
        this.lines = _variant.value.emptyLyric.split("\n")

        // Split the answers by commas (assuming format is correct)
        this.answers = _variant.value.answer.split(", ")

        // Initialize userAnswers list with empty strings
        this.userAnswers = List(answers.size) { "" }

        var counter = 0
        val positions: MutableList<Int> = mutableListOf()

        // Identify positions of blanks and store them
        this.lines.forEach { line ->
            line.split(" ").forEach { word ->
                if (word.contains("_")) {
                    positions.add(counter)
                }
                counter++
            }
        }

        // Set blank positions
        this.blankPositions = positions
    }

    fun processLyrics(): List<LineElement>{
        var wordCounter = 0

        return this.lines.map { line ->
            val words = line.split(" ").map { word ->
                if (word.contains("_")) {
                    val blankIndex = blankPositions.indexOf(wordCounter)
                    wordCounter++
                    TextElement.Blank(blankIndex)
                } else {
                    val text = if (word.startsWith("_")) word.substring(1) else word
                    wordCounter++
                    TextElement.Regular(text)
                }
            }
            LineElement(words)
        }
    }

    fun updateUserAnswer(index: Int, answer: String){
        this.userAnswers = this.userAnswers.toMutableList().apply {
            set(index, answer)
        }
    }

    fun updateIsPlaying(){
        isPlaying = !isPlaying
    }

    fun setShowDialog(){
        _listeningUIState.update { currentState ->
            if(currentState.showSaveDialog) {
                currentState.copy(
                    showSaveDialog = false
                )
            }else{
                currentState.copy(
                    showSaveDialog = true
                )
            }
        }
    }

    fun saveProgress(){

    }

    data class LineElement(val words: List<TextElement>)
    sealed class TextElement{
        data class Blank(val index: Int): TextElement()
        data class Regular(val text: String): TextElement()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EchoLearnApplication)
                val songRepository = application.container.songRepository
                val variantRepository = application.container.variantRepository
                ListeningViewModel(songRepository, variantRepository)
            }
        }
    }
}