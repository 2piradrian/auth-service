package com.twopiradrian.auth_service.domain.error

enum class ErrorType(
    private val message: String,
    private val httpCode: Int
) {

    MISSING_REQUIRED_FIELDS("Missing required fields", 400),

    USER_NOT_FOUND("User not found", 404),

    UNAUTHORIZED("Unauthorized", 401),
    PASSWORDS_DO_NOT_MATCH("Passwords do not match", 400),

    PASSWORD_TOO_SHORT("Password too short", 400),
    PASSWORD_TOO_LONG("Password too long", 400),
    INVALID_PASSWORD("Invalid password", 400),

    USERNAME_TOO_SHORT("Username too short", 400),
    USERNAME_TOO_LONG("Username too long", 400),
    INVALID_USERNAME("Invalid username", 400),

    EMAIL_TOO_SHORT("Email too short", 400),
    EMAIL_TOO_LONG("Email too long", 400),
    INVALID_EMAIL("Invalid email", 400),

    USERNAME_ALREADY_EXISTS("Username already exists", 400),
    EMAIL_ALREADY_EXISTS("Email already exists", 400),

    INTERNAL_ERROR("Internal error", 500);

    fun getMessage(): String = message
    fun getHttpCode(): Int = httpCode

}