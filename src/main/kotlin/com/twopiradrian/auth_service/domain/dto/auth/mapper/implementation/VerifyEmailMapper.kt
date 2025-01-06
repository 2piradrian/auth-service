package com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.auth.request.VerifyEmailReq

class VerifyEmailMapper {

    companion object {
        fun toRequest(token: String?): VerifyEmailReq {
            return VerifyEmailReq.create(
                token
            )
        }
    }

}