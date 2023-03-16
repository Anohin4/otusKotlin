package ru.otus.otuskotlin.marketplace.mappers.v2

import ru.otus.otusKotlin.api.models.*
import ru.otus.otuskotlin.fintrack.api.*
import ru.otus.otuskotlin.fintrack.common.*
import ru.otus.otuskotlin.fintrack.common.models.*
import ru.otus.otuskotlin.fintrack.common.stubs.FinStubs
import ru.otus.otuskotlin.fintrack.mappers.v2.*
import toTransport

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
        assertEquals("title", context.adRequest.title)
        assertEquals(FinVisibility.VISIBLE_PUBLIC, context.adRequest.visibility)
        assertEquals(FinDealSide.DEMAND, context.adRequest.adType)
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
        assertEquals("title", req.ad?.title)
        assertEquals("desc", req.ad?.description)
        assertEquals(AdVisibility.PUBLIC, req.ad?.visibility)
        assertEquals(DealSide.DEMAND, req.ad?.adType)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
