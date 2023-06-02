import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.fintrack.api.apiMapper
import ru.otus.otuskotlin.fintrack.api.models.*

fun createRequestAsString(): String {
    val requestObj = OpCreateRequest(
        requestType = "create",
        requestId = "12345",
        operation = OpCreateRequestObject(
            name = "Кино",
            description = "сходили в кино",
            opType = OperationType.EXPENSE,
            amount = 150.0,
            dateTime = "2005-08-09T18:31:42"
        )
    )
    return apiMapper.encodeToString(requestObj)
}
fun readRequestAsString(): String {
    val requestObj = OpReadRequest(
        requestType = "read",
        requestId = "12345",
        operation = OpReadRequestObject(
            id = "12"
        )
    )
    return apiMapper.encodeToString(requestObj)
}
fun deleteRequestAsString(): String {
    val requestObj = OpDeleteRequest(
        requestType = "delete",
        requestId = "12345",
        operation = OpDeleteRequestObject(
            id = "Кино"
        )
    )
    return apiMapper.encodeToString(requestObj)
}

fun updateRequestAsString(): String {
    val requestObj = OpUpdateRequest(
        requestType = "update",
        requestId = "12345",
        operation = OpUpdateRequestObject(
            name = "Кино",
            description = "сходили в кино",
            opType = OperationType.EXPENSE,
            amount = 150.0,
            dateTime = "2005-08-09T18:31:42"
        )
    )
    return apiMapper.encodeToString(requestObj)
}

fun reportRequestAsString(): String {
    val requestObj = OpReportRequest(
        requestType = "report",
        requestId = "12345",
        opFilter = OpReportFilter(
            name = "Кино",
            dateTimeFrom = "2005-08-09T18:31:42",
            opType = OperationType.EXPENSE
        )
    )
    return apiMapper.encodeToString(requestObj)
}