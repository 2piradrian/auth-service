package com.twopiradrian.auth_service.domain.dto.user.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.user.request.RegisterUserReq
import com.twopiradrian.auth_service.domain.dto.user.response.RegisterUserRes
import com.twopiradrian.auth_service.domain.entity.User

object RegisterMapper {

    fun toRequest(payload: Map<String?, Any?>): RegisterUserReq {
        return RegisterUserReq.create(
            payload["username"] as String?,
            payload["password"] as String?,
            payload["email"] as String?
        )
    }

    fun toResponse(user: User): RegisterUserRes {
        return RegisterUserRes(
            user.id,
            user.username,
            user.email,
            user.roles,
            user.createdAt,
            user.lastLogin
        )
    }

}
