package ru.otus.otuskotlin.fintrack.app.controller

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
import ru.otus.otuskotlin.fintrack.service.report.ReportService

suspend fun ApplicationCall.report(context: FinContext, service: ReportService) {
    val request = apiMapper.decodeFromString<OpReportRequest>(receiveText())
    context.fromTransport(request)
    context.opsResponse.addAll(service.getReport(context.opFilterRequest))
    respond(apiMapper.encodeToString(context.toTransport()))
}