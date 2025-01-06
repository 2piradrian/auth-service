package com.twopiradrian.auth_service.domain.dto.user.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.user.request.GetUserByIdReq
import com.twopiradrian.auth_service.domain.dto.user.response.GetUserByIdRes
import com.twopiradrian.auth_service.domain.entity.User

class GetByIdMapper {

    companion object {
        fun toRequest(userId: String?): GetUserByIdReq {
            return GetUserByIdReq.create(
                userId
            )
        }

        fun toResponse(user: User): GetUserByIdRes {
            return GetUserByIdRes(
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
