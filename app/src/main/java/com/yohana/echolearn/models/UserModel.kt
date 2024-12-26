package com.yohana.echolearn.models

data class UserModel(
    val username: String,
    val token: String?
)

data class UserResponse(
    val data: UserModel
)