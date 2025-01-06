package com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.auth.request.RegisterUserReq
import com.twopiradrian.auth_service.domain.dto.auth.response.RegisterUserRes
import com.twopiradrian.auth_service.domain.entity.User

class RegisterMapper {

    companion object {
        fun toRequest(payload: Map<String?, Any?>): RegisterUserReq {
            return RegisterUserReq.create(
                payload["username"] as String?,
                payload["password"] as String?,
                payload["email"] as String?
            )
        }

        fun toResponse(user: User): RegisterUserRes {
            return RegisterUserRes(
                user.getId()!!,
                user.getUsername(),
                user.getEmail(),
                user.getRoles(),
                user.getCreatedAt(),
                user.getLastLogin()
            )
        }
    }

}
