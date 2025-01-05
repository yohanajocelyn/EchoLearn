
package com.yohana.echolearn.uistates
import com.yohana.echolearn.models.NoteModel
sealed interface NoteDataStatusUIState {
    data class Success(val noteData: NoteModel): NoteDataStatusUIState
    object Loading: NoteDataStatusUIState
    object Start: NoteDataStatusUIState
    data class Failed(val errorMessage: String): NoteDataStatusUIState
}