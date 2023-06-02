package ru.otus.otuskotlin.fintrack.service.report

import models.FinReportFilter
import ru.otus.otuskotlin.fintrack.common.models.FinOperation
import ru.otus.otuskotlin.fintrack.stubs.FinStub

class ReportServiceImpl : ReportService {
    override fun getReport(reportFilter: FinReportFilter): List<FinOperation> {
        return FinStub.getList()
    }
}