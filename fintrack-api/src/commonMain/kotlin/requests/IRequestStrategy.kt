package ru.otus.otuskotlin.fintrack.api.requests

import ru.otus.otusKotlin.api.models.IRequest
import ru.otus.otuskotlin.fintrack.api.IApiStrategy

sealed interface IRequestStrategy : IApiStrategy<IRequest> {
    companion object {
        private val members: List<IRequestStrategy> = listOf(
            CreateRequestStrategy,
            ReadRequestStrategy,
            UpdateRequestStrategy,
            DeleteRequestStrategy,
            ReportRequestStrategy
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
