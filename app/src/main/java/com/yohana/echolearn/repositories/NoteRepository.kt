package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.NoteListResponse
import com.yohana.echolearn.models.NoteResponse
import com.yohana.echolearn.services.NoteAPIService
import retrofit2.Call

interface NoteRepository{
    fun getNotes(token: String): Call<NoteListResponse>
    fun getNote(token: String, noteId: Int): Call<NoteResponse>
    fun createNote(token: String, word: String, meaning: String): Call<String>
    fun updateNote(token: String, noteId: Int, word: String, meaning: String): Call<String>
    fun deleteNote(token: String, noteId: Int): Call<String>
}

class NetworkNoteRepository(
    private val noteAPIService: NoteAPIService
): NoteRepository {
    override fun getNotes(token: String): Call<NoteListResponse> {
        return noteAPIService.getNotes(token)
    }

    override fun getNote(token: String, noteId: Int): Call<NoteResponse> {
        return noteAPIService.getNote(token, noteId)
    }

    override fun createNote(token: String, word: String, meaning: String): Call<String> {
        val createMap = HashMap<String, String>()

        createMap["word"] = word
        createMap["meaning"] = meaning

        return noteAPIService.createNote(token, createMap)
    }

    override fun updateNote(
        token: String,
        noteId: Int,
        word: String,
        meaning: String
    ): Call<String> {
        val updateMap = HashMap<String, String>()

        updateMap["word"] = word
        updateMap["meaning"] = meaning

        return noteAPIService.updateNote(token, noteId, updateMap)
    }

    override fun deleteNote(token: String, noteId: Int): Call<String> {
        return noteAPIService.deleteNote(token, noteId)
    }

}