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
import com.yohana.echolearn.models.AttemptDetail
import com.yohana.echolearn.models.ErrorModel
import com.yohana.echolearn.models.NoteListResponse
import com.yohana.echolearn.models.NoteModel
import com.yohana.echolearn.repositories.NoteRepository
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.uistates.NotesDataStatusUIState
import com.yohana.echolearn.uistates.StringDataStatusUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
class NoteViewModel(
    private val noteRepository: NoteRepository
): ViewModel() {

    private val _notes = MutableStateFlow<List<NoteModel>>(emptyList())
    val notes: StateFlow<List<NoteModel>> = _notes
    var dataStatus: NotesDataStatusUIState by mutableStateOf(NotesDataStatusUIState.Start)
        private set
    var stringStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set
    fun getNotes(token: String, username:String){
        viewModelScope.launch{
            try {
                dataStatus = NotesDataStatusUIState.Loading
                val call = noteRepository.getNotes(token, username)
                call.enqueue(object: Callback<NoteListResponse>{
                    override fun onResponse(
                        call: Call<NoteListResponse>,
                        res: Response<NoteListResponse>
                    ) {
                        if(res.isSuccessful){
                            _notes.value = res.body()!!.data
                            dataStatus = NotesDataStatusUIState.Success(res.body()!!.data)
                        }else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            dataStatus = NotesDataStatusUIState.Failed(errorMessage.errorMessage)
                        }
                    }
                    override fun onFailure(p0: Call<NoteListResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        dataStatus = NotesDataStatusUIState.Failed(t.localizedMessage!!)
                    }
                })
            }catch (error: IOException){
                Log.d("error-data", "ERROR DATA: ${error.localizedMessage}")
                dataStatus = NotesDataStatusUIState.Failed(error.localizedMessage!!)
            }
        }
    }
    fun deleteNote(token: String, id: Int, username: String){
        viewModelScope.launch {
            try {
                stringStatus = StringDataStatusUIState.Loading
                val call = noteRepository.deleteNote(token, id, username)
                call.enqueue(object: Callback<String>{
                    override fun onResponse(call: Call<String>, res: Response<String>) {
                        if(res.isSuccessful){
                            stringStatus = StringDataStatusUIState.Success(res.body()!!)
                            getNotes(
                                token = token,
                                username = username
                            )
                        }else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            stringStatus = StringDataStatusUIState.Failed(errorMessage.errorMessage)
                        }
                    }
                    override fun onFailure(call: Call<String>, t: Throwable) {
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
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EchoLearnApplication)
                val noteRepository = application.container.noteRepository
                NoteViewModel(noteRepository = noteRepository)
            }
        }
    }
}