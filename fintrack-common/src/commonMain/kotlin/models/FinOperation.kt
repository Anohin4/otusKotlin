package ru.otus.otuskotlin.fintrack.common.models

data class FinOperation(
    var id: FinOperationId = FinOperationId.NONE,
    var name: String = "",
    var amount: Double = 0.0,
    var dateTime: String = "",

    var opType: FinOperationType = FinOperationType.NONE,
    var description: String = "",
    var partner: String = "",
    var category: FinCategory = FinCategory.OTHER
)
