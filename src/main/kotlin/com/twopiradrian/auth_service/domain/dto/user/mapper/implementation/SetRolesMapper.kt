package com.twopiradrian.auth_service.domain.dto.user.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.user.request.SetUserRolesReq

class SetRolesMapper {

    companion object {
        fun toRequest(token: String?, payload: Map<String?, Any?>): SetUserRolesReq {
            return SetUserRolesReq.create(
                payload["userId"] as String,
                token,
                payload["roles"] as List<String>
            )
        }
    }

}