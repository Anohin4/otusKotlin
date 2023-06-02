package ru.otus.otuskotlin.fintrack.app.kafka

import kotlinx.atomicfu.atomic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import kotlinx.datetime.Clock
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.WakeupException
import ru.otus.otuskotlin.fintrack.api.models.IRequest
import ru.otus.otuskotlin.fintrack.api.models.IResponse
import ru.otus.otuskotlin.fintrack.common.FinContext
import ru.otus.otuskotlin.fintrack.kafka.config.AppKafkaConfig
import ru.otus.otuskotlin.fintrack.mappers.fromTransport
import ru.otus.otuskotlin.fintrack.mappers.toTransport
import ru.otus.otuskotlin.fintrack.service.operation.OperationService
import java.time.Duration


private val log = KotlinLogging.logger {}

data class InputOutputTopics(val input: String, val output: String, val error: String)
interface ConsumerStrategy {
    fun topics(config: AppKafkaConfig): InputOutputTopics
}

class FintrackKafkaConsumer(
    private val config: AppKafkaConfig,
    private val consumer: KafkaConsumer<String, IRequest>,
    private val responseProducer: KafkaProducer<String, IResponse>,
    private val errorProducer: KafkaProducer<String, String>,
    private val topics: List<ConsumerStrategy>,
    private val service: OperationService
) {
    private val errorTopicsByInput = topics.associate {
        val topics = it.topics(config)
        Pair(
            topics.input,
            topics.error
        )
    }
    private val topicsByInputTopic = topics.associate {
        val topics = it.topics(config)
        Pair(
            topics.input,
            topics.output
        )
    }
    private val process = atomic(true)


    suspend fun run() =
        try {
            while (process.value) {
                val ctx = FinContext(
                    timeStart = Clock.System.now(),
                )
                val records: ConsumerRecords<String, IRequest> = withContext(Dispatchers.IO) {
                    println("Trying to poll")
                    consumer.poll(Duration.ofSeconds(2))
                }
                if (!records.isEmpty)
                    println() { "Receive ${records.count()} messages" }
                records.forEach { record: ConsumerRecord<String, IRequest> ->
                    try {
                        println("process from ${record.topic()}:\n${record.value()}")
                        ctx.fromTransport(record.value())
                        service.process(ctx)
                        val response = ctx.toTransport()
                        println("sending response to ${topicsByInputTopic[record.topic()]}")
                        responseProducer.send(ProducerRecord(topicsByInputTopic[record.topic()], response))
                    } catch (ex: Exception) {
                        log.error(ex) { "error" }
                        errorProducer.send(ProducerRecord(errorTopicsByInput[record.topic()], record.toString()))
                    }
                }
                yield()
            }
        } catch (ex: WakeupException) {
            // ignore for shutdown
        } catch (ex: RuntimeException) {
            // exception handling
            withContext(NonCancellable) {
                throw ex
            }
        } finally {
            withContext(NonCancellable) {
                consumer.close()
            }
        }

    fun stop() {
        println("Kafka module is stopping")
        process.value = false
        responseProducer.flush()
        errorProducer.flush()
    }
}