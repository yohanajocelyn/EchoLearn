package com.yohana.echolearn.models

data class RegisterUIState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String = "",
    val isPasswordVisible: Boolean = false,
    val dataStatus: Boolean = false,
)