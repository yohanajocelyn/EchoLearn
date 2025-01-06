package com.yohana.echolearn.models

data class VariantModel(
    val answer: String = "",
    val emptyLyric: String = "",
    val id: Int = 0,
    val type: String = "",
    val songId: Int = 0
)

data class VariantResponse(
    val data: VariantModel
)

data class VariantListResponse(
    val data: List<VariantModel>
)

data class AttemptVariantDetail(
    val type: String = ""
)

data class SpeakingRequest(
    val answer: String,
    val id : Int,
)
