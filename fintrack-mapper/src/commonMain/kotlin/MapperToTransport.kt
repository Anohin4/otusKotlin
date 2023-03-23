package ru.otus.otuskotlin.fintrack.mappers

import ru.otus.otuskotlin.fintrack.api.models.*
import ru.otus.otuskotlin.fintrack.common.FinContext
import ru.otus.otuskotlin.fintrack.common.models.*
import ru.otus.otuskotlin.fintrack.mappers.exceptions.UnknownRequestClassException


fun FinContext.toTransport() = when (command) {
    FinCommand.CREATE -> fromTransportCreate()
    FinCommand.READ -> fromTransportRead()
    FinCommand.UPDATE -> fromTransportUpdate()
    FinCommand.DELETE -> fromTransportDelete()
    FinCommand.REPORT -> fromTransportReport()
    FinCommand.NONE -> throw UnknownRequestClassException(command::class)
}

fun FinContext.fromTransportCreate() = OpCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == FinState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = this.errors.toTransportError(),
    operation = this.opResponse.toTransport()

)

fun FinContext.fromTransportRead() = OpReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == FinState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = this.errors.toTransportError(),
    operation = this.opResponse.toTransport()

)

fun FinContext.fromTransportUpdate() = OpUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == FinState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = this.errors.toTransportError(),
    operation = this.opResponse.toTransport()
)

fun FinContext.fromTransportDelete() = OpDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == FinState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = this.errors.toTransportError(),
    operation = this.opResponse.toTransport()
)

fun FinContext.fromTransportReport() = OpReportResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == FinState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = this.errors.toTransportError(),
    operations = this.opsResponse.toTransport()
)

private fun List<FinOperation>.toTransport() =
    this.map { it.toTransport() }
        .toList()
        .takeIf { it.isNotEmpty() }

private fun FinOperation.toTransport() = OpResponseObject(
    id = id.takeIf { it != FinOperationId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    amount = amount,
    dateTime = dateTime.takeIf { it.isNotBlank() },
    opType = opType.toTransport(),
    description = description.takeIf { it.isNotBlank() },
    partner = partner.takeIf { it.isNotBlank() },
    category = category.toTransport()

)

private fun List<FinError>.toTransportError() =
    this
        .map { it.toTransport() }
        .toList()
        .takeIf { it.isNotEmpty() }

private fun FinError.toTransport() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() }
)


fun FinOperationType.toTransport() = when (this) {
    FinOperationType.INCOME -> OperationType.INCOME
    FinOperationType.EXPENSE -> OperationType.EXPENSE
    FinOperationType.NONE -> null
}

fun FinCategory.toTransport() = when (this) {
    FinCategory.ENTERTAINMENT -> OperationCategory.ENTERTAINMENT
    FinCategory.TAXI -> OperationCategory.TAXI
    FinCategory.HEALTH -> OperationCategory.HEALTH
    FinCategory.PET -> OperationCategory.PET
    FinCategory.FOOD -> OperationCategory.FOOD
    FinCategory.AUTOMOBILE -> OperationCategory.AUTOMOBILE
    FinCategory.OTHER -> OperationCategory.OTHER
    FinCategory.NONE -> null

}