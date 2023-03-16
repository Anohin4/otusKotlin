package ru.otus.otuskotlin.fintrack.api.responses

import kotlinx.serialization.KSerializer
import ru.otus.otuskotlin.fintrack.api.models.IResponse
import ru.otus.otuskotlin.fintrack.api.models.OpDeleteResponse
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
