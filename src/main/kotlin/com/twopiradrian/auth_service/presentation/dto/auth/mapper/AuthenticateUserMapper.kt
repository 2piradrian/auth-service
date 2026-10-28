package com.twopiradrian.auth_service.presentation.dto.auth.mapper

import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.presentation.dto.auth.request.AuthenticateUserReq
import com.twopiradrian.auth_service.presentation.dto.auth.response.AuthenticateUserRes

object AuthenticateUserMapper {

    fun toRequest(token: String?): AuthenticateUserReq {
        return AuthenticateUserReq(
            token = token
        )
    }

    fun toResponse(user: User): AuthenticateUserRes {
        return AuthenticateUserRes(
            id = user.getId()!!,
            email = user.getEmail(),
            roles = user.getRoles()
        )
    }

}
