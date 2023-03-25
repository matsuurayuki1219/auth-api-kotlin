package jp.matsuura.model

import io.ktor.server.auth.*

data class User (val email: String): Principal