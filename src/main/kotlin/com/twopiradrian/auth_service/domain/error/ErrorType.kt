package com.twopiradrian.auth_service.domain.error

enum class ErrorType(
    private val message: String,
    private val httpCode: Int
) {

    MISSING_REQUIRED_FIELDS("Missing required fields", 400),

    USER_NOT_FOUND("User not found", 404),

    UNAUTHORIZED("Unauthorized", 401),
    PASSWORDS_DO_NOT_MATCH("Passwords do not match", 400),
    USER_NOT_ACTIVATED("User not activated", 400),

    INVALID_PASSWORD("Invalid password", 400),
    INVALID_USERNAME("Invalid username", 400),
    INVALID_EMAIL("Invalid email", 400),

    USERNAME_ALREADY_EXISTS("Username already exists", 400),
    EMAIL_ALREADY_EXISTS("Email already exists", 400),
    USER_ALREADY_ACTIVATED("User already activated", 400),

    INTERNAL_ERROR("Internal error", 500),
    INVALID_FIELDS("Invalid fields", 400);

    fun getMessage(): String = message
    fun getHttpCode(): Int = httpCode

}