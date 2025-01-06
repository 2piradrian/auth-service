package com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.auth.request.AuthUserReq
import com.twopiradrian.auth_service.domain.dto.auth.response.AuthUserRes
import com.twopiradrian.auth_service.domain.entity.User

class AuthenticateMapper {

    companion object {
        fun toRequest(token: String?): AuthUserReq {
            return AuthUserReq.create(
                token
            )
        }

        fun toResponse(user: User): AuthUserRes {
            return AuthUserRes(
                user.getId(),
                user.getEmail(),
                user.getRoles(),
            )
        }
    }

}
