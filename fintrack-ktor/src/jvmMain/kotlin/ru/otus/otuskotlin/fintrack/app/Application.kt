package ru.otus.otuskotlin.fintrack.app

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.otus.otuskotlin.fintrack.app.plugins.configureKoin
import ru.otus.otuskotlin.fintrack.app.plugins.configureLogging
import ru.otus.otuskotlin.fintrack.app.plugins.configureMapping
import ru.otus.otuskotlin.fintrack.app.plugins.configureRouting


fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureMapping()
    configureLogging()
    configureRouting()
    configureKoin()

}
