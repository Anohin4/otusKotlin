package ru.otus.otuskotlin.fintrack.app.controller

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.fintrack.api.apiMapper
import ru.otus.otuskotlin.fintrack.api.models.OpCreateRequest
import ru.otus.otuskotlin.fintrack.api.models.OpDeleteRequest
import ru.otus.otuskotlin.fintrack.api.models.OpReadRequest
import ru.otus.otuskotlin.fintrack.api.models.OpUpdateRequest
import ru.otus.otuskotlin.fintrack.common.FinContext
import ru.otus.otuskotlin.fintrack.mappers.fromTransport
import ru.otus.otuskotlin.fintrack.mappers.toTransport
import ru.otus.otuskotlin.fintrack.service.operation.OperationService


suspend fun ApplicationCall.operationDelete(context: FinContext, repository: OperationService) {
    val request = apiMapper.decodeFromString<OpDeleteRequest>(receiveText())
    context.fromTransport(request)
    context.opResponse = repository.deleteOperation(context.opRequest)
    respond(apiMapper.encodeToString(context.toTransport()))
}

suspend fun ApplicationCall.operationRead(context: FinContext, repository: OperationService) {
    val request = apiMapper.decodeFromString<OpReadRequest>(receiveText())
    context.fromTransport(request)
    context.opResponse = repository.readOperation(context.opRequest)
    respond(apiMapper.encodeToString(context.toTransport()))
}

suspend fun ApplicationCall.operationCreate(context: FinContext, repository: OperationService) {
    val request = apiMapper.decodeFromString<OpCreateRequest>(receiveText())
    context.fromTransport(request)
    context.opResponse = repository.createOperation(context.opRequest)
    respond(apiMapper.encodeToString(context.toTransport()))
}

suspend fun ApplicationCall.operationUpdate(context: FinContext, repository: OperationService) {
    val request = apiMapper.decodeFromString<OpUpdateRequest>(receiveText())
    context.fromTransport(request)
    context.opResponse = repository.deleteOperation(context.opRequest)
    respond(apiMapper.encodeToString(context.toTransport()))
}