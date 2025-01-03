package com.yohana.echolearn.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.google.gson.Gson
import com.yohana.echolearn.EchoLearnApplication
import com.yohana.echolearn.R
import com.yohana.echolearn.uistates.AuthenticationStatusUIState
import com.yohana.echolearn.uistates.AuthenticationUIState
import com.yohana.echolearn.models.ErrorModel
import com.yohana.echolearn.models.UserResponse
import com.yohana.echolearn.repositories.AuthenticationRepository
import com.yohana.echolearn.repositories.UserRepository
import com.yohana.echolearn.route.PagesEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AuthenticationViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _authenticationUIState = MutableStateFlow<AuthenticationUIState>(
        AuthenticationUIState()
    )
    val authenticationUIState: StateFlow<AuthenticationUIState> = _authenticationUIState

    var dataStatus: AuthenticationStatusUIState by mutableStateOf(AuthenticationStatusUIState.Start)
        private set

    var emailInput: String by mutableStateOf("")
        private set

    var usernameInput: String by mutableStateOf("")
        private set

    var passwordInput: String by mutableStateOf("")
        private set

    var confirmPasswordInput: String by mutableStateOf("")
        private set

    var profilePictureInput: String by mutableStateOf("")
        private set

    var defaultProfilePictures: List<String> by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        fetchDefaultProfilePictures()
    }

    private fun fetchDefaultProfilePictures() {
        viewModelScope.launch {
            try {
                defaultProfilePictures = authenticationRepository.getDefaultProfilePictures()
            } catch (e: Exception) {
                // Handle error (e.g., log or show a message)
                e.printStackTrace()
            }
        }
    }

    fun setEmail(email: String) {
        this.emailInput = email
    }

    fun setUsername(username: String) {
        this.usernameInput = username
    }

    fun setPassword(password: String) {
        this.passwordInput = password
    }

    fun setConfirmPassword(confirmPassword: String) {
        this.confirmPasswordInput = confirmPassword
    }

    fun setProfilePicture(profilePicture: String) {
        this.profilePictureInput = profilePicture
    }

    fun setPasswordVisibility() {
        _authenticationUIState.update { currentState ->
            if (currentState.showPassword) {
                currentState.copy(
                    showPassword = false,
                    passwordVisibility = PasswordVisualTransformation(),
                    passwordVisibilityIcon = R.drawable.ic_eye
                )
            } else {
                currentState.copy(
                    showPassword = true,
                    passwordVisibility = VisualTransformation.None,
                    passwordVisibilityIcon = R.drawable.ic_visibility_off
                )
            }
        }
    }

    fun setConfirmPasswordVisibility() {
        _authenticationUIState.update { currentState ->
            if (currentState.showConfirmPassword) {
                currentState.copy(
                    showConfirmPassword = false,
                    confirmPasswordVisibility = PasswordVisualTransformation(),
                    confirmPasswordVisibilityIcon = R.drawable.ic_eye
                )
            } else {
                currentState.copy(
                    showConfirmPassword = true,
                    confirmPasswordVisibility = VisualTransformation.None,
                    confirmPasswordVisibilityIcon = R.drawable.ic_visibility_off
                )
            }
        }
    }

    fun resetViewModel() {
        setEmail("")
        setUsername("")
        setPassword("")
        setConfirmPassword("")
        _authenticationUIState.update { currentState ->
            currentState.copy(
                showConfirmPassword = false,
                showPassword = false,
                passwordVisibility = PasswordVisualTransformation(),
                confirmPasswordVisibility = PasswordVisualTransformation(),
                passwordVisibilityIcon = R.drawable.ic_eye,
                confirmPasswordVisibilityIcon = R.drawable.ic_eye,
                buttonEnabled = false
            )
        }
        dataStatus = AuthenticationStatusUIState.Start
    }

    fun clearErrorMessage() {
        dataStatus = AuthenticationStatusUIState.Start
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EchoLearnApplication)
                val authenticationRepository = application.container.authenticationRepository
                val userRepository = application.container.userRepository
                AuthenticationViewModel(
                    authenticationRepository = authenticationRepository,
                    userRepository = userRepository
                )
            }
        }
    }

    fun registerUser(navController: NavController) {
        viewModelScope.launch {
            dataStatus = AuthenticationStatusUIState.Loading

            try {
                val call = authenticationRepository.register(
                    usernameInput,
                    emailInput,
                    passwordInput,
                    profilePictureInput
                )
                call.enqueue(object : Callback<UserResponse> {
                    override fun onResponse(call: Call<UserResponse>, res: Response<UserResponse>) {
                        if (res.isSuccessful) {
                            Log.d("response-data", "RESPONSE DATA: ${res.body()}")
                            dataStatus = AuthenticationStatusUIState.Success(res.body()!!.data)

                            resetViewModel()

                            navController.navigate(PagesEnum.Home.name) {
                                popUpTo(PagesEnum.Register.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            Log.e("ErrorBody", res.errorBody()?.string() ?: "No error body")

                            //ambil error messagenya
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            dataStatus = AuthenticationStatusUIState.Failed(errorMessage.errorMessage)
                        }
                    }

                    override fun onFailure(p0: Call<UserResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        dataStatus = AuthenticationStatusUIState.Failed(t.localizedMessage)
                    }
                })

            } catch (error: IOException) {
                dataStatus = AuthenticationStatusUIState.Failed(error.localizedMessage)
                Log.d("register-error", "REGISTER ERROR: ${error.localizedMessage}")
            }
        }
    }

    fun saveUsernameToken(username: String, token: String) {
        viewModelScope.launch {
            userRepository.saveUserToken(token)
            userRepository.saveUsername(username)
        }
    }

    fun loginUser(navController: NavController) {
        viewModelScope.launch {
            dataStatus = AuthenticationStatusUIState.Loading

            try {
                val call = authenticationRepository.login(emailInput, passwordInput)
                call.enqueue(object : Callback<UserResponse> {
                    override fun onResponse(call: Call<UserResponse>, res: Response<UserResponse>) {
                        if (res.isSuccessful){
                            saveUsernameToken(
                                token = res.body()!!.data.token!!,
                                username = res.body()!!.data.username!!
                            )

                            dataStatus = AuthenticationStatusUIState.Success(res.body()!!.data)

                            resetViewModel()

                            navController.navigate(PagesEnum.Home.name) {
                                popUpTo(PagesEnum.Login.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            //ambil error messagenya
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            dataStatus = AuthenticationStatusUIState.Failed(errorMessage.errorMessage)
                        }
                    }

                    override fun onFailure(p0: Call<UserResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        dataStatus = AuthenticationStatusUIState.Failed(t.localizedMessage)
                    }
                })

            } catch (error: IOException) {
                dataStatus = AuthenticationStatusUIState.Failed(error.localizedMessage)
                Log.d("register-error", "REGISTER ERROR: ${error.localizedMessage}")
            }
        }
    }
}