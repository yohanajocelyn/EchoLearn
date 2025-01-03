package com.yohana.echolearn.models

data class UserModel(
    val token: String?,
    val username: String
)

data class UserResponse(
    val data: UserModel
)