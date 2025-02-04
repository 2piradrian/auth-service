package com.twopiradrian.auth_service.presentation.app.service

import com.twopiradrian.auth_service.domain.dto.user.request.DeleteUserReq
import com.twopiradrian.auth_service.domain.dto.user.request.GetUserByIdReq
import com.twopiradrian.auth_service.domain.dto.user.request.GetUserByUsernameReq
import com.twopiradrian.auth_service.domain.dto.user.request.SetUserRolesReq
import com.twopiradrian.auth_service.domain.dto.user.response.GetUserByIdRes
import com.twopiradrian.auth_service.domain.dto.user.response.GetUserByUsernameRes

interface UserServiceI {

    fun getById(dto: GetUserByIdReq): GetUserByIdRes

    fun getByUsername(dto: GetUserByUsernameReq): GetUserByUsernameRes

    fun setRoles(dto: SetUserRolesReq)

    fun delete(dto: DeleteUserReq)

}