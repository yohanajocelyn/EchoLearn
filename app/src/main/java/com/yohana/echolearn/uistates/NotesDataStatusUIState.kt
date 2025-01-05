package com.yohana.echolearn.uistates

import com.yohana.echolearn.models.NoteModel

sealed interface NotesDataStatusUIState {
    data class Success(val notesData: List<NoteModel>): NotesDataStatusUIState
    object Loading: NotesDataStatusUIState
    object Start: NotesDataStatusUIState
    data class Failed(val errorMessage: String): NotesDataStatusUIState
}