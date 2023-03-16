package ru.otus.otuskotlin.fintrack.api.requests

import ru.otus.otuskotlin.fintrack.api.IApiStrategy
import ru.otus.otuskotlin.fintrack.api.models.IRequest

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
