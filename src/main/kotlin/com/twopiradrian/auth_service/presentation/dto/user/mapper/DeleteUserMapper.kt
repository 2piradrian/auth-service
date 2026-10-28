package com.twopiradrian.auth_service.presentation.dto.user.mapper

import com.twopiradrian.auth_service.presentation.dto.user.request.DeleteUserReq

object DeleteUserMapper {

    fun toRequest(token: String?): DeleteUserReq {
        return DeleteUserReq(
            token = token
        )
    }

}
