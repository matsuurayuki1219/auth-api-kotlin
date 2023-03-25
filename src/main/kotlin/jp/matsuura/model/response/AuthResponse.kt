package jp.matsuura.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthResponse(
    val message: String,
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String,
)
