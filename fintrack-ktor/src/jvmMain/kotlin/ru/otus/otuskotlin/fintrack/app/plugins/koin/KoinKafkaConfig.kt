package ru.otus.otuskotlin.fintrack.app.plugins.koin

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.sour.cabbage.soup.consumer
import org.sour.cabbage.soup.producer
import ru.otus.otuskotlin.fintrack.api.models.IRequest
import ru.otus.otuskotlin.fintrack.api.models.IResponse
import ru.otus.otuskotlin.fintrack.kafka.config.AppKafkaConfig
import ru.otus.otuskotlin.fintrack.app.kafka.DefaultConsumerStrategy
import ru.otus.otuskotlin.fintrack.app.kafka.FintrackKafkaConsumer
import ru.otus.otuskotlin.fintrack.kafka.config.IRequestKafkaDeserializer
import ru.otus.otuskotlin.fintrack.kafka.config.IRequestKafkaSerializer

val errorKafkaProducer = "errorKafkaProducer"
val responseKafkaProducer = "responseKafkaProducer"

val kafkaModule = module {
    single(named(responseKafkaProducer)) { producerGetter(get()) }
    single(named(errorKafkaProducer)) { errorProducerGetter(get()) }
    single{ consumerGetter(get()) }
    single { FintrackKafkaConsumer(
        get(),
        get(),
        get(named(responseKafkaProducer)),
        get(named(errorKafkaProducer)),
        listOf(DefaultConsumerStrategy()),
        get()) }
}

fun producerGetter(appKafkaConfig: AppKafkaConfig) = producer<String, IResponse>(
    mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to appKafkaConfig.kafkaHosts,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to IRequestKafkaSerializer::class.java
    )
)
fun errorProducerGetter(appKafkaConfig: AppKafkaConfig) = producer<String, String>(
    mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to  appKafkaConfig.kafkaHosts,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
    )
)

fun consumerGetter(appKafkaConfig: AppKafkaConfig) = consumer<String, IRequest>(
    mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to  appKafkaConfig.kafkaHosts,
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to IRequestKafkaDeserializer::class.java,
        ConsumerConfig.GROUP_ID_CONFIG to AppKafkaConfig.KAFKA_GROUP_ID,
        ConsumerConfig.CLIENT_ID_CONFIG to AppKafkaConfig.KAFKA_CLIENT_ID
    ),
    listOf(appKafkaConfig.kafkaTopicIn)
)