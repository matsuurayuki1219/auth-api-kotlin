package jp.matsuura.model

data class JwtInfo (
    val secret: String,
    val issuer: String,
    val audience: String,
)