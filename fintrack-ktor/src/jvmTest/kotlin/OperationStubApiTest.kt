import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.core.context.GlobalContext.stopKoin
import ru.otus.otuskotlin.fintrack.api.apiMapper
import ru.otus.otuskotlin.fintrack.api.models.*
import ru.otus.otuskotlin.fintrack.app.plugins.configureRouting
import ru.otus.otuskotlin.fintrack.app.plugins.koin.configureKoin
import ru.otus.otuskotlin.fintrack.stubs.FinStub


class OperationStubApiTest {
    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun create() = testApplication {
        configureTestContext()
        val response = client.post("/operation/create") {
            val requestJson = createRequestAsString()
            contentType(ContentType.Application.Json)
            println(requestJson)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()

        val responseObj = apiMapper.decodeFromString<OpCreateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(FinStub.get().id.asString(), responseObj.operation?.id)
    }

    @Test
    fun read() = testApplication {
        configureTestContext()
        val response = client.post("/operation/read") {
            contentType(ContentType.Application.Json)
            val requestJson = readRequestAsString()
            println(requestJson)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()

        val responseObj = apiMapper.decodeFromString<OpReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(FinStub.get().id.asString(), responseObj.operation?.id)
    }

    @Test
    fun delete() = testApplication {
        configureTestContext()
        val response = client.post("/operation/delete") {
            contentType(ContentType.Application.Json)
            val requestJson = deleteRequestAsString()
            println(requestJson)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<OpDeleteResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(FinStub.get().id.asString(), responseObj.operation?.id)
    }

    @Test
    fun update() = testApplication {
        configureTestContext()
        val response = client.post("/operation/update") {
            contentType(ContentType.Application.Json)
            val requestJson = updateRequestAsString()
            println(requestJson)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()

        val responseObj = apiMapper.decodeFromString<OpUpdateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(FinStub.get().id.asString(), responseObj.operation?.id)
    }

    @Test
    fun report() = testApplication {
        configureTestContext()
        val response = client.post("/report") {
            val requestJson = reportRequestAsString()
            contentType(ContentType.Application.Json)
            println(requestJson)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()

        val responseObj = apiMapper.decodeFromString<OpReportResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(3, responseObj.operations?.size)
    }

    private fun ApplicationTestBuilder.configureTestContext() {
        application {
            configureRouting()
            configureKoin()
        }
    }
}
