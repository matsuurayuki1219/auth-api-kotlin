package jp.matsuura.user

import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import jp.matsuura.model.response.ErrorResponse
import jp.matsuura.model.response.UserResponse
import jp.matsuura.utility.JwtUtils
import jp.matsuura.utility.MessageCode
import junit.framework.TestCase
import org.junit.Test

class UserTest {

    @Test
    fun test_success() = testApplication {
        val accessToken = JwtUtils.generateAccessToken(
            audience = "http://0.0.0.0:8080/hello",
            issuer = "http://0.0.0.0:8080/",
            email = "test+1@example.com",
            secret = "secret"
        )
        val client = createClient {
            install(DefaultRequest) {
                header("Authorization", "Bearer $accessToken")
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                jackson()
            }
        }
        val response = client.get("/user/me")
        val body = response.body<UserResponse>()
        TestCase.assertEquals(HttpStatusCode.OK, response.status)
        TestCase.assertEquals("test+1@example.com", body.email)
        TestCase.assertEquals("pass9999", body.password)
    }

    @Test
    fun test_failure_access_token_empty() = testApplication {
        val accessToken = JwtUtils.generateAccessToken(
            audience = "http://0.0.0.0:8080/hello",
            issuer = "http://0.0.0.0:8080/",
            email = "test+100@example.com",
            secret = "secret"
        )
        val client = createClient {
            install(DefaultRequest) {
                header("Authorization", "Bearer $accessToken")
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                jackson()
            }
        }
        val response = client.get("/user/me")
        val body = response.body<ErrorResponse>()
        val correctCode = MessageCode.ES03_001
        val correctMessage = MessageCode.MessageMap[correctCode]
        TestCase.assertEquals(HttpStatusCode.NotFound, response.status)
        TestCase.assertEquals(correctCode, body.code)
        TestCase.assertEquals(correctMessage, body.message)
    }

    @Test
    fun test_failure_access_token_format_error() = testApplication {
        val accessToken = JwtUtils.generateAccessToken(
            audience = "http://0.0.0.0:8080/hello",
            issuer = "http://0.0.0.0:8080/",
            email = "test+100@example.com",
            secret = "secret"
        )
        val client = createClient {
            install(DefaultRequest) {
                header("Authorization", accessToken)
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                jackson()
            }
        }
        val response = client.get("/user/me")
        TestCase.assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun test_failure_access_token_expired() = testApplication {
        val accessToken = JwtUtils.generateAccessTokenForTest(
            audience = "http://0.0.0.0:8080/hello",
            issuer = "http://0.0.0.0:8080/",
            email = "test+1@example.com",
            secret = "secret",
            expiredDuration = -1,
        )
        val client = createClient {
            install(DefaultRequest) {
                header("Authorization", "Bearer $accessToken")
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                jackson()
            }
        }
        val response = client.get("/user/me")
        TestCase.assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun test_failure_access_token_wrong() = testApplication {
        val client = createClient {
            install(DefaultRequest) {
                header("Authorization", "")
                contentType(ContentType.Application.Json)
            }
        }
        val response = client.get("/user/me")
        TestCase.assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun test_failure_access_token_in_email_empty() = testApplication {
        val accessToken = JwtUtils.generateAccessToken(
            audience = "http://0.0.0.0:8080/hello",
            issuer = "http://0.0.0.0:8080/",
            email = "",
            secret = "secret"
        )
        val client = createClient {
            install(DefaultRequest) {
                header("Authorization", "Bearer $accessToken")
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                jackson()
            }
        }
        val response = client.get("/user/me")
        TestCase.assertEquals(HttpStatusCode.Unauthorized, response.status)
    }
}