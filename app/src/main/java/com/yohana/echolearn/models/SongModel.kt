package com.yohana.echolearn.models

data class SongModel (
    val id: Int,
    val artist: String,
    val fileName: String,
    val genre: String,
    val image: String,
    val lyrics: String,
    val title: String,
    val variants: List<VariantModel>
)

data class SongResponse(
    val data: SongModel
)