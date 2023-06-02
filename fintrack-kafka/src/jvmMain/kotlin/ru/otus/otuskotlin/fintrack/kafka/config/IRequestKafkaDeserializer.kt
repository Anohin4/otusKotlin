package ru.otus.otuskotlin.fintrack.kafka.config

import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import ru.otus.otuskotlin.fintrack.api.apiRequestDeserialize
import ru.otus.otuskotlin.fintrack.api.models.IRequest
import java.nio.charset.Charset

class IRequestKafkaDeserializer : Deserializer<IRequest> {
    override fun deserialize(topic: String?, data: ByteArray?): IRequest {
        if (data == null) throw SerializationException("Message can't be null")
        val dataInString = String(data, Charset.defaultCharset())
        return apiRequestDeserialize(dataInString)
    }
}