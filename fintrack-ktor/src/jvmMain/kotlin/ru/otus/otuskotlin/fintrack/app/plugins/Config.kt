package ru.otus.otuskotlin.fintrack.app.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.StringFormat
import kotlinx.serialization.serializer
import org.slf4j.event.Level
import ru.otus.otuskotlin.fintrack.api.apiMapper
import ru.otus.otuskotlin.fintrack.app.controller.*

public fun Application.configureMapping() {
    install(ContentNegotiation) {
        json(apiMapper)
    }
}

public fun Application.configureLogging() {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}

public fun Application.configureRouting() {
    routing {
        route("operation") {
            post("create") {
                call.operationCreate()
            }
            post("read") { call.operationRead() }
            post("update") { call.operationUpdate() }
            post("delete") { call.operationDelete() }
        }
        route("/report") {
            post{ call.report()}
        }
    }
}


