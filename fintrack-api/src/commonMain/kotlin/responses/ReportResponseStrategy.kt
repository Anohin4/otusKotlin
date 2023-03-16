package ru.otus.otuskotlin.fintrack.api.responses

import kotlinx.serialization.KSerializer
import ru.otus.otuskotlin.fintrack.api.models.IResponse
import ru.otus.otuskotlin.fintrack.api.models.OpReportResponse
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
