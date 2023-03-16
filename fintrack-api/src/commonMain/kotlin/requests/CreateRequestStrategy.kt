package ru.otus.otuskotlin.fintrack.api.v2.requests

import kotlinx.serialization.KSerializer
import ru.otus.otusKotlin.api.v2.models.IRequest
import ru.otus.otusKotlin.api.v2.models.OpCreateRequest
import kotlin.reflect.KClass

object CreateRequestStrategy : IRequestStrategy {
    override val discriminator: String = "create"
    override val clazz: KClass<out IRequest> = OpCreateRequest::class
    override val serializer: KSerializer<out IRequest> = OpCreateRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is OpCreateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
