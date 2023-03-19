package ru.otus.otuskotlin.fintrack.common

import kotlinx.datetime.Instant
import models.FinReportFilter
import ru.otus.otuskotlin.fintrack.common.models.*
import ru.otus.otuskotlin.fintrack.common.stubs.FinStubs


data class FinContext(
    var command: FinCommand = FinCommand.NONE,
    var state: FinState = FinState.NONE,
    val errors: MutableList<FinError> = mutableListOf(),

    var workMode: FinWorkMode = FinWorkMode.PROD,
    var stubCase: FinStubs = FinStubs.NONE,

    var requestId: FinRequestId = FinRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var opRequest: FinOperation = FinOperation(),
    var opFilterRequest: FinReportFilter = FinReportFilter(),
    var opResponse:FinOperation = FinOperation(),
    var opsResponse:MutableList<FinOperation> = mutableListOf()
)
