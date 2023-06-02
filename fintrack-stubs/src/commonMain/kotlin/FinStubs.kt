package ru.otus.otuskotlin.fintrack.stubs

import ru.otus.otuskotlin.fintrack.common.models.FinOperation
import ru.otus.otuskotlin.fintrack.stubs.OperationStub.VKUSSVIL

object FinStub {
    fun get(): FinOperation = VKUSSVIL.copy()

    fun getList():List<FinOperation> = listOf(get(), get(), get())
}
