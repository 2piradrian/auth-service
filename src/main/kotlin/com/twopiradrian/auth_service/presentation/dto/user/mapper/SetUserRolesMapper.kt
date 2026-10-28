package com.twopiradrian.auth_service.presentation.dto.user.mapper

import com.twopiradrian.auth_service.presentation.dto.user.request.SetUserRolesReq

object SetUserRolesMapper {

    @Suppress("UNCHECKED_CAST")
    fun toRequest(token: String?, payload: Map<String, Any>): SetUserRolesReq {
        return SetUserRolesReq(
            token = token,
            userId = payload["userId"] as? String,
            roles = payload["roles"] as? List<String>
        )
    }

}
