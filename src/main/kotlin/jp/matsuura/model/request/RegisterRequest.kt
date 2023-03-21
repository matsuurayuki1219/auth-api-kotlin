package jp.matsuura.model.request

data class RegisterRequest(
    val email: String,
    val password: String,
)