package ru.otus.otuskotlin.fintrack.mappers.exceptions

import ru.otus.otuskotlin.fintrack.common.models.FinCommand

class UnknownCommandException(command: FinCommand) : Throwable("Wrong command $command at mapping toTransport stage")
