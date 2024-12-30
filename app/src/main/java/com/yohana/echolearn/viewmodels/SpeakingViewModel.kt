package com.yohana.echolearn.viewmodels

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.yohana.echolearn.repositories.UserRepository

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
class SpeakingViewModel(  private val userRepository: UserRepository): ViewModel() {
    private val _talk = MutableStateFlow("Speech text should come here")
    val talk: StateFlow<String> = _talk

    fun askSpeechInput(context: Context, onSpeechStarted: (Intent) -> Unit) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
                )
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US)
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Talk Something")
            }
            onSpeechStarted(intent)
        }
    }

//    fun handleSpeechResult(result: String) {
//        viewModelScope.launch {
//            _talk.value = result?.get(0).orEmpty()
//        }
//    }
}