package com.twopiradrian.auth_service.domain.repository

import com.twopiradrian.auth_service.domain.entity.User

interface UserRepository {

    fun findById(id: String): User?
    fun findByEmail(email: String): User?
    fun findByUsername(username: String): User?

    fun save(user: User): User
    fun update(user: User): User

}