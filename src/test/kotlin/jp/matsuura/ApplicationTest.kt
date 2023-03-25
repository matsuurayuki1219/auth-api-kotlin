package jp.matsuura

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase
import org.junit.Test


class ApplicationTest {
    @Test
    fun test_sample() = testApplication {
        val response = client.get("/")
        TestCase.assertEquals(HttpStatusCode.OK, response.status)
        TestCase.assertEquals("Hello World!!", response.bodyAsText())
    }
}
