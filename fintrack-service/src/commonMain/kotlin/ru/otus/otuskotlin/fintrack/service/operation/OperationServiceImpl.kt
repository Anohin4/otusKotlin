package ru.otus.otuskotlin.fintrack.service.operation

import ru.otus.otuskotlin.fintrack.common.FinContext
import ru.otus.otuskotlin.fintrack.common.models.FinOperation
import ru.otus.otuskotlin.fintrack.stubs.FinStub

class OperationServiceImpl() : OperationService {
    override fun process(request: FinContext) {
        request.opResponse = FinStub.get()
    }
}