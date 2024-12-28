package com.yohana.echolearn.repositories

import com.yohana.echolearn.models.SongResponse
import com.yohana.echolearn.services.SongAPIService
import retrofit2.Call

interface SongRepository{
    fun getSongs(): Call<List<SongResponse>>
    fun getSongsByGenre(genre: String): Call<List<SongResponse>>
    fun getSongById(songId: Int): Call<SongResponse>
}

class NetworkSongRepository(
    private val songAPIService: SongAPIService
): SongRepository{
    override fun getSongs(): Call<List<SongResponse>> {
        return songAPIService.getSongs()
    }

    override fun getSongsByGenre(genre: String): Call<List<SongResponse>> {
        return songAPIService.getSongsByGenre(genre)
    }

    override fun getSongById(songId: Int): Call<SongResponse> {
        return songAPIService.getSongById(songId)
    }
}