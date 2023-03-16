package ru.otus.otuskotlin.fintrack.mappers.v2.exceptions

import kotlin.reflect.KClass

class UnknownRequestClassException(clazz: KClass<*>) : RuntimeException("Class $clazz cannot be mapped to FinContext")
