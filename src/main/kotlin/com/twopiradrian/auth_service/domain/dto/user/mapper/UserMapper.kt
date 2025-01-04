package com.twopiradrian.auth_service.domain.dto.user.mapper

import com.twopiradrian.auth_service.domain.dto.user.mapper.implementation.*

object UserMapper {

    fun getById() = GetByIdMapper

    fun register(): RegisterMapper = RegisterMapper

    fun login(): LoginMapper = LoginMapper

    fun auth(): AuthMapper = AuthMapper

    fun delete(): DeleteMapper = DeleteMapper

}
