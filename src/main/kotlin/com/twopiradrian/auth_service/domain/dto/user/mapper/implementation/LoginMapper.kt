package com.twopiradrian.auth_service.domain.dto.user.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.user.request.LoginUserReq
import com.twopiradrian.auth_service.domain.dto.user.response.LoginUserRes
import com.twopiradrian.auth_service.domain.entity.Token

object LoginMapper {

    fun toRequest(payload: Map<String?, Any?>): LoginUserReq {
        return LoginUserReq.create(
            payload["email"] as String?,
            payload["password"] as String?
        )
    }

    fun toResponse(token: Token?): LoginUserRes {
        return LoginUserRes(
            token
        )
    }

}
