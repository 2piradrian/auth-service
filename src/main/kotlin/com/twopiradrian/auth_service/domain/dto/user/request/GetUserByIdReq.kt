package com.twopiradrian.auth_service.domain.dto.user.request

import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType

class GetUserByIdReq private constructor(
    val userId: String
) {
    companion object {
        fun create(
            userId: String?
        ): GetUserByIdReq {

            if (userId.isNullOrEmpty()) {
                throw ErrorHandler(ErrorType.MISSING_REQUIRED_FIELDS)
            }

            return GetUserByIdReq(userId)
        }
    }
}
