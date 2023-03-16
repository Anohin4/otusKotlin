package ru.otus.otuskotlin.fintrack.api.requests

import kotlinx.serialization.KSerializer
import ru.otus.otusKotlin.api.models.IResponse
import ru.otus.otusKotlin.api.models.OpUpdateResponse
import kotlin.reflect.KClass

object UpdateResponseStrategy : IResponseStrategy {
    override val discriminator: String = "update"
    override val clazz: KClass<out IResponse> = OpUpdateResponse::class
    override val serializer: KSerializer<out IResponse> = OpUpdateResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is OpUpdateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
