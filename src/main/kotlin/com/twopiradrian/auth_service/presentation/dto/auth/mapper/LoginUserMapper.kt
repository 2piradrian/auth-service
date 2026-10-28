package com.twopiradrian.auth_service.presentation.dto.auth.mapper

import com.twopiradrian.auth_service.domain.entity.Token
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.presentation.dto.auth.request.LoginUserReq
import com.twopiradrian.auth_service.presentation.dto.auth.response.LoginUserRes

object LoginUserMapper {

    fun toRequest(payload: Map<String, Any>): LoginUserReq {
        return LoginUserReq(
            email = payload["email"] as? String,
            password = payload["password"] as? String
        )
    }

    fun toResponse(user: User, token: Token): LoginUserRes {
        return LoginUserRes(
            token = token,
            status = user.getStatus(),
            email = user.getEmail()
        )
    }

}
