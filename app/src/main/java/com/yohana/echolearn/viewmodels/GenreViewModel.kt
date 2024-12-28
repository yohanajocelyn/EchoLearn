package com.yohana.echolearn.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.gson.Gson
import com.yohana.echolearn.EchoLearnApplication
import com.yohana.echolearn.models.ErrorModel
import com.yohana.echolearn.models.SongListResponse
import com.yohana.echolearn.models.SongModel
import com.yohana.echolearn.models.SongResponse
import com.yohana.echolearn.repositories.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class GenreViewModel(
    private val songRepository: SongRepository
): ViewModel(){
    private val _songs = MutableStateFlow<List<SongModel>>(emptyList())
    val songs: StateFlow<List<SongModel>> = _songs

    fun setSongs(genre: String){
        viewModelScope.launch {
            try {
                val call = songRepository.getSongsByGenre(genre)
                call.enqueue(object: Callback<SongListResponse> {
                    override fun onResponse(
                        call: Call<SongListResponse>,
                        res: Response<SongListResponse>
                    ) {
                        if (res.isSuccessful) {
                            _songs.value = res.body()!!.data
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                        }
                    }

                    override fun onFailure(p0: Call<SongListResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                    }
                })
            }catch(error: IOException){
                Log.d("register-error", "REGISTER ERROR: ${error.localizedMessage}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EchoLearnApplication)
                val songRepository = application.container.songRepository
                GenreViewModel(songRepository)
            }
        }
    }
}

//class GenreViewModel(
//    private val songRepository: SongRepository
//): ViewModel() {
//    private val _songs = MutableStateFlow<List<SongModel>>(emptyList())
//    val songs: StateFlow<List<SongModel>> = _songs
//
//    fun setSongs(genre: String){
//        viewModelScope.launch {
//            try {
//                val call = songRepository.getSongsByGenre(genre)
//                call.enqueue(object: Callback<List<SongResponse>>{
//                    override fun onResponse(
//                        call: Call<List<SongResponse>>,
//                        res: Response<List<SongResponse>>
//                    ) {
//                        if (res.isSuccessful){
//                            val songResponses = res.body()!!
//                            _songs.value = songResponses.map { it.data }
//                        }else{
//                            val errorMessage = Gson().fromJson(
//                                res.errorBody()!!.charStream(),
//                                ErrorModel::class.java
//                            )
//                        }
//                    }
//
//                    override fun onFailure(p0: Call<List<SongResponse>>, t: Throwable) {
//                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
//                    }
//
//                })
//            }catch (error: IOException){
//                Log.d("register-error", "REGISTER ERROR: ${error.localizedMessage}")
//            }
//        }
//    }
//
//    companion object {
//        val Factory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application = (this[APPLICATION_KEY] as EchoLearnApplication)
//                val songRepository = application.container.songRepository
//                GenreViewModel(songRepository)
//            }
//        }
//    }
//}