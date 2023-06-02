package ru.otus.otuskotlin.fintrack.common.models

import kotlin.jvm.JvmInline
import kotlin.random.Random

@JvmInline
value class FinRequestId(private val id: String) {

    fun asString(): String = id
    companion object{
        val NONE = FinRequestId("")
        val RANDOM
            get() = FinRequestId(Random.nextInt().toString())
    }
}