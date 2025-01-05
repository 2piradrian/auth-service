package com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.auth.request.AuthUserReq
import com.twopiradrian.auth_service.domain.dto.auth.response.AuthUserRes
import com.twopiradrian.auth_service.domain.entity.User

object AuthMapper {

    fun toRequest(token: String?): AuthUserReq {
        return AuthUserReq.create(
            token
        )
    }

    fun toResponse(user: User): AuthUserRes {
        return AuthUserRes(
            user.id,
            user.email,
            user.roles
        )
    }

}
