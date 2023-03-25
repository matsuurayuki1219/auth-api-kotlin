package jp.matsuura.login

import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import jp.matsuura.model.request.LoginRequest
import jp.matsuura.model.response.AuthResponse
import jp.matsuura.model.response.ErrorResponse
import jp.matsuura.utility.Const
import jp.matsuura.utility.MessageCode
import junit.framework.TestCase
import org.junit.Test

class LoginTest {

    @Test
    fun test_success() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                jackson()
            }
        }
        val response = client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(
                LoginRequest(
                    email = "test+1@example.com",
                    password = "pass9999",
                )
            )
        }
        val body = response.body<AuthResponse>()
        TestCase.assertEquals(HttpStatusCode.OK, response.status)
        TestCase.assertEquals(Const.OK, body.message)
        TestCase.assertEquals(body.accessToken.isNotEmpty(), true)
        TestCase.assertEquals(body.refreshToken.isNotEmpty(), true)
    }

    @Test
    fun test_failure_user_not_exist() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                jackson()
            }
        }
        val response = client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(
                LoginRequest(
                    email = "test+100@example.com",
                    password = "pass9999",
                )
            )
        }
        val body = response.body<ErrorResponse>()
        val correctCode = MessageCode.ES01_001
        val correctMessage = MessageCode.MessageMap[correctCode]
        TestCase.assertEquals(HttpStatusCode.NotFound, response.status)
        TestCase.assertEquals(correctCode, body.code)
        TestCase.assertEquals(correctMessage, body.message)
    }

    @Test
    fun test_failure_password_wrong() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                jackson()
            }
        }
        val response = client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(
                LoginRequest(
                    email = "test+1@example.com",
                    password = "pass999",
                )
            )
        }
        val body = response.body<ErrorResponse>()
        val correctCode = MessageCode.ES01_003
        val correctMessage = MessageCode.MessageMap[correctCode]
        TestCase.assertEquals(HttpStatusCode.Unauthorized, response.status)
        TestCase.assertEquals(correctCode, body.code)
        TestCase.assertEquals(correctMessage, body.message)
    }

}