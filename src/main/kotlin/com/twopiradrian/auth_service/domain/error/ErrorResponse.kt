package com.twopiradrian.auth_service.domain.error

class ErrorResponse(errorHandler: ErrorHandler) {

    var message: String? = errorHandler.message

}