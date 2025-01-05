package com.yohana.echolearn.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yohana.echolearn.EchoLearnApplication
import com.yohana.echolearn.repositories.UserRepository

class UpdateProfileViewModel(userRepository: UserRepository): ViewModel() {
    var emailInput: String by mutableStateOf("")
        private set

    var usernameInput: String by mutableStateOf("")
        private set

    var passwordInput: String by mutableStateOf("")
        private set
    fun setEmail(email: String) {
        this.emailInput = email
    }

    fun setUsername(username: String) {
        this.usernameInput = username
    }

    fun setPassword(password: String) {
        this.passwordInput = password
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EchoLearnApplication)
                val userRepository = application.container.userRepository


             UpdateProfileViewModel(userRepository)
            }
        }
    }
}