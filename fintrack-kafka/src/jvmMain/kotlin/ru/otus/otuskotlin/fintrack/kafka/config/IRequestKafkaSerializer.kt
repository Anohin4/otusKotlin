package ru.otus.otuskotlin.fintrack.kafka.config

import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import org.apache.kafka.common.serialization.Serializer
import ru.otus.otuskotlin.fintrack.api.apiMapper
import ru.otus.otuskotlin.fintrack.api.apiResponseSerialize
import ru.otus.otuskotlin.fintrack.api.models.IRequest
import ru.otus.otuskotlin.fintrack.api.models.IResponse

class IRequestKafkaSerializer : Serializer<IResponse> {
    override fun serialize(topic: String?, data: IResponse?): ByteArray {
        val encodeToString = data?.let { apiResponseSerialize(it) }
        if (encodeToString != null) {
            return encodeToString.toByteArray()
        }
        throw SerializationException("Can't serialize null value")
    }

}