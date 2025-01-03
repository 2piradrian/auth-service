package com.twopiradrian.auth_service.domain.entity

import java.time.LocalDateTime

data class User (
    private val id: Long,
    private val username: String,
    private val email: String,
    private val password: String,
    private val role: Set<Role>,
    private val status: Status,
    private val createdAt: LocalDateTime,
    private val lastLogin: LocalDateTime
)