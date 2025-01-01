package com.yohana.echolearn.models

data class SongModel (
    val id: Int = 0,
    val artist: String = "",
    val fileName: String ="",
    val genre: String = "",
    val image: String = "",
    val lyrics: String = "",
    val title: String = "",
    val variants: List<VariantModel> = emptyList()
)

data class SongResponse(
    val data: SongModel
)

data class SongListResponse(
    val data: List<SongModel>
)

data class AttemptSongDetail(
    val title: String = "",
    val artist: String = "",
)