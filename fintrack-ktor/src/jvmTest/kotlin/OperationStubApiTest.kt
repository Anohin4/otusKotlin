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
import ru.otus.otuskotlin.fintrack.app.plugins.configureKoin
import ru.otus.otuskotlin.fintrack.app.plugins.configureRouting
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
            val requestObj = OpCreateRequest(
                requestType = "create",
                requestId = "12345",
                operation = OpCreateRequestObject(
                    name = "Кино",
                    description = "сходили в кино",
                    opType = OperationType.EXPENSE,
                    amount = 150.0,
                    dateTime = "2005-08-09T18:31:42"
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
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
            val requestObj = OpReadRequest(
                requestType = "read",
                requestId = "12345",
                operation = OpReadRequestObject(
                    id = "12"
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
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
            val requestObj = OpDeleteRequest(
                requestType = "delete",
                requestId = "12345",
                operation = OpDeleteRequestObject(
                    id = "Кино"
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
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
            val requestObj = OpUpdateRequest(
                requestType = "update",
                requestId = "12345",
                operation = OpUpdateRequestObject(
                    name = "Кино",
                    description = "сходили в кино",
                    opType = OperationType.EXPENSE,
                    amount = 150.0,
                    dateTime = "2005-08-09T18:31:42"
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
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
            val requestObj = OpReportRequest(
                requestType = "report",
                requestId = "12345",
                opFilter = OpReportFilter(
                    name = "Кино",
                    dateTimeFrom = "2005-08-09T18:31:42",
                    opType = OperationType.EXPENSE
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
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
