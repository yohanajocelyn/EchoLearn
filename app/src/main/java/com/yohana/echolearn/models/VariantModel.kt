package com.yohana.echolearn.models

data class VariantModel(
    val answer: String = "",
    val emptyLyric: String ="",
    val id: Int =0,
    val type: String =""
)
data class VariantListResponse(
    val data: List<VariantModel>
)


data class SpeakingRequest(

    val answer: String,
    val id : Int,
)

data class VariantResponse(
    val data: VariantModel
)