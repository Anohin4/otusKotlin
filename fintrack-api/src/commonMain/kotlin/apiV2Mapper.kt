package ru.otus.otuskotlin.fintrack.api

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import ru.otus.otusKotlin.api.models.*


@OptIn(ExperimentalSerializationApi::class)
val apiV2Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(IRequest::class) {
            @Suppress("UNCHECKED_CAST")
            when (it) {
                is OpCreateRequest -> RequestSerializer(OpCreateRequest.serializer()) as SerializationStrategy<IRequest>
                is OpReadRequest -> RequestSerializer(OpReadRequest.serializer()) as SerializationStrategy<IRequest>
                is OpUpdateRequest -> RequestSerializer(OpUpdateRequest.serializer()) as SerializationStrategy<IRequest>
                is OpDeleteRequest -> RequestSerializer(OpDeleteRequest.serializer()) as SerializationStrategy<IRequest>
                is OpReportRequest -> RequestSerializer(OpReportRequest.serializer()) as SerializationStrategy<IRequest>
                else -> null
            }
        }
        polymorphicDefaultSerializer(IResponse::class) {
            @Suppress("UNCHECKED_CAST")
            when (it) {
                is OpCreateResponse -> ResponseSerializer(OpCreateResponse.serializer()) as SerializationStrategy<IResponse>
                is OpReadResponse -> ResponseSerializer(OpReadResponse.serializer()) as SerializationStrategy<IResponse>
                is OpUpdateResponse -> ResponseSerializer(OpUpdateResponse.serializer()) as SerializationStrategy<IResponse>
                is OpDeleteResponse -> ResponseSerializer(OpDeleteResponse.serializer()) as SerializationStrategy<IResponse>
                is OpReportResponse -> ResponseSerializer(OpReportResponse.serializer()) as SerializationStrategy<IResponse>
                else -> null
            }
        }

        contextual(AdRequestSerializer)
        contextual(AdResponseSerializer)
    }
}
