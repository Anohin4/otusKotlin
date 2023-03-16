package ru.otus.otuskotlin.fintrack.api.requests

import kotlinx.serialization.KSerializer
import ru.otus.otusKotlin.api.models.IResponse
import ru.otus.otusKotlin.api.models.OpReportResponse
import kotlin.reflect.KClass

object ReportResponseStrategy : IResponseStrategy {
    override val discriminator: String = "search"
    override val clazz: KClass<out IResponse> = OpReportResponse::class
    override val serializer: KSerializer<out IResponse> = OpReportResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is OpReportResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
