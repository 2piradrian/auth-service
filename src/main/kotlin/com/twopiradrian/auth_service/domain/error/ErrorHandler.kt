package com.twopiradrian.auth_service.domain.error


class ErrorHandler(errorType: ErrorType) : RuntimeException(errorType.getMessage()) {
    private val httpCode: Int = errorType.getHttpCode()

    fun toResponse(): ErrorResponse {
        return ErrorResponse(this)
    }

    fun getHttpCode(): Int = httpCode

}