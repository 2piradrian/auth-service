package com.twopiradrian.auth_service.presentation.dto.auth.response

import com.twopiradrian.auth_service.domain.entity.Role
import java.time.LocalDateTime

data class RegisterUserRes(
    val id: String,
    val username: String,
    val email: String,
    val roles: Set<Role>,
    val createdAt: LocalDateTime,
    val lastLogin: LocalDateTime?
)
