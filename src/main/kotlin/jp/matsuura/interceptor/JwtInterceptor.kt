package jp.matsuura.interceptor

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import jp.matsuura.model.response.ErrorResponse
import jp.matsuura.utility.MessageCode

fun AuthenticationConfig.jwtInterceptor(environment: ApplicationEnvironment) {

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()

    jwt("auth-jwt") {
        // verify a token format and its signature.
        verifier(
            JWT
            .require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build())

        // validations on the JWT payload.
        validate { credential ->
            val email = credential.payload.getClaim("email").asString()
            val expiredAt = credential.payload.expiresAt.time
            val isExpired = expiredAt.checkWhetherExpired()
            if (isExpired) {
                null
            } else if (email.isEmpty()) {
                null
            } else {
                JWTPrincipal(credential.payload)
            }
        }

        //　if authentication fails.
        challenge { _, _ ->
            val code = MessageCode.ES00_001
            val message = MessageCode.MessageMap[code] ?: ""
            call.respond(
                HttpStatusCode.Unauthorized,
                ErrorResponse(
                    code = code,
                    message = message,
                )
            )
        }
    }
}

private fun Long.checkWhetherExpired(): Boolean {
    return (this - System.currentTimeMillis()) < 0
}