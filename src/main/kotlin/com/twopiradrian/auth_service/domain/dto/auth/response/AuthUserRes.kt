package com.twopiradrian.auth_service.domain.dto.auth.response

import com.twopiradrian.auth_service.domain.entity.Role

data class AuthUserRes (

    val id: String,

    val email: String,

    val roles: Set<Role>,

)