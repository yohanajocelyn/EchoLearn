package com.yohana.echolearn.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.google.gson.Gson
import com.yohana.echolearn.EchoLearnApplication
import com.yohana.echolearn.models.ErrorModel
import com.yohana.echolearn.models.NoteModel
import com.yohana.echolearn.models.NoteResponse
import com.yohana.echolearn.repositories.NoteRepository
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.uistates.NoteDataStatusUIState
import com.yohana.echolearn.uistates.NotesDataStatusUIState
import com.yohana.echolearn.uistates.StringDataStatusUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class UpdateNoteViewModel(
    private val noteRepository: NoteRepository
): ViewModel() {
    private val _note = MutableStateFlow<NoteModel>(NoteModel())
    val note: StateFlow<NoteModel> = _note

    var dataStatus: NoteDataStatusUIState by mutableStateOf(NoteDataStatusUIState.Start)
        private set

    var stringStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

    fun getNote(token: String, id: Int){
        viewModelScope.launch {
            try {
                dataStatus = NoteDataStatusUIState.Loading

                val call = noteRepository.getNote(token, id)
                call.enqueue(object: Callback<NoteResponse>{
                    override fun onResponse(call: Call<NoteResponse>, res: Response<NoteResponse>) {
                        if (res.isSuccessful){
                            dataStatus = NoteDataStatusUIState.Success(res.body()!!.data)
                            _note.value = res.body()!!.data
                        }else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            dataStatus = NoteDataStatusUIState.Failed(errorMessage.errorMessage)
                        }
                    }

                    override fun onFailure(p0: Call<NoteResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        dataStatus = NoteDataStatusUIState.Failed(t.localizedMessage!!)
                    }

                })
            }catch (error: IOException){
                Log.d("error-data", "ERROR DATA: ${error.localizedMessage}")
                dataStatus = NoteDataStatusUIState.Failed(error.localizedMessage!!)
            }
        }
    }

    fun updateNote(token: String, id: Int, word: String, meaning: String, navController: NavController){
        viewModelScope.launch {
            try {
                stringStatus = StringDataStatusUIState.Loading
                val call = noteRepository.updateNote(token, id, word, meaning)
                call.enqueue(object: Callback<String>{
                    override fun onResponse(call: Call<String>, res: Response<String>) {
                        if (res.isSuccessful){
                            stringStatus = StringDataStatusUIState.Success(res.body()!!)

                            navController.navigate(PagesEnum.Notes.name){
                                popUpTo(PagesEnum.UpdateNote.name) {
                                    inclusive = true
                                }
                            }
                        }else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            stringStatus = StringDataStatusUIState.Failed(errorMessage.errorMessage)
                        }
                    }

                    override fun onFailure(p0: Call<String>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        stringStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            }catch (error:IOException){
                Log.d("error-data", "ERROR DATA: ${error.localizedMessage}")
                stringStatus = StringDataStatusUIState.Failed(error.localizedMessage!!)
            }
        }
    }

    fun createNote(token: String, word: String, meaning: String, navController: NavController){
        viewModelScope.launch {
            try {
                stringStatus = StringDataStatusUIState.Loading
                val call = noteRepository.createNote(token, word, meaning)
                call.enqueue(object: Callback<String>{
                    override fun onResponse(call: Call<String>, res: Response<String>) {
                        if (res.isSuccessful){
                            stringStatus = StringDataStatusUIState.Success(res.body()!!)

                            navController.navigate(PagesEnum.Notes.name){
                                popUpTo(PagesEnum.UpdateNote.name) {
                                    inclusive = true
                                }
                            }
                        }else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            stringStatus = StringDataStatusUIState.Failed(errorMessage.errorMessage)
                        }
                    }

                    override fun onFailure(p0: Call<String>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        stringStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            }catch (error: IOException){
                Log.d("error-data", "ERROR DATA: ${error.localizedMessage}")
                stringStatus = StringDataStatusUIState.Failed(error.localizedMessage!!)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EchoLearnApplication)
                val noteRepository = application.container.noteRepository
                UpdateNoteViewModel(noteRepository = noteRepository)
            }
        }
    }
}