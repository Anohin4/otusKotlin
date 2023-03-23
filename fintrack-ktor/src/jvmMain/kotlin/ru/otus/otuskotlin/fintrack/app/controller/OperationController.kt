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
import ru.otus.otuskotlin.fintrack.stubs.FinStub

suspend fun ApplicationCall.operationDelete() {
    val request = apiMapper.decodeFromString<OpDeleteRequest>(receiveText())
    val context = FinContext()
    context.fromTransport(request)
    context.opResponse = FinStub.get()
    respond(apiMapper.encodeToString(context.toTransport()))
}

suspend fun ApplicationCall.operationRead() {
    val request = apiMapper.decodeFromString<OpReadRequest>(receiveText())
    val context = FinContext()
    context.fromTransport(request)
    context.opResponse = FinStub.get()
    respond(apiMapper.encodeToString(context.toTransport()))
}

suspend fun ApplicationCall.operationCreate() {
    val request = apiMapper.decodeFromString<OpCreateRequest>(receiveText())
    val context = FinContext()
    context.fromTransport(request)
    context.opResponse = FinStub.get()
    respond(apiMapper.encodeToString(context.toTransport()))
}

suspend fun ApplicationCall.operationUpdate() {
    val request = apiMapper.decodeFromString<OpUpdateRequest>(receiveText())
    val context = FinContext()
    context.fromTransport(request)
    context.opResponse = FinStub.get()
    respond(apiMapper.encodeToString(context.toTransport()))
}