package com.twopiradrian.auth_service.domain.dto.auth.request

import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.validator.RegexValidators

class RegisterUserReq private constructor(
    val username: String,
    val password: String,
    val email: String
) {
    companion object {
        fun create(
            username: String?,
            password: String?,
            email: String?
        ): RegisterUserReq {

            if (username.isNullOrEmpty() || password.isNullOrEmpty() || email.isNullOrEmpty()) {
                throw ErrorHandler(ErrorType.MISSING_REQUIRED_FIELDS)
            }

            val usernameValidator: RegexValidators = RegexValidators.USERNAME
            if (!username.matches(usernameValidator.getRegex().toRegex())) {
                throw ErrorHandler(ErrorType.INVALID_USERNAME)
            }

            val passwordValidator: RegexValidators = RegexValidators.PASSWORD
            if (!password.matches(passwordValidator.getRegex().toRegex())) {
                throw ErrorHandler(ErrorType.INVALID_PASSWORD)
            }

            val emailValidator: RegexValidators = RegexValidators.EMAIL
            if (!email.matches(emailValidator.getRegex().toRegex())) {
                throw ErrorHandler(ErrorType.INVALID_EMAIL)
            }

            return RegisterUserReq(username, password, email)
        }
    }
}
