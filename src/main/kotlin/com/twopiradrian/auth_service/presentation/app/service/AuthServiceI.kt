package com.twopiradrian.auth_service.presentation.app.service

import com.twopiradrian.auth_service.domain.dto.auth.request.*
import com.twopiradrian.auth_service.domain.dto.auth.response.AuthenticateUserRes
import com.twopiradrian.auth_service.domain.dto.auth.response.LoginUserRes
import com.twopiradrian.auth_service.domain.dto.auth.response.RegisterUserRes

interface AuthServiceI {

    fun authenticate(dto: AuthenticateUserReq): AuthenticateUserRes

    fun login(dto: LoginUserReq): LoginUserRes

    fun register(dto: RegisterUserReq): RegisterUserRes

    fun verifyEmail(dto: VerifyEmailReq)

    fun resendVerifyEmail(dto: ResendEmailReq)

}