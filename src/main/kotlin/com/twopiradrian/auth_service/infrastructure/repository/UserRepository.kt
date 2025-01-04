package com.twopiradrian.auth_service.infrastructure.repository

import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.repository.UserRepository

class UserRepository : UserRepository {

    override fun findById(id: String): User? {
        TODO("Not yet implemented")
    }

    override fun findByEmail(email: String): User? {
        TODO("Not yet implemented")
    }

    override fun findByUsername(username: String): User? {
        TODO("Not yet implemented")
    }

    override fun save(user: User): User {
        TODO("Not yet implemented")
    }

    override fun update(user: User): User {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

}