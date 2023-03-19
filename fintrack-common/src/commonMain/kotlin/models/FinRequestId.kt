package ru.otus.otuskotlin.fintrack.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class FinRequestId(private val id: String) {
    fun asString(): String = id
    companion object{
        val NONE = FinRequestId("")
    }
}