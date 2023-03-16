package ru.otus.otuskotlin.fintrack.api.v2.requests

import kotlinx.serialization.KSerializer
import ru.otus.otusKotlin.api.v2.models.IResponse
import ru.otus.otusKotlin.api.v2.models.OpCreateResponse
import kotlin.reflect.KClass

object CreateResponseStrategy : IResponseStrategy {
    override val discriminator: String = "create"
    override val clazz: KClass<out IResponse> = OpCreateResponse::class
    override val serializer: KSerializer<out IResponse> = OpCreateResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is OpCreateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
