package com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.auth.request.LoginUserReq
import com.twopiradrian.auth_service.domain.dto.auth.response.LoginUserRes
import com.twopiradrian.auth_service.domain.entity.Token

class LoginMapper {

    companion object {
        fun toRequest(payload: Map<String?, Any?>): LoginUserReq {
            return LoginUserReq.create(
                payload["email"] as String?,
                payload["password"] as String?
            )
        }

        fun toResponse(token: Token?): LoginUserRes {
            return LoginUserRes(
                token
            )
        }
    }

}
