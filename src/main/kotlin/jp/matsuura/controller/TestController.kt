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
import jp.matsuura.service.auth.AuthService
import jp.matsuura.utility.MessageCode
import org.koin.ktor.ext.inject
import jp.matsuura.utility.Result

fun Routing.testController() {

    get("/") {
        call.respond("Hello World!!")
    }
}