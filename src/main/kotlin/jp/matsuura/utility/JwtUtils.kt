package jp.matsuura.utility

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtUtils {

    private const val ACCESS_TOKEN_EXPIRE_TIME = 1200000L // 20 Minutes
    private const val REFRESH_TOKEN_EXPIRE_TIME = 3600000L * 24L * 30L // 30 days

    fun generateAccessToken(
        audience: String,
        issuer: String,
        email: String,
        secret: String,
        ): String {

        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("email", email)
            .withClaim("tokenType", "accessToken")
            .withExpiresAt(Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
            .sign(Algorithm.HMAC256(secret))
    }

    fun generateRefreshToken(
        audience: String,
        issuer: String,
        email: String,
        secret: String,
    ): String {

        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("email", email)
            .withClaim("tokenType", "refreshToken")
            .withExpiresAt(Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
            .sign(Algorithm.HMAC256(secret))

    }
}