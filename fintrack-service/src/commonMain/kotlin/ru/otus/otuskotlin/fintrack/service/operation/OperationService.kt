package ru.otus.otuskotlin.fintrack.service.operation

import ru.otus.otuskotlin.fintrack.common.FinContext
import ru.otus.otuskotlin.fintrack.common.models.FinOperation

interface OperationService {
    fun process(request: FinContext)
}