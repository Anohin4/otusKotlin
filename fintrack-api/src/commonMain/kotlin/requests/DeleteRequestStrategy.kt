package ru.otus.otuskotlin.fintrack.api.v2.requests

import kotlinx.serialization.KSerializer
import ru.otus.otusKotlin.api.v2.models.IRequest
import ru.otus.otusKotlin.api.v2.models.OpDeleteRequest
import kotlin.reflect.KClass

object DeleteRequestStrategy : IRequestStrategy {
    override val discriminator: String = "delete"
    override val clazz: KClass<out IRequest> = OpDeleteRequest::class
    override val serializer: KSerializer<out IRequest> = OpDeleteRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is OpDeleteRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
