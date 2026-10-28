package com.twopiradrian.auth_service.presentation.dto.auth.mapper

import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.presentation.dto.auth.request.RegisterUserReq
import com.twopiradrian.auth_service.presentation.dto.auth.response.RegisterUserRes

object RegisterUserMapper {

    fun toRequest(payload: Map<String, Any>): RegisterUserReq {
        return RegisterUserReq(
            username = payload["username"] as? String,
            password = payload["password"] as? String,
            email = payload["email"] as? String
        )
    }

    fun toResponse(user: User): RegisterUserRes {
        return RegisterUserRes(
            id = user.getId()!!,
            username = user.getUsername(),
            email = user.getEmail(),
            roles = user.getRoles(),
            createdAt = user.getCreatedAt(),
            lastLogin = user.getLastLogin()
        )
    }

}
