package com.yohana.echolearn.models

data class UserResponse(
    val data: UserModel
)

data class UserModel (
    val username: String,
    val token: String?
)