package ru.otus.otuskotlin.fintrack.api.v2.requests

import kotlinx.serialization.KSerializer
import ru.otus.otusKotlin.api.v2.models.IRequest
import ru.otus.otusKotlin.api.v2.models.OpUpdateRequest
import kotlin.reflect.KClass

object UpdateRequestStrategy : IRequestStrategy {
    override val discriminator: String = "update"
    override val clazz: KClass<out IRequest> = OpUpdateRequest::class
    override val serializer: KSerializer<out IRequest> = OpUpdateRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is OpUpdateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
