package com.yohana.echolearn.models

data class UserModel(
    val token: String?,
    val username: String,
)

data class GetUserResponse(
    val id: Int = 0,
    val username: String = "",
    val email: String = "",
    val profilePicture: String = "",
    val token: String = "",
    val totalScore: Int = 0,
)

data class ToGetUserResponse(
    val data: GetUserResponse
)

data class LeaderboardResponse(
    val id: Int,
    val username: String,
    val totalScore: Int,
    val profilePicture: String
)

data class LeaderboardListResponse(
    val data: List<LeaderboardResponse>
)

data class UserResponse(
    val data: UserModel
)

data class UserListResponse(
    val data: List<UserModel>
)