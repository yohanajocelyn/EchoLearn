package com.yohana.echolearn.viewmodels

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
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
import androidx.navigation.NavController
import com.google.gson.Gson
import com.yohana.echolearn.EchoLearnApplication
import com.yohana.echolearn.models.ErrorModel
import com.yohana.echolearn.models.SongModel
import com.yohana.echolearn.models.SongResponse
import com.yohana.echolearn.repositories.SongRepository
import com.yohana.echolearn.viewmodels.ListeningViewModel.LineElement
import com.yohana.echolearn.viewmodels.ListeningViewModel.TextElement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LyricsViewModel(
    private val songRepository: SongRepository
): ViewModel() {
    private val _song = MutableStateFlow<SongModel>(SongModel())
    val song: StateFlow<SongModel> = _song

    var isPlaying: Boolean by mutableStateOf(false)
        private set

    var audio: MediaPlayer by mutableStateOf(MediaPlayer())
        private set

    var lines: List<List<String>> by mutableStateOf(emptyList())
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
                            setLines()
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

    @SuppressLint("NewApi")
    fun initializeAudio(){
        this.audio = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(_song.value.fileName) // Use the actual remote URL
            setOnErrorListener { mp, what, extra ->
                Log.e("MediaPlayerError", "Error occurred: $what, Extra: $extra")
                true
            }
            prepareAsync()
            setOnCompletionListener {}
        }
    }

    fun updateIsPlaying(){
        if(isPlaying){
            this.audio.pause()
        }else{
            this.audio.start()
        }
        isPlaying = !isPlaying
    }

    fun setLines() {
        this.lines = _song.value.lyrics.split("\n").map { line ->
            val words = line.split(" ").map { word ->
                word.trim()
            }
            words
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EchoLearnApplication)
                val songRepository = application.container.songRepository
                LyricsViewModel(songRepository)
            }
        }
    }
}