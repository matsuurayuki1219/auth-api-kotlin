package jp.matsuura.controller

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.testController() {
    route("/") {
        get {
            call.respondText("Hello World!")
        }
    }
}