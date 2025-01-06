package com.twopiradrian.auth_service.domain.dto.user.mapper

import com.twopiradrian.auth_service.domain.dto.user.mapper.implementation.*

class UserMapper {

    companion object {

        fun getById(): GetByIdMapper.Companion = GetByIdMapper

        fun delete(): DeleteMapper.Companion = DeleteMapper

    }

}
