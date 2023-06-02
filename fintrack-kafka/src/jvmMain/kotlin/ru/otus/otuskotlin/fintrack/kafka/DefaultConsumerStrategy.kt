package ru.otus.otuskotlin.fintrack.app.kafka

import ru.otus.otuskotlin.fintrack.kafka.config.AppKafkaConfig

class DefaultConsumerStrategy : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicIn, config.kafkaTopicOut, config.kafkaErrorTopic)
    }
}