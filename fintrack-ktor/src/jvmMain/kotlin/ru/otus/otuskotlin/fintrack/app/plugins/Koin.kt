package ru.otus.otuskotlin.fintrack.app.plugins

import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import ru.otus.otuskotlin.fintrack.common.FinContext
import ru.otus.otuskotlin.fintrack.ktor.plugin.koin.*
import ru.otus.otuskotlin.fintrack.service.operation.OperationService
import ru.otus.otuskotlin.fintrack.service.operation.OperationServiceImpl
import ru.otus.otuskotlin.fintrack.service.report.ReportService
import ru.otus.otuskotlin.fintrack.service.report.ReportServiceImpl


val appModule = module {
    single<OperationService> { OperationServiceImpl() }
    single<ReportService> { ReportServiceImpl() }
    scope<KoinRequestScope> {
        scoped { FinContext() }
    }
}


fun Application.configureKoin() {
    install(Koin) {
        modules(appModule)
    }
    install(RequestScope)
}