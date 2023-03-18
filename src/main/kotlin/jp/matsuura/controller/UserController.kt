package jp.matsuura.controller

import io.ktor.server.routing.*

fun Route.userController() {
    route("/user/me") {
        get {
            // get user info.
        }
    }
}