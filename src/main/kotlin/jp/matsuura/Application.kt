package jp.matsuura

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.tomcat.*
import jp.matsuura.controller.authController
import jp.matsuura.controller.testController
import jp.matsuura.controller.userController
import jp.matsuura.data.Migration
import jp.matsuura.di.authRepositoryModule
import jp.matsuura.di.authServiceModule
import org.koin.ktor.plugin.Koin
import java.text.DateFormat

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
        modules(authServiceModule, authRepositoryModule)
    }
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
            dateFormat = DateFormat.getDateInstance()
        }
    }
    Migration()
}