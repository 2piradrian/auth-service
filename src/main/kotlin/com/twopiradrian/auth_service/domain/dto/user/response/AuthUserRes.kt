package com.twopiradrian.auth_service.domain.dto.user.response

import com.twopiradrian.auth_service.domain.entity.Role

data class AuthUserRes (

    val id: String,

    val email: String,

    val roles: Set<Role>,

)
