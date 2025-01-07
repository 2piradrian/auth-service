package com.twopiradrian.auth_service.domain.dto.user.request

import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType

class GetUserByUsernameReq private constructor(
    val username: String
) {
    companion object {
        fun create(
            username: String?
        ): GetUserByUsernameReq {

            if (username.isNullOrEmpty()) {
                throw ErrorHandler(ErrorType.MISSING_REQUIRED_FIELDS)
            }

            return GetUserByUsernameReq(username)
        }
    }
}
