package ru.otus.otuskotlin.marketplace.api.v2

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otusKotlin.api.models.*
import ru.otus.otuskotlin.fintrack.api.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response: IResponse = OpCreateResponse(
        responseType = "create",
        requestId = "123",
        operation = OpResponseObject(
            name = "response title",
            description = "my description",
            opType = OperationType.EXPENSE
        )
    )

    @Test
    fun serialize() {
        val json = apiV2Mapper.encodeToString(response)

        println(json)

        assertContains(json, Regex("\"name\":\\s*\"response title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV2Mapper.encodeToString(response)
        val obj = apiV2Mapper.decodeFromString(json) as OpCreateResponse

        assertEquals(response, obj)
    }
}
