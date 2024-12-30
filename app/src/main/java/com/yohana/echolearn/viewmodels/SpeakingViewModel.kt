package com.yohana.echolearn.viewmodels

import android.app.Activity
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
    private val _recognizedText = MutableStateFlow("Speech text should come here")
    val recognizedText: StateFlow<String> get() = _recognizedText

    private var speechRecognizer: SpeechRecognizer? = null

    fun askSpeechInput(context: Context) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
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
}