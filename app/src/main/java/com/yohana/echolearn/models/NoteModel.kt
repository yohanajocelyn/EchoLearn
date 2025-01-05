package com.yohana.echolearn.models

data class NoteModel(
    val id: Int = 0,
    val word: String = "",
    val meaning: String = ""
)

data class NoteResponse(
    val data: NoteModel = NoteModel(),
)

data class NoteListResponse(
    val data: List<NoteModel> = emptyList()
)