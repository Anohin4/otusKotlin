package ru.otus.otuskotlin.fintrack.api.responses

import kotlinx.serialization.KSerializer
import ru.otus.otuskotlin.fintrack.api.models.IResponse
import ru.otus.otuskotlin.fintrack.api.models.OpCreateResponse
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
