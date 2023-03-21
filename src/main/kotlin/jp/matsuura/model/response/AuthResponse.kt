package jp.matsuura.model.response

data class AuthResponse(
    val message: String,
    val accessToken: String,
    val refreshToken: String,
)
