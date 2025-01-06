package com.twopiradrian.auth_service.domain.dto.user.request

import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType

class DeleteUserReq private constructor(
    val token: String
) {
    companion object {
        fun create(
            token: String?
        ): DeleteUserReq {

            if (token.isNullOrEmpty()) {
                throw ErrorHandler(ErrorType.UNAUTHORIZED)
            }

            return DeleteUserReq(token)
        }
    }
}
