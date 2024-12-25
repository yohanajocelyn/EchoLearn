package com.yohana.echolearn.models

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val errorMessage: String = "",
    val isPasswordVisible: Boolean = false
)