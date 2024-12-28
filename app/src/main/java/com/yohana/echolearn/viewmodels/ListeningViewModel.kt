package com.yohana.echolearn.viewmodels

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.yohana.echolearn.uistates.ListeningUIState
import kotlinx.coroutines.flow.MutableStateFlow

class ListeningViewModel: ViewModel() {


    var isPlaying: Boolean by mutableStateOf(false)
        private set

    var audioFilePath: String by mutableStateOf("")
        private set

    var userAnswers: List<String> by mutableStateOf(emptyList())
        private set

    var blankPositions: List<Int> by mutableStateOf(emptyList())
        private set

    var blankLyrics: String by mutableStateOf("")
        private set

    var answers: List<String> by mutableStateOf(emptyList())
        private set

    var song: MediaPlayer by mutableStateOf(MediaPlayer())
        private set

    var albumImagePath: String by mutableStateOf("")
        private set

    fun initializeSong(id: Int){
//        setAudio
        this.audioFilePath = "" //sambung dengan api

        this.song = MediaPlayer().apply {
            setDataSource(audioFilePath)
        }

//        setAlbumImage
        this.albumImagePath = "" //sambung dengan api

//        setBlankLyrics
//        setAnswers
//        setBlankPositions
    }
}