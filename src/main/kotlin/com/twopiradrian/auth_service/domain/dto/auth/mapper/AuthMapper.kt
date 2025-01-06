package com.twopiradrian.auth_service.domain.dto.auth.mapper

import com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation.LoginMapper
import com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation.RegisterMapper
import com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation.AuthenticateMapper

class AuthMapper {

    companion object {

        fun authenticate(): AuthenticateMapper.Companion = AuthenticateMapper

        fun login(): LoginMapper.Companion = LoginMapper

        fun register(): RegisterMapper.Companion = RegisterMapper

    }

}