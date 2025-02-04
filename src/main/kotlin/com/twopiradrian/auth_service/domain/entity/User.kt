package com.twopiradrian.auth_service.domain.entity

import java.time.LocalDateTime

data class User (
    private var id: String?,
    private var username: String,
    private var email: String,
    private var password: String,
    private var roles: Set<Role>,
    private var status: Status,
    private var createdAt: LocalDateTime,
    private var lastLogin: LocalDateTime?
) {

    fun getId() = id
    fun getUsername() = username
    fun getEmail() = email
    fun getPassword() = password
    fun getRoles() = roles
    fun getStatus() = status
    fun getCreatedAt() = createdAt
    fun getLastLogin() = lastLogin

    fun updateLastLogin() {
        this.lastLogin = LocalDateTime.now()
    }

    fun updateStatus(status: Status) {
        this.status = status
    }

    fun updateRoles(roles: Set<Role>) {
        this.roles = roles
    }

}