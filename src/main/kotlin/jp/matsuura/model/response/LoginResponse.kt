package jp.matsuura.model.response

data class LoginResponse (
    val message: String,
    val accessToken: String,
    val refreshToken: String,
)