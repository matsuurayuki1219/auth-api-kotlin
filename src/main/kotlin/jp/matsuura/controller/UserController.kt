package jp.matsuura.controller

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import jp.matsuura.model.error.GetUserErrorType
import jp.matsuura.model.response.ErrorResponse
import jp.matsuura.model.response.UserResponse
import jp.matsuura.service.user.UserService
import jp.matsuura.utility.MessageCode
import jp.matsuura.utility.MessageCode.MessageMap
import jp.matsuura.utility.Result
import org.koin.ktor.ext.inject

fun Routing.userController() {

    val userService by inject<UserService>()

    authenticate("auth-jwt") {
        get("/user/me") {
            val principal = call.principal<JWTPrincipal>() ?: return@get
            val expiresAt = principal.expiresAt?.time
            if (!expiresAt.checkExpiration()) {
                val code = MessageCode.ES00_001
                val message = MessageMap[code] ?: ""
                call.respond(
                    ErrorResponse(
                        code = code,
                        message = message,
                    )
                )
            }
            val email = principal.payload.getClaim("email").asString()
            when (val result = userService.getInfo(email = email)) {
                is Result.Success -> {
                    call.respond(
                        UserResponse(
                            email = result.value.email,
                            password = result.value.password,
                        )
                    )
                }
                is Result.Failure -> {
                    when (result.error) {
                        GetUserErrorType.NotExistUser -> {
                            val code = MessageCode.ES03_001
                            val message = MessageMap[code] ?: ""
                            call.respond(HttpStatusCode.InternalServerError, ErrorResponse(code = code, message = message))
                        }
                    }
                }
            }
        }
    }

}

private fun Long?.checkExpiration(): Boolean {
    if (this == null) return false
    return minus(System.currentTimeMillis()) > 0
}