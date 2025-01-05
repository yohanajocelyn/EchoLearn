package com.yohana.echolearn.services

import com.yohana.echolearn.models.NoteListResponse
import com.yohana.echolearn.models.NoteResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteAPIService {
    @GET("api/notes")
    fun getNotes(
        @Header("X-API-TOKEN") token: String
    ): Call<NoteListResponse>

    @GET("api/note/{noteId}")
    fun getNote(
        @Header("X-API-TOKEN") token: String,
        @Path("noteId") noteId: Int
    ): Call<NoteResponse>

    @POST("api/note")
    fun createNote(
        @Header("X-API-TOKEN") token: String,
        @Body createMap: HashMap<String, String>
    ): Call<String>

    @PUT("api/note/{noteId}")
    fun updateNote(
        @Header("X-API-TOKEN") token: String,
        @Path("noteId") noteId: Int,
        @Body updateMap: HashMap<String, String>
    ): Call<String>

    @DELETE("api/note/{noteId}")
    fun deleteNote(
        @Header("X-API-TOKEN") token: String,
        @Path("noteId") noteId: Int
    ): Call<String>
}