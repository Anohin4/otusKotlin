package ru.otus.otuskotlin.fintrack.app.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import org.slf4j.event.Level
import ru.otus.otuskotlin.fintrack.api.apiMapper

public fun Application.configureMapping() {
    install(ContentNegotiation) {
        json(apiMapper)
    }
}

public fun Application.configureLogging() {
    install(CallLogging) {
        level = Level.INFO
    }
}




