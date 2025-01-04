package com.twopiradrian.auth_service.domain.entity

import java.time.LocalDateTime

data class User (

    val id: String,

    val username: String,

    val email: String,

    val password: String,

    val roles: Set<Role>,

    val status: Status,

    val createdAt: LocalDateTime,

    val lastLogin: LocalDateTime?

)