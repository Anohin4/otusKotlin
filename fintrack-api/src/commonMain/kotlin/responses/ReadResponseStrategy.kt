package ru.otus.otuskotlin.fintrack.api.responses

import kotlinx.serialization.KSerializer
import ru.otus.otuskotlin.fintrack.api.models.IResponse
import ru.otus.otuskotlin.fintrack.api.models.OpReadResponse
import kotlin.reflect.KClass

object ReadResponseStrategy : IResponseStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out IResponse> = OpReadResponse::class
    override val serializer: KSerializer<out IResponse> = OpReadResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is OpReadResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
