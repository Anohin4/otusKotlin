package ru.otus.otuskotlin.fintrack.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class FinOperationId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = FinOperationId("")
    }
}