package ru.otus.otuskotlin.fintrack.api.v2.requests

import ru.otus.otusKotlin.api.v2.models.IResponse
import ru.otus.otuskotlin.fintrack.api.v2.IApiStrategy

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
