package com.twopiradrian.auth_service.domain.dto.auth.mapper

import com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation.LoginMapper
import com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation.RegisterMapper
import com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation.AuthMapper

object AuthMapper {

    fun auth(): AuthMapper = AuthMapper

    fun login(): LoginMapper = LoginMapper

    fun register(): RegisterMapper = RegisterMapper


}