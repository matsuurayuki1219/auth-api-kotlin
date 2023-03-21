package jp.matsuura.model

data class AuthResponse(
    val message: String,
    val accessToken: String,
    val refreshToken: String,
)
