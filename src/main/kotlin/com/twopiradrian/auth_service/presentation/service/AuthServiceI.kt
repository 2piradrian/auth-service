package com.twopiradrian.auth_service.presentation.service

import com.twopiradrian.auth_service.domain.dto.auth.request.AuthUserReq
import com.twopiradrian.auth_service.domain.dto.auth.request.LoginUserReq
import com.twopiradrian.auth_service.domain.dto.auth.request.RegisterUserReq
import com.twopiradrian.auth_service.domain.dto.auth.response.AuthUserRes
import com.twopiradrian.auth_service.domain.dto.auth.response.LoginUserRes
import com.twopiradrian.auth_service.domain.dto.auth.response.RegisterUserRes

interface AuthServiceI {

    fun auth(dto: AuthUserReq): AuthUserRes

    fun login(dto: LoginUserReq): LoginUserRes

    fun register(dto: RegisterUserReq): RegisterUserRes

    // refresh token

}