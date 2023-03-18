package jp.matsuura.model.response

data class RegisterResponse (
    val message: String,
    val accessToken: String,
    val refreshToken: String,
)