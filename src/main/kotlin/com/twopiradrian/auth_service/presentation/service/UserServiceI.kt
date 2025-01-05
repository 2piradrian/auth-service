package com.twopiradrian.auth_service.presentation.service

import com.twopiradrian.auth_service.domain.dto.user.request.DeleteUserReq
import com.twopiradrian.auth_service.domain.dto.user.request.GetUserByIdReq
import com.twopiradrian.auth_service.domain.dto.user.response.GetUserByIdRes

interface UserServiceI {

    fun getById(dto: GetUserByIdReq): GetUserByIdRes

    fun delete(dto: DeleteUserReq): Unit

    //getByUsername
    //delete
    //disable
    //changeRoles
    //changePassword
    //validateEmail

}