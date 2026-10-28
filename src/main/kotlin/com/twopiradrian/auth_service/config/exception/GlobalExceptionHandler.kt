package com.twopiradrian.auth_service.config.exception

import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ErrorHandler::class)
    fun handleErrorHandler(e: ErrorHandler): ResponseEntity<*> {
        return ResponseEntity
            .status(e.getHttpCode())
            .body(e.toResponse())
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<*> {
        val error = ErrorHandler(ErrorType.INVALID_FIELDS)

        return ResponseEntity
            .status(error.getHttpCode())
            .body(error.toResponse())
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<*> {
        val error = ErrorHandler(ErrorType.INVALID_FIELDS)

        return ResponseEntity
            .status(error.getHttpCode())
            .body(error.toResponse())
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception?): ResponseEntity<*> {
        val error = ErrorHandler(ErrorType.INTERNAL_ERROR)

        return ResponseEntity
            .status(error.getHttpCode())
            .body(error.toResponse())
    }

}
