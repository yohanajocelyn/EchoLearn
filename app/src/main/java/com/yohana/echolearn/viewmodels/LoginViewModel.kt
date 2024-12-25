package com.yohana.echolearn.viewmodels

import androidx.lifecycle.ViewModel
import com.yohana.echolearn.models.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel: ViewModel(){
    private val _loginUIState = MutableStateFlow<LoginUIState>(LoginUIState())
    val loginUIState: StateFlow<LoginUIState> = _loginUIState

    init {
        loadValues()
    }

    fun loadValues(){
        _loginUIState.value = LoginUIState()
    }

    fun onEmailChange(email: String){
        _loginUIState.value = _loginUIState.value.copy(email = email)
    }

    fun onPasswordChange(password: String){
        _loginUIState.value = _loginUIState.value.copy(password = password)
    }

    fun setIsPasswordVisible(isVisible: Boolean){
        _loginUIState.value = _loginUIState.value.copy(isPasswordVisible = isVisible)
    }

}