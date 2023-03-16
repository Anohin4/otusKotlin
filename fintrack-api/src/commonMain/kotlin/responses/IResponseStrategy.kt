package ru.otus.otuskotlin.fintrack.api.requests

import ru.otus.otusKotlin.api.models.IResponse
import ru.otus.otuskotlin.fintrack.api.IApiStrategy

sealed interface IResponseStrategy : IApiStrategy<IResponse> {
    companion object {
        val members = listOf(
            CreateResponseStrategy,
            ReadResponseStrategy,
            UpdateResponseStrategy,
            DeleteResponseStrategy,
            ReportResponseStrategy,
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
