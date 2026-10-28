package com.twopiradrian.auth_service.presentation.dto.auth.mapper

import com.twopiradrian.auth_service.presentation.dto.auth.request.VerifyEmailReq

object VerifyEmailMapper {

    fun toRequest(token: String?): VerifyEmailReq {
        return VerifyEmailReq(
            token = token
        )
    }

}
