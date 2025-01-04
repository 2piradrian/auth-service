package com.twopiradrian.auth_service.domain.dto.user.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.user.request.DeleteUserReq

object DeleteMapper {

    fun toRequest(token: String?): DeleteUserReq {
        return DeleteUserReq.create(
            token
        )
    }

}
