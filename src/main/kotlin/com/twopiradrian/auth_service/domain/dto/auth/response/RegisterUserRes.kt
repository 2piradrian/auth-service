package com.twopiradrian.auth_service.domain.dto.auth.response

import com.twopiradrian.auth_service.domain.entity.Role
import java.time.LocalDateTime

data class RegisterUserRes (

    val userId: String,

    val username: String,

    val email: String,

    val roles: Set<Role>,

    val memberSince: LocalDateTime,

    val lastLogin: LocalDateTime?,

)
