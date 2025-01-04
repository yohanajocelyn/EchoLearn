package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.SearchSongRequest
import com.yohana.echolearn.models.SongListResponse
import com.yohana.echolearn.models.SongResponse
import com.yohana.echolearn.services.SongAPIService
import retrofit2.Call

interface SongRepository{
    fun getSongs(): Call<SongListResponse>
    fun getSongsByGenre(genre: String): Call<SongListResponse>
    fun getSongById(songId: Int): Call<SongResponse>
    fun searchSongs(token: String, query: String): Call<SongListResponse>
}

class NetworkSongRepository(
    private val songAPIService: SongAPIService
): SongRepository{
    override fun getSongs(): Call<SongListResponse> {
        return songAPIService.getSongs()
    }

    override fun getSongsByGenre(genre: String): Call<SongListResponse> {
        return songAPIService.getSongsByGenre(genre)
    }

    override fun getSongById(songId: Int): Call<SongResponse> {
        return songAPIService.getSongById(songId)
    }

    override fun searchSongs(token: String, query: String): Call<SongListResponse> {
        return songAPIService.searchSongs(token, query)
    }
}