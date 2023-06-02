package ru.otus.otuskotlin.fintrack.app.plugins.koin

import io.ktor.server.application.*
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.sour.cabbage.soup.consumer
import org.sour.cabbage.soup.producer
import ru.otus.otuskotlin.fintrack.api.models.IRequest
import ru.otus.otuskotlin.fintrack.api.models.IResponse
import ru.otus.otuskotlin.fintrack.app.kafka.*
import ru.otus.otuskotlin.fintrack.common.FinContext
import ru.otus.otuskotlin.fintrack.kafka.config.AppKafkaConfig
import ru.otus.otuskotlin.fintrack.ktor.plugin.koin.*
import ru.otus.otuskotlin.fintrack.service.operation.OperationService
import ru.otus.otuskotlin.fintrack.service.operation.OperationServiceImpl
import ru.otus.otuskotlin.fintrack.service.report.ReportService
import ru.otus.otuskotlin.fintrack.service.report.ReportServiceImpl



fun Application.configureKoin() {
    install(Koin) {
        modules(listOf(
            appModule,
            kafkaModule,
            configs
        ))
    }
    install(RequestScope)
}

val appModule = module {
    single<OperationService> { OperationServiceImpl() }
    single<ReportService> { ReportServiceImpl() }

    scope<KoinRequestScope> {
        scoped { FinContext() }
    }
}

val configs = module {
    single<AppKafkaConfig> { AppKafkaConfig() }
}