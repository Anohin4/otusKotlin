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
var messageInput = """
    {"requestType":"create","requestId":"12345","debug":null,"operation":{"name":"Кино","amount":150.0,"dateTime":"2005-08-09T18:31:42","opType":"expense","description":"сходили в кино","partner":null,"category":null}}
""".trimIndent()

val messageOutput = """
    {"_":"ru.otus.otuskotlin.fintrack.api.models.OpCreateResponse","responseType":"create","requestId":"12345","result":"error","errors":null,"operation":{"id":"123456","name":"Закупка продуктов","description":"Закупились продуктами на неделю","partner":"VKUSSVIL LLC","amount":5555.53,"dateTime":"2022-07-24T09:36:13","opType":"expense","category":"food"}}
""".trimIndent()

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
        val kafkaConsumer by inject<FintrackKafkaConsumer>()


        test("Kafka create operation").config(timeout = 30.seconds) {
            runKafkaTest(kafkaConsumer, testProducer, testConsumer, "create")
        }
        test("Kafka delete operation").config(timeout = 30.seconds) {
            runKafkaTest(kafkaConsumer, testProducer, testConsumer, "delete")
        }
    }

    private suspend fun TestScope.runKafkaTest(
        kafkaConsumer: FintrackKafkaConsumer,
        testProducer: KafkaProducer<String, String>,
        testConsumer: KafkaConsumer<String, String>,
        responseType:String
    ) {
        val job = launch {
            kafkaConsumer.run()
        }

        var secondJob = launch {
            delay(1000)
            testProducer.send(ProducerRecord(inputTopic, messageInput))
            kafkaConsumer.stop()
        }

        job.join()
        secondJob.join()

        //подписываемся на топик
        testConsumer.subscribe(listOf(outputTopics))
        //регестрируемся в кафке
        testConsumer.poll(Duration.ofSeconds(1))
        //ставим поиск с самого начала
        testConsumer.seekToBeginning(testConsumer.assignment())
        while (true) {
            var recordsNew = testConsumer.poll(Duration.ofSeconds(2))

            if (!recordsNew.isEmpty) {
                assertEquals(1, recordsNew.count())
                for (consumerRecord in recordsNew.asIterable()) {
                    val value = consumerRecord.value()
                    assertTrue(value.contains("responseType"))
                    assertTrue(value.contains(responseType))
                    assertTrue(value.contains("Закупились продуктами на неделю"))
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
