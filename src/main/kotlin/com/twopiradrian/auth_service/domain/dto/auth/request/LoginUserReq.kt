package com.twopiradrian.auth_service.domain.dto.auth.request

import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.validator.RegexValidators

class LoginUserReq private constructor(
    val email: String,
    val password: String
) {
    companion object {
        fun create(
            email: String?,
            password: String?
        ): LoginUserReq {

            if (password.isNullOrEmpty() || email.isNullOrEmpty()) {
                throw ErrorHandler(ErrorType.MISSING_REQUIRED_FIELDS)
            }

            val passwordValidator: RegexValidators = RegexValidators.PASSWORD
            if (!password.matches(passwordValidator.getRegex().toRegex())) {
                throw ErrorHandler(ErrorType.INVALID_PASSWORD)
            }

            val emailValidator: RegexValidators = RegexValidators.EMAIL
            if (!email.matches(emailValidator.getRegex().toRegex())) {
                throw ErrorHandler(ErrorType.INVALID_EMAIL)
            }

            return LoginUserReq(email, password)
        }
    }
}
