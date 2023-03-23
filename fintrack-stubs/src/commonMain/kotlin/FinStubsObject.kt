package ru.otus.otuskotlin.fintrack.stubs

import ru.otus.otuskotlin.fintrack.common.models.*

object OperationStub {
    val VKUSSVIL: FinOperation
        get() = FinOperation(
            id = FinOperationId("123456"),
            name = "Закупка продуктов",
            description = "Закупились продуктами на неделю",
            amount = 5555.53,
            partner = "VKUSSVIL LLC",
            dateTime = "2022-07-24T09:36:13",
            opType = FinOperationType.EXPENSE,
            category = FinCategory.FOOD
        )
}
