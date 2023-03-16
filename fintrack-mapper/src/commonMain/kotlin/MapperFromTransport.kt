package ru.otus.otuskotlin.fintrack.mappers.v2

import models.FinReportFilter
import ru.otus.otusKotlin.api.models.*
import ru.otus.otuskotlin.fintrack.common.FinContext
import ru.otus.otuskotlin.fintrack.common.models.*
import ru.otus.otuskotlin.fintrack.common.stubs.FinStubs
import ru.otus.otuskotlin.fintrack.mappers.v2.exceptions.UnknownRequestClassException

fun FinContext.fromTransport(request: IRequest) = when (request) {
    is OpCreateRequest -> fromTransport(request)
    is OpReadRequest -> fromTransport(request)
    is OpUpdateRequest -> fromTransport(request)
    is OpDeleteRequest -> fromTransport(request)
    is OpReportRequest -> fromTransport(request)
    else -> throw UnknownRequestClassException(request::class)
}

fun IRequest?.requestId() = this?.requestId?.let { FinRequestId(it) } ?: FinRequestId.NONE
fun String?.toOpId() = this?.let { FinOperationId(it) } ?: FinOperationId.NONE
fun String?.toOperationWithId() = FinOperation(id = this.toOpId())


fun FinContext.fromTransport(request: OpCreateRequest) {
    command = FinCommand.CREATE
    requestId = request.requestId()
    opRequest = request.operation?.toInternal() ?: FinOperation()
    workMode = request.debug.transportToWorkNode()
    stubCase = request.debug.transportToStubCase()
}

fun FinContext.fromTransport(request: OpReadRequest) {
    command = FinCommand.READ
    requestId = request.requestId()
    opRequest = request.operation.id.toOperationWithId()
    workMode = request.debug.transportToWorkNode()
    stubCase = request.debug.transportToStubCase()
}

fun FinContext.fromTransport(request: OpDeleteRequest) {
    command = FinCommand.DELETE
    requestId = request.requestId()
    opRequest = request.operation.id.toOperationWithId()
    workMode = request.debug.transportToWorkNode()
    stubCase = request.debug.transportToStubCase()
}

fun FinContext.fromTransport(request: OpUpdateRequest) {
    command = FinCommand.UPDATE
    requestId = request.requestId()
    opRequest = request.operation.toInternal()
    workMode = request.debug.transportToWorkNode()
    stubCase = request.debug.transportToStubCase()
}

fun FinContext.fromTransport(request: OpReportRequest) {
    command = FinCommand.UPDATE
    requestId = request.requestId()
    opFilterRequest = request.opFilter.toInternal()
    workMode = request.debug.transportToWorkNode()
    stubCase = request.debug.transportToStubCase()
}

private fun OpReportFilter?.toInternal() = FinReportFilter(
    dateTimeFrom = this?.dateTimeFrom,
    dateTimeTo = this?.dateTimeTo ?: "",
    category = this?.category.fromTransport(),
    name = this?.name ?: "",
    partner = this?.partner ?: "",
    opType = this?.opType.fromTransport()
)


fun OpUpdateRequestObject.toInternal() = FinOperation(
    name = this.name,
    amount = this.amount,
    dateTime = this.dateTime,
    opType = this.opType.fromTransport(),
    description = this.description ?: "",
    partner = this.partner ?: "",
    category = this.category.fromTransport()
)

fun OpCreateRequestObject.toInternal() = FinOperation(
    name = this.name,
    amount = this.amount,
    dateTime = this.dateTime,
    opType = this.opType.fromTransport(),
    description = this.description ?: "",
    partner = this.partner ?: "",
    category = this.category.fromTransport()
)

fun OpDebug?.transportToWorkNode() = when (this?.mode) {
    OpRequestDebugMode.PROD -> FinWorkMode.PROD
    OpRequestDebugMode.TEST -> FinWorkMode.TEST
    OpRequestDebugMode.STUB -> FinWorkMode.STUB
    null -> FinWorkMode.STUB
}

fun OpDebug?.transportToStubCase() = when (this?.stub) {
    OpRequestDebugStubs.SUCCESS -> FinStubs.SUCCESS
    OpRequestDebugStubs.NOT_FOUND -> FinStubs.NOT_FOUND
    OpRequestDebugStubs.BAD_ID -> FinStubs.BAD_ID
    OpRequestDebugStubs.BAD_AMOUNT -> FinStubs.BAD_AMOUNT
    OpRequestDebugStubs.BAD_CATEGORY -> FinStubs.BAD_CATEGORY
    null -> FinStubs.NONE
}

fun OperationType?.fromTransport() = when (this) {
    OperationType.INCOME -> FinOperationType.INCOME
    OperationType.EXPENSE -> FinOperationType.EXPENSE
    null -> FinOperationType.NONE
}

fun OperationCategory?.fromTransport() = when (this) {
    OperationCategory.ENTERTAINMENT -> FinCategory.ENTERTAINMENT
    OperationCategory.TAXI -> FinCategory.TAXI
    OperationCategory.HEALTH -> FinCategory.HEALTH
    OperationCategory.PET -> FinCategory.PET
    OperationCategory.FOOD -> FinCategory.FOOD
    OperationCategory.AUTOMOBILE -> FinCategory.AUTOMOBILE
    OperationCategory.OTHER -> FinCategory.OTHER
    null -> FinCategory.NONE

}