package ru.otus.otuskotlin.fintrack.api.requests

import kotlinx.serialization.KSerializer
import ru.otus.otusKotlin.api.models.IRequest
import ru.otus.otusKotlin.api.models.OpReportRequest
import kotlin.reflect.KClass

object ReportRequestStrategy : IRequestStrategy {
    override val discriminator: String = "report"
    override val clazz: KClass<out IRequest> = OpReportRequest::class
    override val serializer: KSerializer<out IRequest> = OpReportRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is OpReportRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
