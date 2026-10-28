package com.twopiradrian.auth_service.presentation.dto.auth.mapper

import com.twopiradrian.auth_service.presentation.dto.auth.request.ResendEmailReq

object ResendEmailMapper {

    fun toRequest(payload: Map<String, Any>): ResendEmailReq {
        return ResendEmailReq(
            email = payload["email"] as? String
        )
    }

}
