package com.yohana.echolearn.services

import com.yohana.echolearn.models.SongListResponse
import com.yohana.echolearn.models.SongResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface SongAPIService {
    @GET("api/songs")
    fun getSongs(): Call<SongListResponse>

    @GET("api/songs/{genre}")
    fun getSongsByGenre(
        @Path("genre") genre: String
    ): Call<SongListResponse>

    @GET("api/songs/{songId}")
    fun getSongById(
        @Path("songId") songId: Int
    ): Call<SongResponse>
}