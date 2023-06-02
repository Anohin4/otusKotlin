package ru.otus.otuskotlin.fintrack.ktor.plugin.koin

import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

class KoinRequestScope : KoinScopeComponent {
    override val scope: Scope by lazy { createScope(this) }
}