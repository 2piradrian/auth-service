package com.twopiradrian.auth_service.presentation.dto.user.mapper

import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.presentation.dto.user.request.GetUserByUsernameReq
import com.twopiradrian.auth_service.presentation.dto.user.response.GetUserByUsernameRes

object GetUserByUsernameMapper {

    fun toRequest(username: String?): GetUserByUsernameReq {
        return GetUserByUsernameReq(
            username = username
        )
    }

    fun toResponse(user: User): GetUserByUsernameRes {
        return GetUserByUsernameRes(
            id = user.getId()!!,
            username = user.getUsername(),
            email = user.getEmail(),
            roles = user.getRoles(),
            memberSince = user.getCreatedAt(),
            lastLogin = user.getLastLogin()
        )
    }

}
