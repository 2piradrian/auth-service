package com.twopiradrian.auth_service.domain.dto.user.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.user.request.GetUserByIdReq
import com.twopiradrian.auth_service.domain.dto.user.request.GetUserByUsernameReq
import com.twopiradrian.auth_service.domain.dto.user.response.GetUserByIdRes
import com.twopiradrian.auth_service.domain.dto.user.response.GetUserByUsernameRes
import com.twopiradrian.auth_service.domain.entity.User

class GetByUsernameMapper {

    companion object {
        fun toRequest(username: String?): GetUserByUsernameReq {
            return GetUserByUsernameReq.create(
                username
            )
        }

        fun toResponse(user: User): GetUserByUsernameRes {
            return GetUserByUsernameRes(
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
