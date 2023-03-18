package jp.matsuura

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.routing.*
import io.ktor.server.tomcat.*
import jp.matsuura.controller.authController
import jp.matsuura.controller.testController
import jp.matsuura.controller.userController
import jp.matsuura.di.loginServiceModule
import jp.matsuura.di.registerServiceModule
import org.koin.ktor.plugin.Koin


fun main() {
    embeddedServer(Tomcat, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(Routing) {
        testController()
        authController()
        userController()
    }
    install(Koin) {
        modules(loginServiceModule, registerServiceModule)
    }


}