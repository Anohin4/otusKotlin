package ru.otus.otuskotlin.marketplace.mappers.v2

import ru.otus.otuskotlin.fintrack.api.models.*
import ru.otus.otuskotlin.fintrack.api.*
import ru.otus.otuskotlin.fintrack.common.*
import ru.otus.otuskotlin.fintrack.common.models.*
import ru.otus.otuskotlin.fintrack.common.stubs.FinStubs
import ru.otus.otuskotlin.fintrack.mappers.fromTransport
import ru.otus.otuskotlin.fintrack.mappers.toTransport
import ru.otus.otuskotlin.fintrack.mappers.v2.*


import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = OpCreateRequest(
            requestId = "1234",
            debug = OpDebug(
                mode = OpRequestDebugMode.STUB,
                stub = OpRequestDebugStubs.SUCCESS,
            ),
            operation = OpCreateRequestObject(
                name = "test operation",
                description = "this is description",
                opType = OperationType.EXPENSE,
                amount = 12.1,
                dateTime = "2022-07-24T09:36:13"
            ),
        )

        val context = FinContext()
        context.fromTransport(req)

        assertEquals(FinStubs.SUCCESS, context.stubCase)
        assertEquals(FinWorkMode.STUB, context.workMode)
        assertEquals("test operation", context.opRequest.name)
        assertEquals(FinOperationType.EXPENSE, context.opRequest.opType)
    }

    @Test
    fun toTransport() {
        val context = FinContext(
            requestId = FinRequestId("1234"),
            command = FinCommand.CREATE,
            opResponse = FinOperation(
                name = "THis is my name",
                description = "smth",
                opType = FinOperationType.EXPENSE
            ),
            errors = mutableListOf(
                FinError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = FinState.RUNNING
        )

        val req = context.toTransport() as OpCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("THis is my name", req.operation?.name)
        assertEquals("smth", req.operation?.description)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
