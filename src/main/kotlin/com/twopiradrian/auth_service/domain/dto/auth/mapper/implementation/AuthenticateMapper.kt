package com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.auth.request.AuthenticateUserReq
import com.twopiradrian.auth_service.domain.dto.auth.response.AuthenticateUserRes
import com.twopiradrian.auth_service.domain.entity.User

class AuthenticateMapper {

    companion object {
        fun toRequest(token: String?): AuthenticateUserReq {
            return AuthenticateUserReq.create(
                token
            )
        }

        fun toResponse(user: User): AuthenticateUserRes {
            return AuthenticateUserRes(
                user.getId()!!,
                user.getEmail(),
                user.getRoles(),
            )
        }
    }

}
