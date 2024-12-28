package com.yohana.echolearn.models

data class VariantModel(
    val answer: String,
    val emptyLyric: String,
    val id: Int,
    val type: String
)

data class VariantResponse(
    val data: VariantModel
)