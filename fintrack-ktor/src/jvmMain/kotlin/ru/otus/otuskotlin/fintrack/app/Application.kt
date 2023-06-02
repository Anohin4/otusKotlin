package ru.otus.otuskotlin.fintrack.app

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.coroutineScope

import ru.otus.otuskotlin.fintrack.app.kafka.DefaultConsumerStrategy
import ru.otus.otuskotlin.fintrack.app.kafka.FintrackKafkaConsumer
import ru.otus.otuskotlin.fintrack.app.plugins.*
import ru.otus.otuskotlin.fintrack.app.plugins.koin.configureKoin
import ru.otus.otuskotlin.fintrack.kafka.config.AppKafkaConfig
import ru.otus.otuskotlin.fintrack.service.operation.OperationServiceImpl
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)

}


fun Application.module() {
    configureMapping()
    configureLogging()
    configureRouting()
    configureKoin()
    configureKafka()
}



