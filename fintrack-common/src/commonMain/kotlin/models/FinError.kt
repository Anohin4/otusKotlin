package ru.otus.otuskotlin.fintrack.common.models

data class FinError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
