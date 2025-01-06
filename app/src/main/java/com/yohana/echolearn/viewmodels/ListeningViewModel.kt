package com.yohana.echolearn.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.text.Editable.Factory
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.yohana.echolearn.models.AttemptDetail
import com.yohana.echolearn.models.AttemptModel
import com.yohana.echolearn.models.AttemptResponse
import com.yohana.echolearn.models.AttemptSongDetail
import com.yohana.echolearn.models.AttemptVariantDetail
import com.yohana.echolearn.models.ErrorModel
import com.yohana.echolearn.models.GeneralResponseModel
import com.yohana.echolearn.models.SongModel
import com.yohana.echolearn.models.SongResponse
import com.yohana.echolearn.models.VariantListResponse
import com.yohana.echolearn.models.VariantModel
import com.yohana.echolearn.models.VariantResponse
import com.yohana.echolearn.repositories.AttemptRepository
import com.yohana.echolearn.repositories.SongRepository
import com.yohana.echolearn.repositories.VariantRepository
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.uistates.AttemptDataStatusUIState
import com.yohana.echolearn.uistates.ListeningUIState
import com.yohana.echolearn.uistates.StringDataStatusUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ListeningViewModel(
    private val songRepository: SongRepository,
    private val variantRepository: VariantRepository,
    private val attemptRepository: AttemptRepository
): ViewModel() {

    private val _song = MutableStateFlow<SongModel>(SongModel())
    val song: StateFlow<SongModel> = _song

    private val _variant = MutableStateFlow<VariantModel>(VariantModel())
    val variant: StateFlow<VariantModel> = _variant

    private val _attempt = MutableStateFlow<AttemptModel>(AttemptModel())
    val attempt: StateFlow<AttemptModel> = _attempt

    var createStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

    var attemptDataStatus: AttemptDataStatusUIState by mutableStateOf(AttemptDataStatusUIState.Start)
        private set

    var showSaveDialog: Boolean by mutableStateOf(false)
        private set

    var isContinue: Boolean by mutableStateOf(false)
        private set

    var isPlaying: Boolean by mutableStateOf(false)
        private set

    var isLoading: Boolean by mutableStateOf(true)
        private set

    var lines: List<String> by mutableStateOf(emptyList())
        private set

    var userAnswers: List<String> by mutableStateOf(emptyList())
        private set

    var blankPositions: List<Int> by mutableStateOf(emptyList())
        private set

    var answers: List<String> by mutableStateOf(emptyList())
        private set

    var audio: MediaPlayer by mutableStateOf(MediaPlayer())
        private set

    data class LineElement(val words: List<TextElement>)
    sealed class TextElement{
        data class Blank(val index: Int): TextElement()
        data class Regular(val text: String): TextElement()
    }

    fun initializeViewModel(
        songId: Int,
        token: String,
        navController: NavController,
        type: String,
        attemptId: Int,
        isContinue: Boolean
    ){
        this.isContinue = isContinue
        viewModelScope.launch {
            try {
                if(isContinue) {
                    setAttempt(token, attemptId, navController)
                } else {
                    setSong(songId, token, navController)
                    setVariants(token, songId, type)
                }
            } catch(e: Exception) {
                Log.e("ViewModel", "Error loading data", e)
            } finally {
                isLoading = false
            }
        }
    }

    @SuppressLint("NewApi")
    fun finishExercise(token: String, navController: NavController){
        if(isContinue){
            updateProgress(
                token = token,
                navController = navController,
                isCompleted = true
            )
        }else{
            saveProgress(
                token = token,
                navController = navController,
                isCompleted = true
            )
        }
    }

    fun setSong(songId: Int, token: String, navController: NavController){
        viewModelScope.launch {
            try {
                val call = songRepository.getSongById(songId)
                call.enqueue(object: Callback<SongResponse> {
                    override fun onResponse(
                        call: Call<SongResponse>,
                        res: Response<SongResponse>
                    ) {
                        if (res.isSuccessful) {
                            _song.value = res.body()!!.data
                            initializeAudio(token, navController)
                        }else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                        }
                    }

                    override fun onFailure(p0: Call<SongResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                    }
                })
            }catch (error: IOException) {
                Log.d("get-error", "GET ERROR: ${error.localizedMessage}")
            }
        }
    }

    fun setVariants(token: String, songId: Int, type: String, variantId: Int = 0){
        viewModelScope.launch {
            try{
                if(variantId != 0){
                    val call = variantRepository.getVariantById(token = token, variantId = _attempt.value.variantId)
                    call.enqueue(object: Callback<VariantResponse>{
                        override fun onResponse(
                            call: Call<VariantResponse>,
                            res: Response<VariantResponse>
                        ) {
                            if(res.isSuccessful){
                                _variant.value = res.body()!!.data
                                initializeData()
                            }else{
                                val errorMessage = Gson().fromJson(
                                    res.errorBody()!!.charStream(),
                                    ErrorModel::class.java
                                )
                                Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            }
                        }

                        override fun onFailure(p0: Call<VariantResponse>, t: Throwable) {
                            Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        }

                    })
                }else{
                    val call = variantRepository.getVariants(token, songId, type)
                    call.enqueue(object: Callback<VariantListResponse>{
                        override fun onResponse(
                            call: Call<VariantListResponse>,
                            res: Response<VariantListResponse>
                        ) {
                            if(res.isSuccessful){
                                val variants: List<VariantModel> = res.body()!!.data
                                val randomVariant = variants.random()
                                _variant.value = randomVariant
                                initializeData()
                            }else{
                                val errorMessage = Gson().fromJson(
                                    res.errorBody()!!.charStream(),
                                    ErrorModel::class.java
                                )
                                Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            }
                        }

                        override fun onFailure(p0: Call<VariantListResponse>, t: Throwable) {
                            Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        }

                    })
                }

            }catch (error: IOException){
                Log.d("get-error", "GET ERROR: ${error.localizedMessage}")
            }
        }
    }

    fun setAttempt(token: String, attemptId: Int, navController: NavController){
        viewModelScope.launch {
            try {
                attemptDataStatus = AttemptDataStatusUIState.Loading

                val call = attemptRepository.getAttempt(token, attemptId)
                call.enqueue(object: Callback<AttemptResponse>{
                    override fun onResponse(call: Call<AttemptResponse>, res: Response<AttemptResponse>) {
                        if(res.isSuccessful){
                            res.body()!!.data.let { attempt ->
                                val processedAttempt = AttemptModel(
                                    id = attempt.id,
                                    userId = attempt.userId,
                                    variantId = attempt.variantId,
                                    correctAnswer = attempt.correctAnswer,
                                    attemptedAnswer = attempt.attemptedAnswer,
                                    score = attempt.score,
                                    attemptedAt = attempt.attemptedAt, // Assuming attemptedAt is in the correct Date format
                                    isComplete = attempt.isComplete,
                                    song = attempt.song,
                                    variant = attempt.variant
                                )
                                _attempt.value = processedAttempt
                                _song.value = attempt.song!!
                                _variant.value = attempt.variant!!
                            }
                            initializeAudio(token = token, navController = navController)
                            initializeData()

                            attemptDataStatus = AttemptDataStatusUIState.Success(_attempt.value)
                        }else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            attemptDataStatus = AttemptDataStatusUIState.Failed(errorMessage.errorMessage)
                        }
                    }

                    override fun onFailure(p0: Call<AttemptResponse>, t: Throwable) {
                        attemptDataStatus = AttemptDataStatusUIState.Failed(t.localizedMessage!!)
                    }
                })
            }catch (error: IOException){
                attemptDataStatus = AttemptDataStatusUIState.Failed(error.localizedMessage!!)
            }
        }
    }

    @SuppressLint("NewApi")
    fun initializeAudio(token: String, navController: NavController){
        this.audio = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(_song.value.fileName) // Use the actual remote URL
            setOnErrorListener { mp, what, extra ->
                Log.e("MediaPlayerError", "Error occurred: $what, Extra: $extra")
                true
            }
            prepareAsync()
            setOnCompletionListener {
                if(isContinue){
                    updateProgress(
                        token = token,
                        navController = navController,
                        isCompleted = true
                    )
                }else{
                    saveProgress(
                        token = token,
                        navController = navController,
                        isCompleted = true
                    )
                }
            }
        }
    }

    fun initializeData() {
        this.lines = _variant.value.emptyLyric.split("\n")

        this.answers = _variant.value.answer.split(", ")

        if (this.isContinue){
            this.userAnswers = _attempt.value.attemptedAnswer.split(", ")
            this.userAnswers = this.userAnswers.map { if (it == " ") "" else it }
        }else{
            this.userAnswers = List(answers.size) { "" }
        }

        var counter = 0
        val positions: MutableList<Int> = mutableListOf()

        // Identify positions of blanks and store them
        this.lines.forEach { line ->
            line.split(" ").forEach { word ->
                if (word.contains("_")) {
                    positions.add(counter)
                }
                counter++
            }
        }
        this.blankPositions = positions
    }

    fun processLyrics(): List<LineElement>{
        var wordCounter = 0

        return this.lines.map { line ->
            val words = line.split(" ").map { word ->
                if (word.contains("_")) {
                    val blankIndex = blankPositions.indexOf(wordCounter)
                    wordCounter++
                    TextElement.Blank(blankIndex)
                } else {
                    val text = if (word.startsWith("_")) word.substring(1) else word
                    wordCounter++
                    TextElement.Regular(text)
                }
            }
            LineElement(words)
        }
    }

    fun updateUserAnswer(index: Int, answer: String){
        this.userAnswers = this.userAnswers.toMutableList().apply {
            set(index, answer)
        }
    }

    fun updateIsPlaying(){
        if(isPlaying){
            this.audio.pause()
        }else{
            this.audio.start()
        }
        isPlaying = !isPlaying
    }

    fun setShowDialog(){
        showSaveDialog = !showSaveDialog
    }

    fun returnWithoutSaving(navController: NavController){
        navController.popBackStack()
        resetViewModel()
    }

    private fun calculateScore(): Int{
        var counter = 0

        answers.forEachIndexed { index, answer ->
            if(userAnswers[index].equals(answer, ignoreCase = true)){
                counter++
            }
        }

        return (counter * (100/answers.size))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveProgress(token: String, navController: NavController, isCompleted: Boolean){
        viewModelScope.launch {
            createStatus = StringDataStatusUIState.Loading

            Log.d("attempt-creation", "TOKEN: ${token}")
            var score: String
            val isComplete: String
            userAnswers = userAnswers.map { if (it.isEmpty()) " " else it }
            val attemptedAnswer: String = userAnswers.joinToString(", ")
            var correctAnswer: String = answers.joinToString(", ")
            if(isCompleted){
                score = calculateScore().toString()
                isComplete = "true"
            }else{
                score = "0"
                isComplete = "false"
            }
            val attemptedAt: String = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

            try{
                val call = attemptRepository.createAttempt(
                    token = token,
                    variantId = _variant.value.id.toString(),
                    correctAnswer = correctAnswer,
                    attemptedAnswer = attemptedAnswer,
                    score = score,
                    attemptedAt = attemptedAt,
                    isComplete = isComplete
                )

                call.enqueue(object: Callback<GeneralResponseModel>{
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        res: Response<GeneralResponseModel>
                    ) {
                        if(res.isSuccessful){
                            Log.d("json", "JSON RESPONSE: ${res.body()!!.data}")
                            createStatus = StringDataStatusUIState.Success(res.body()!!.data)

                            if(isCompleted){
                                navController.navigate(PagesEnum.Lyrics.name+"/${_song.value.id}"){
                                    if (isContinue){
                                        popUpTo(PagesEnum.Listening.name + "continue-attempt/{attemptId}") {
                                            inclusive = true
                                        }
                                    }
                                }
                                resetViewModel()
                            }else{
                                navController.popBackStack()
                            }

                            resetViewModel()
                        }else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            createStatus = StringDataStatusUIState.Failed(errorMessage.errorMessage)
                        }
                    }

                    override fun onFailure(p0: Call<GeneralResponseModel>, t: Throwable) {
                        createStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            }catch(error: IOException){
                createStatus = StringDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateProgress(token: String, navController: NavController, isCompleted: Boolean){
        viewModelScope.launch {
            try {
                createStatus = StringDataStatusUIState.Loading

                val score: String = calculateScore().toString()
                val isComplete: String = "true"
                userAnswers = userAnswers.map { if (it.isEmpty()) " " else it }
                val attemptedAnswer: String = userAnswers.joinToString(", ")
                val attemptedAt: String = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

                val call = attemptRepository.updateAttempt(
                    token = token,
                    attemptId = _attempt.value.id,
                    score = score,
                    attemptedAnswer = attemptedAnswer,
                    isComplete = isComplete,
                    attemptedAt = attemptedAt
                )
                call.enqueue(object: Callback<GeneralResponseModel>{
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        res: Response<GeneralResponseModel>
                    ) {
                        if(res.isSuccessful) {
                            createStatus = StringDataStatusUIState.Success(res.body()!!.data)

                            navController.navigate(PagesEnum.Lyrics.name+"/${_song.value.id}"){
                                popUpTo(PagesEnum.Listening.name + "continue-attempt/{attemptId}") {
                                    inclusive = true
                                }
                            }
                            resetViewModel()
                        }else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            createStatus = StringDataStatusUIState.Failed(errorMessage.errorMessage)
                        }
                    }

                    override fun onFailure(p0: Call<GeneralResponseModel>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        createStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            }catch(error: IOException){
                Log.d("error-data", "ERROR DATA: ${error.localizedMessage}")
                createStatus = StringDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun resetViewModel(){
        audio.reset()
        createStatus = StringDataStatusUIState.Start
        isPlaying = false
        lines = emptyList()
        userAnswers = emptyList()
        blankPositions = emptyList()
        answers = emptyList()
        showSaveDialog = false
        isContinue = false
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EchoLearnApplication)
                val songRepository = application.container.songRepository
                val variantRepository = application.container.variantRepository
                val attemptRepository = application.container.attemptRepository
                ListeningViewModel(songRepository, variantRepository, attemptRepository)
            }
        }
    }
}