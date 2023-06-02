package ru.otus.otuskotlin.fintrack.app.rest

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.fintrack.api.apiMapper
import ru.otus.otuskotlin.fintrack.api.models.OpReportRequest
import ru.otus.otuskotlin.fintrack.common.FinContext
import ru.otus.otuskotlin.fintrack.mappers.fromTransport
import ru.otus.otuskotlin.fintrack.mappers.toTransport
import ru.otus.otuskotlin.fintrack.service.operation.OperationService
import ru.otus.otuskotlin.fintrack.service.report.ReportService

suspend fun ApplicationCall.report(context: FinContext, service: OperationService) {
    val request = apiMapper.decodeFromString<OpReportRequest>(receiveText())
    context.fromTransport(request)
    service.process(context)
    respond(apiMapper.encodeToString(context.toTransport()))
}