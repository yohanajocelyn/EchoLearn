package com.yohana.echolearn.models

// exhaustive (covers all scenario possibilities) interface
sealed interface AuthenticationStatusUIState {
    data class Success(val userModelData: UserModel): AuthenticationStatusUIState
    object Loading: AuthenticationStatusUIState
    object Start: AuthenticationStatusUIState
    data class Failed(val errorMessage: String): AuthenticationStatusUIState
}