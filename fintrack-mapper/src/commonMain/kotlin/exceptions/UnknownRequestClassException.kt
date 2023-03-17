package ru.otus.otuskotlin.fintrack.mappers.exceptions

import kotlin.reflect.KClass

class UnknownRequestClassException(clazz: KClass<*>) : RuntimeException("Class $clazz cannot be mapped to FinContext")
