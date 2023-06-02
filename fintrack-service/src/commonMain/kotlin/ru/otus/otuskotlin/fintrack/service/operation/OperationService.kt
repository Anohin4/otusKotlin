package ru.otus.otuskotlin.fintrack.service.operation

import ru.otus.otuskotlin.fintrack.common.models.FinOperation

interface OperationService {
    fun createOperation(request: FinOperation) : FinOperation
    fun readOperation(request: FinOperation) : FinOperation
    fun updateOperation(request: FinOperation) : FinOperation
    fun deleteOperation(request: FinOperation) : FinOperation
}