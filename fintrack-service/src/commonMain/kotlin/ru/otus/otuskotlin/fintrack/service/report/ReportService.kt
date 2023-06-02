package ru.otus.otuskotlin.fintrack.service.report

import models.FinReportFilter
import ru.otus.otuskotlin.fintrack.common.models.FinOperation

interface ReportService {
    fun getReport(reportFilter: FinReportFilter):List<FinOperation>;
}