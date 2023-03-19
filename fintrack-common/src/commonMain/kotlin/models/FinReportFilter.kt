package models

import ru.otus.otuskotlin.fintrack.common.models.FinCategory
import ru.otus.otuskotlin.fintrack.common.models.FinOperationType

data class FinReportFilter(

    /* Дата окончания отчета, если не указан - берется текущая дата */
    val dateTimeTo: String = "",

    /* Дата начала отчета */
    val dateTimeFrom: String? = null,

    val category: FinCategory? = null,

    /* Наименование операции */
    val name: String? = null,

    /* имя юр лица контрагента */
    val partner: String? = null,

    val opType: FinOperationType? = null

)

