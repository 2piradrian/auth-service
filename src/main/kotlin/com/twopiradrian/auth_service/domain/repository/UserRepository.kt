package com.twopiradrian.auth_service.domain.repository

import com.twopiradrian.auth_service.domain.entity.Role
import com.twopiradrian.auth_service.domain.entity.User

interface UserRepository {

    fun findById(id: String): User?
    fun findByEmail(email: String): User?
    fun findByUsername(username: String): User?

    fun save(user: User): User
    fun update(user: User): User

    fun deleteById(id: Long)

    fun changeStatus(id: String, status: String): User
    fun changePassword(id: String, password: String): User
    fun changeRoles(id: String, roles: Set<Role>): User

}