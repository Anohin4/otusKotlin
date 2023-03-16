package ru.otus.otuskotlin.fintrack.api

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.fintrack.api.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request: IRequest = OpCreateRequest(
        requestType = "create",
        requestId = "123",
        debug = OpDebug(
            mode = OpRequestDebugMode.STUB,
            stub = OpRequestDebugStubs.BAD_AMOUNT
        ),
        operation = OpCreateRequestObject(
            name = "test operation",
            description = "this is description",
            opType = OperationType.EXPENSE,
            amount = 12.1,
            dateTime = "2022-07-24T09:36:13"
        )
    )

    @Test
    fun `serialize object to json`() {
        val json = apiMapper.encodeToString(request)

        println(json)

        assertContains(json, Regex("\"name\":\\s*\"test operation\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badAmount\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
        assertContains(json, Regex("\"dateTime\":\\s*\"2022-07-24T09:36:13\""))
    }

    @Test
    fun `deserialize object from json`() {
        val json = apiMapper.encodeToString(request)
        val obj = apiMapper.decodeFromString(json) as OpCreateRequest

        assertEquals(request, obj)
    }
}
