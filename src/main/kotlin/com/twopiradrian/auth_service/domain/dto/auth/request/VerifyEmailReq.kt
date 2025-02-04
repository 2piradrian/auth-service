package com.twopiradrian.auth_service.domain.dto.auth.request

import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType

class VerifyEmailReq private constructor(
    val token: String
){
    companion object {
        fun create(
            token: String?
        ): VerifyEmailReq {

            if (token.isNullOrEmpty()) {
                throw ErrorHandler(ErrorType.MISSING_REQUIRED_FIELDS)
            }

            return VerifyEmailReq(token)
        }
    }
}