package ru.otus.otuskotlin.fintrack.api.v2.requests

import ru.otus.otusKotlin.api.v2.models.IRequest
import ru.otus.otuskotlin.fintrack.api.v2.IApiStrategy

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
