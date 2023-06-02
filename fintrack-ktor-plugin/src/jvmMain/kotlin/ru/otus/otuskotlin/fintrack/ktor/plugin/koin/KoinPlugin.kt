package ru.otus.otuskotlin.fintrack.ktor.plugin.koin

import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.request.*
import io.ktor.util.*
import org.koin.core.scope.Scope


    private val requestScopeKey = AttributeKey<Scope>("requestScopeKey")

    val ApplicationRequest.scope
        get() = this.call.attributes[requestScopeKey]

    val RequestScope = createApplicationPlugin(name = "RequestScope") {
        var scope: KoinRequestScope? = null

        //при вызове создаем скоуп, который живет только на время запроса
        on(CallSetup) { call ->
            scope = KoinRequestScope().also {
                call.attributes.put(requestScopeKey, it.scope)
            }
        }

        on(ResponseSent) {
            scope?.closeScope()
        }
    }