package com.twopiradrian.auth_service.domain.dto.auth.mapper

import com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation.*

class AuthMapper {

    companion object {

        fun authenticate(): AuthenticateMapper.Companion = AuthenticateMapper

        fun login(): LoginMapper.Companion = LoginMapper

        fun register(): RegisterMapper.Companion = RegisterMapper

        fun verifyEmail(): VerifyEmailMapper.Companion = VerifyEmailMapper

        fun resendEmail(): ResendEmailMapper.Companion = ResendEmailMapper

    }

}