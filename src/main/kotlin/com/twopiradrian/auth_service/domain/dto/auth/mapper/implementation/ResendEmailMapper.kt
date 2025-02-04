package com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.auth.request.ResendEmailReq

class ResendEmailMapper {

    companion object {
        fun toRequest(payload: Map<String?, Any?>): ResendEmailReq {
            return ResendEmailReq.create(
                payload["email"] as String?
            )
        }
    }

}