package jp.matsuura.controller

import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.post
import jp.matsuura.model.ErrorResponse
import jp.matsuura.model.error.LoginErrorType
import jp.matsuura.service.AuthService
import jp.matsuura.utility.MessageCode
import org.koin.ktor.ext.inject
import jp.matsuura.utility.Result

fun Routing.authController() {

    val authService by inject<AuthService>()
    @Location("/auth/login")
    data class AuthLoginLocation(val email: String, val password: String)
    post<AuthLoginLocation> { param ->
        val email = param.email
        val password = param.password
        val result = authService.login(
            email = email,
            password = password,
        )
        when (result) {
            is Result.Success -> {
                call.respond(result.value)
            }
            is Result.Failure -> {
                val code = when (result.value) {
                    LoginErrorType.NotExistUser -> MessageCode.ES01_001
                    LoginErrorType.UnknownError -> MessageCode.ES01_002
                    LoginErrorType.WrongPassword -> MessageCode.ES01_003
                }
                val message = MessageCode.MessageMap[code] ?: ""
                call.respond(ErrorResponse(code = code, message = message))
            }
        }
    }
}