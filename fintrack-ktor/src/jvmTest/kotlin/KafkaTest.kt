import io.kotest.core.extensions.Extension
import io.kotest.core.extensions.install
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestScope
import io.kotest.extensions.system.OverrideMode
import io.kotest.extensions.system.withEnvironment
import io.kotest.extensions.testcontainers.TestContainerExtension
import io.kotest.extensions.testcontainers.kafka.createStringStringConsumer
import io.kotest.extensions.testcontainers.kafka.createStringStringProducer
import io.kotest.koin.KoinExtension
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName
import ru.otus.otuskotlin.fintrack.app.kafka.FintrackKafkaConsumer
import ru.otus.otuskotlin.fintrack.app.plugins.koin.appModule
import ru.otus.otuskotlin.fintrack.app.plugins.koin.kafkaModule
import ru.otus.otuskotlin.fintrack.kafka.config.AppKafkaConfig
import java.time.Duration
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

var inputTopic = "inputTopic"
var outputTopics = "outputTopic"
class KafkaTest : FunSpec(), KoinTest {
    val kafkaContainer =
        install(TestContainerExtension(KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1")))) {
            withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true")
        }

    val testConfigs = module {
        single<AppKafkaConfig> { AppKafkaConfig(kafkaHosts = listOf(kafkaContainer.bootstrapServers),
            kafkaTopicOut = outputTopics,
            kafkaTopicIn = inputTopic) }
    }

    init {
        val testProducer = kafkaContainer.createStringStringProducer()
        val testConsumer = kafkaContainer.createStringStringConsumer{
            mapOf(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to "myRandomGroup")
        }
        beforeTest {
            testProducer.send(ProducerRecord(outputTopics, "test"))
            testProducer.flush()

            testConsumer.subscribe(listOf(outputTopics))
            //апи кафки не гарантирует доставку с первого раза сообщения, поэтому на всякий случай повторим запрос
            repeat(3) {
                testConsumer.poll(Duration.ofSeconds(1))
            }
        }


        test("Kafka create operation test").config(timeout = 30.seconds) {
            runKafkaTest( testProducer, testConsumer, "create", createRequestAsString())
        }
        test("Kafka read operation test").config(timeout = 30.seconds) {
            runKafkaTest( testProducer, testConsumer, "read",readRequestAsString())
        }
        test("Kafka delete operation test").config(timeout = 30.seconds) {
            runKafkaTest( testProducer, testConsumer, "delete", deleteRequestAsString())
        }
        test("Kafka update operation test").config(timeout = 30.seconds) {
            runKafkaTest( testProducer, testConsumer, "update", updateRequestAsString())
        }
    }

    private suspend fun TestScope.runKafkaTest(
        testProducer: KafkaProducer<String, String>,
        testConsumer: KafkaConsumer<String, String>,
        responseType:String,
        message: String
    ) {
        val kafkaConsumer by inject<FintrackKafkaConsumer>()
        val job = launch {
            kafkaConsumer.run()
        }

        var secondJob = launch {
            delay(1000)
            testProducer.send(ProducerRecord(inputTopic, message))
            kafkaConsumer.stop()
        }

        job.join()
        secondJob.join()

        while (true) {
            var recordsNew = testConsumer.poll(Duration.ofSeconds(2))

            if (!recordsNew.isEmpty) {
                assertEquals(1, recordsNew.count())
                for (consumerRecord in recordsNew.asIterable()) {
                    val value = consumerRecord.value()
                    value.contains("responseType") shouldBe true
                    value.contains(responseType) shouldBe true
                    value.contains("Закупились продуктами на неделю") shouldBe true
                }
                break
            }
        }
    }

    override fun extensions(): List<Extension> {
    return listOf(
        KoinExtension(listOf(appModule, kafkaModule, testConfigs))
    )

}
}
