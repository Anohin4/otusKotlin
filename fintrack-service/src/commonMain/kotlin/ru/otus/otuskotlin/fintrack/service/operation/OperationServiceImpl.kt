package ru.otus.otuskotlin.fintrack.service.operation

import ru.otus.otuskotlin.fintrack.common.models.FinOperation
import ru.otus.otuskotlin.fintrack.stubs.FinStub

class OperationServiceImpl() : OperationService {

    override fun createOperation(request: FinOperation): FinOperation {
        return FinStub.get()
    }

    override fun readOperation(request: FinOperation): FinOperation {
        return FinStub.get()
    }

    override fun updateOperation(request: FinOperation): FinOperation {
        return FinStub.get()
    }

    override fun deleteOperation(request: FinOperation): FinOperation {
        return FinStub.get()
    }
}