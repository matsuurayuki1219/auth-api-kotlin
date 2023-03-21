package jp.matsuura.controller

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.post
import jp.matsuura.model.JwtInfo
import jp.matsuura.model.response.ErrorResponse
import jp.matsuura.model.request.LoginRequest
import jp.matsuura.model.error.LoginErrorType
import jp.matsuura.model.error.RegisterErrorType
import jp.matsuura.model.request.RegisterRequest
import jp.matsuura.service.AuthService
import jp.matsuura.utility.MessageCode
import org.koin.ktor.ext.inject
import jp.matsuura.utility.Result

fun Routing.authController() {

    val authService by inject<AuthService>()

    val environment = environment ?: return

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()

    val jwtInfo = JwtInfo(secret = secret, issuer = issuer, audience = audience)

    post("/auth/login") {
        val request = call.receive<LoginRequest>()
        val email = request.email
        val password = request.password
        val result = authService.login(
            email = email,
            password = password,
            jwtInfo = jwtInfo,
        )
        when (result) {
            is Result.Success -> {
                call.respond(result.value)
            }
            is Result.Failure -> {
                when (result.error) {
                    LoginErrorType.NotExistUser -> {
                        val code = MessageCode.ES01_001
                        val message = MessageCode.MessageMap[code] ?: ""
                        call.respond(HttpStatusCode.NotFound, ErrorResponse(code = code, message = message))
                    }
                    LoginErrorType.UnknownError -> {
                        val code = MessageCode.ES01_002
                        val message = MessageCode.MessageMap[code] ?: ""
                        call.respond(HttpStatusCode.InternalServerError, ErrorResponse(code = code, message = message))
                    }
                    LoginErrorType.WrongPassword -> {
                        val code = MessageCode.ES01_003
                        val message = MessageCode.MessageMap[code] ?: ""
                        call.respond(HttpStatusCode.Unauthorized, ErrorResponse(code = code, message = message))
                    }
                }

            }
        }
    }

    post("/auth/register") {
        val request = call.receive<RegisterRequest>()
        val email = request.email
        val password = request.password
        val result = authService.register(
            email = email,
            password = password,
            jwtInfo = jwtInfo,
        )
        when (result) {
            is Result.Success -> {
                call.respond(result.value)
            }
            is Result.Failure -> {
                when (result.error) {
                    RegisterErrorType.AlreadyExistedUser -> {
                        val code = MessageCode.ES02_001
                        val message = MessageCode.MessageMap[code] ?: ""
                        call.respond(HttpStatusCode.Conflict, ErrorResponse(code = code, message = message))
                    }
                    RegisterErrorType.EmailValidationError -> {
                        val code = MessageCode.ES02_002
                        val message = MessageCode.MessageMap[code] ?: ""
                        call.respond(HttpStatusCode.BadRequest, ErrorResponse(code = code, message = message))
                    }
                    RegisterErrorType.PasswordValidationError -> {
                        val code = MessageCode.ES02_003
                        val message = MessageCode.MessageMap[code] ?: ""
                        call.respond(HttpStatusCode.BadRequest, ErrorResponse(code = code, message = message))
                    }
                    RegisterErrorType.UnknownError -> {
                        val code = MessageCode.ES02_004
                        val message = MessageCode.MessageMap[code] ?: ""
                        call.respond(HttpStatusCode.InternalServerError, ErrorResponse(code = code, message = message))
                    }
                }

            }
        }
    }
}