package com.yohana.echolearn.services

import com.yohana.echolearn.models.SearchSongRequest
import com.yohana.echolearn.models.SongListResponse
import com.yohana.echolearn.models.SongResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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

    @GET("api/songs/search/{keyword}")
    fun searchSongs(
        @Header("X-API-TOKEN") token: String,
        @Path("keyword") keyword: String
    ): Call<SongListResponse>
}