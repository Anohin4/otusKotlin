package ru.otus.otuskotlin.fintrack.app.plugins

import io.ktor.server.application.*
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import ru.otus.otuskotlin.fintrack.app.kafka.FintrackKafkaConsumer


fun Application.configureKafka() {
    val kafkaConsumer by inject<FintrackKafkaConsumer>()
    launch { kafkaConsumer.run() }
}