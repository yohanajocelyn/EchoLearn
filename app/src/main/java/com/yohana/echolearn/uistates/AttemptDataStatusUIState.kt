package com.yohana.echolearn.uistates

import com.yohana.echolearn.models.AttemptModel

sealed interface AttemptDataStatusUIState {
    data class Success(val attemptData: AttemptModel): AttemptDataStatusUIState
    object Start: AttemptDataStatusUIState
    object Loading: AttemptDataStatusUIState
    data class Failed(val errorMessage: String): AttemptDataStatusUIState
}