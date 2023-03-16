package ru.otus.otuskotlin.fintrack.api.v2.requests

import kotlinx.serialization.KSerializer
import ru.otus.otusKotlin.api.v2.models.IResponse
import ru.otus.otusKotlin.api.v2.models.OpDeleteResponse
import kotlin.reflect.KClass

object DeleteResponseStrategy : IResponseStrategy {
    override val discriminator: String = "delete"
    override val clazz: KClass<out IResponse> = OpDeleteResponse::class
    override val serializer: KSerializer<out IResponse> = OpDeleteResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is OpDeleteResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
