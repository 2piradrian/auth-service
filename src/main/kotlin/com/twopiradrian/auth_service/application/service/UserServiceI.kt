package com.twopiradrian.auth_service.application.service

import com.twopiradrian.auth_service.presentation.dto.user.request.*
import com.twopiradrian.auth_service.presentation.dto.user.response.*
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

@Validated
interface UserServiceI {
    fun getById(@Valid dto: GetUserByIdReq): GetUserByIdRes
    fun getByUsername(@Valid dto: GetUserByUsernameReq): GetUserByUsernameRes
    fun setRoles(@Valid dto: SetUserRolesReq)
    fun delete(@Valid dto: DeleteUserReq)
}
