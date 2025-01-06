package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.NoteListResponse
import com.yohana.echolearn.models.NoteResponse
import com.yohana.echolearn.services.NoteAPIService
import retrofit2.Call

interface NoteRepository {
    fun getNotes(token: String, username: String): Call<NoteListResponse>
    fun getNote(token: String, noteId: Int, username: String): Call<NoteResponse>
    fun createNote(token: String, username: String, word: String, meaning: String): Call<String>
    fun updateNote(
        token: String,
        noteId: Int,
        username: String,
        word: String,
        meaning: String
    ): Call<String>

    fun deleteNote(
        token: String, noteId: Int, username: String,
    ): Call<String>
}

class NetworkNoteRepository(
    private val noteAPIService: NoteAPIService
) : NoteRepository {
    override fun getNotes(token: String, username: String): Call<NoteListResponse> {
        return noteAPIService.getNotes(token, username)
    }

    override fun getNote(token: String, noteId: Int, username: String): Call<NoteResponse> {
        return noteAPIService.getNote(token, noteId, username)
    }

    override fun createNote(
        token: String,
        username: String,
        word: String,
        meaning: String
    ): Call<String> {
        val createMap = HashMap<String, String>()
        createMap["word"] = word
        createMap["meaning"] = meaning
        return noteAPIService.createNote(token, username, createMap)
    }

    override fun updateNote(
        token: String,
        noteId: Int,
        username: String,
        word: String,
        meaning: String
    ): Call<String> {
        val updateMap = HashMap<String, String>()
        updateMap["word"] = word
        updateMap["meaning"] = meaning
        return noteAPIService.updateNote(token, noteId, username, updateMap)
    }

    override fun deleteNote(
        token: String, noteId: Int, username: String,
    ): Call<String> {
        return noteAPIService.deleteNote(token, noteId, username)
    }
}