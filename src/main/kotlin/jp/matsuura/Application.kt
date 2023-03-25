package jp.matsuura

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.tomcat.*
import jp.matsuura.controller.authController
import jp.matsuura.controller.userController
import jp.matsuura.data.Migration
import jp.matsuura.di.authRepositoryModule
import jp.matsuura.di.authServiceModule
import jp.matsuura.di.userRepositoryModule
import jp.matsuura.di.userServiceModule
import jp.matsuura.interceptor.jwtInterceptor
import org.koin.ktor.plugin.Koin
import java.text.DateFormat

fun main(args: Array<String>) {
    embeddedServer(Tomcat, commandLineEnvironment(args)).start(wait = true)
}

fun Application.module() {

    val environment = environment
    install(Authentication) {
        jwtInterceptor(environment = environment)
    }

    install(Routing) {
        authController()
        userController()
    }
    install(Koin) {
        modules(authServiceModule, userServiceModule)
        modules(authRepositoryModule, userRepositoryModule)
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