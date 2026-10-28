package com.twopiradrian.auth_service.application.service

import com.twopiradrian.auth_service.presentation.dto.auth.request.*
import com.twopiradrian.auth_service.presentation.dto.auth.response.*
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

@Validated
interface AuthServiceI {
    fun authenticate(@Valid dto: AuthenticateUserReq): AuthenticateUserRes
    fun login(@Valid dto: LoginUserReq): LoginUserRes
    fun register(@Valid dto: RegisterUserReq): RegisterUserRes
    fun verifyEmail(@Valid dto: VerifyEmailReq)
    fun resendVerifyEmail(@Valid dto: ResendEmailReq)
}
