package com.twopiradrian.auth_service.domain.dto.user.mapper.implementation

import com.twopiradrian.auth_service.domain.dto.user.request.DeleteUserReq

class DeleteMapper {

    companion object {
        fun toRequest(token: String?): DeleteUserReq {
            return DeleteUserReq.create(
                token
            )
        }
    }

}
