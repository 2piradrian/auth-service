package com.twopiradrian.auth_service.domain.dto.user.mapper

import com.twopiradrian.auth_service.domain.dto.user.mapper.implementation.*

object UserMapper {

    fun getById() = GetByIdMapper

    fun delete(): DeleteMapper = DeleteMapper

}
