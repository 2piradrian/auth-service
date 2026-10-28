package com.twopiradrian.auth_service.presentation.dto.user.mapper

import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.presentation.dto.user.request.GetUserByIdReq
import com.twopiradrian.auth_service.presentation.dto.user.response.GetUserByIdRes

object GetUserByIdMapper {

    fun toRequest(userId: String?): GetUserByIdReq {
        return GetUserByIdReq(
            userId = userId
        )
    }

    fun toResponse(user: User): GetUserByIdRes {
        return GetUserByIdRes(
            id = user.getId()!!,
            username = user.getUsername(),
            email = user.getEmail(),
            roles = user.getRoles(),
            memberSince = user.getCreatedAt(),
            lastLogin = user.getLastLogin()
        )
    }

}
