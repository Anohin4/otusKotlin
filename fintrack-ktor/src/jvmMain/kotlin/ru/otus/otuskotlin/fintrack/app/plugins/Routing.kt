package ru.otus.otuskotlin.fintrack.app.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.otus.otuskotlin.fintrack.app.controller.*
import ru.otus.otuskotlin.fintrack.ktor.plugin.koin.scope
import ru.otus.otuskotlin.fintrack.service.operation.OperationService
import ru.otus.otuskotlin.fintrack.service.report.ReportService

public fun Application.configureRouting() {
    routing {
        route("/operation") {
            operation()
        }
        route("/report") {
            report()
        }
    }
}

fun Route.operation() {
    val service by inject<OperationService>()
    post("create") { call.operationCreate(call.request.scope.get(), service) }
    post("read") { call.operationRead(call.request.scope.get(), service) }
    post("update") { call.operationUpdate(call.request.scope.get(), service) }
    post("delete") { call.operationDelete(call.request.scope.get(), service) }

}

fun Route.report() {
    val service by inject<ReportService>()
    post { call.report(call.request.scope.get(), service) }
}