package com.yohana.echolearn.models

import android.content.Context
import android.media.MediaPlayer

data class ListeningUIState (
    val context: Context,
    val isPlaying: Boolean = false,
    val audio: MediaPlayer,
    val userAnswers: List<String> = emptyList(),
    val blankPositions: List<Int> = emptyList()
)