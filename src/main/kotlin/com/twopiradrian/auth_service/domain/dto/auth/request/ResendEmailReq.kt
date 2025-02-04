package com.twopiradrian.auth_service.domain.dto.auth.request

import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType

class ResendEmailReq private constructor(
    val email: String
){
    companion object {
        fun create(
            email: String?
        ): ResendEmailReq {

            if (email.isNullOrEmpty()) {
                throw ErrorHandler(ErrorType.MISSING_REQUIRED_FIELDS)
            }

            return ResendEmailReq(email)
        }
    }
}