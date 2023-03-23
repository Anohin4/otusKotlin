package ru.otus.otuskotlin.fintrack.app.controller

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.fintrack.api.apiMapper
import ru.otus.otuskotlin.fintrack.api.models.OpReportRequest
import ru.otus.otuskotlin.fintrack.api.models.OpUpdateRequest
import ru.otus.otuskotlin.fintrack.common.FinContext
import ru.otus.otuskotlin.fintrack.mappers.fromTransport
import ru.otus.otuskotlin.fintrack.mappers.toTransport
import ru.otus.otuskotlin.fintrack.stubs.FinStub

suspend fun ApplicationCall.report() {
    val request = apiMapper.decodeFromString<OpReportRequest>(receiveText())
    val context = FinContext()
    context.fromTransport(request)
    context.opsResponse.addAll(FinStub.getList())
    respond(apiMapper.encodeToString(context.toTransport()))
}