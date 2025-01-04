package com.twopiradrian.auth_service.domain.dto.user.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.user.request.GetUserByIdReq
import com.twopiradrian.auth_service.domain.dto.user.response.GetUserByIdRes
import com.twopiradrian.auth_service.domain.entity.User

object GetByIdMapper {
    fun toRequest(userId: String?): GetUserByIdReq {
        return GetUserByIdReq.create(
            userId
        )
    }

    fun toResponse(user: User): GetUserByIdRes {
        return GetUserByIdRes(
            user.id,
            user.username,
            user.email,
            user.roles,
            user.createdAt,
            user.lastLogin
        )
    }
}
