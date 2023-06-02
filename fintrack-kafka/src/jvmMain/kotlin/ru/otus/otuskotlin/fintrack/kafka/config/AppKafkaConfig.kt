package ru.otus.otuskotlin.fintrack.kafka.config

class AppKafkaConfig(
    val kafkaHosts: List<String> = KAFKA_HOSTS,
    val kafkaGroupId: String = KAFKA_GROUP_ID,
    val kafkaTopicIn: String = KAFKA_TOPIC_IN,
    val kafkaTopicOut: String = KAFKA_TOPIC_OUT,
    val kafkaClient: String = KAFKA_CLIENT_ID,
    val kafkaErrorTopic: String = KAFKA_ERROR_TOPIC
) {
    companion object {
        private const val KAFKA_HOST_VAR = "KAFKA_HOSTS"
        private const val KAFKA_TOPIC_IN_VAR = "KAFKA_TOPIC_IN"
        private const val KAFKA_TOPIC_OUT_VAR = "KAFKA_TOPIC_OUT"
        private const val KAFKA_GROUP_ID_VAR = "KAFKA_GROUP_ID"
        private const val KAFKA_CLIENT_ID_VAR = "KAFKA_GROUP_ID"
        private const val KAFKA_ERROR_TOPIC_VAR = "KAFKA_TOPIC_ERROR"

        val KAFKA_HOSTS by lazy { (System.getenv(KAFKA_HOST_VAR) ?: "localhost:9094").split("\\s*[,;]\\s*") }
        val KAFKA_GROUP_ID by lazy { System.getenv(KAFKA_GROUP_ID_VAR) ?: "fintrack_01" }
        val KAFKA_TOPIC_IN by lazy { System.getenv(KAFKA_TOPIC_IN_VAR) ?: "fintrackInput" }
        val KAFKA_TOPIC_OUT by lazy { System.getenv(KAFKA_TOPIC_OUT_VAR) ?: "fintrackOutput" }
        val KAFKA_CLIENT_ID by lazy { System.getenv(KAFKA_CLIENT_ID_VAR) ?: "fintrackClient" }
        val KAFKA_ERROR_TOPIC by lazy { System.getenv(KAFKA_ERROR_TOPIC_VAR) ?: "fintrackClient" }
    }
}
