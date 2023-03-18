package jp.matsuura.controller

import io.ktor.server.routing.*
import jp.matsuura.service.login.LoginService
import jp.matsuura.service.register.RegisterService
import org.koin.ktor.ext.inject

fun Route.authController() {
    route("/auth") {
        route("/login") {
            val loginService by inject<LoginService>()
            post {

            }
        }
        route("/register") {
            val registerService by inject<RegisterService>()
            post {
            }
        }
    }
}