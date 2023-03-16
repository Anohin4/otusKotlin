package ru.otus.otuskotlin.fintrack.api.requests

import kotlinx.serialization.KSerializer
import ru.otus.otuskotlin.fintrack.api.models.IRequest
import ru.otus.otuskotlin.fintrack.api.models.OpReadRequest

import kotlin.reflect.KClass

object ReadRequestStrategy: IRequestStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out IRequest> = OpReadRequest::class
    override val serializer: KSerializer<out IRequest> = OpReadRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is OpReadRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
