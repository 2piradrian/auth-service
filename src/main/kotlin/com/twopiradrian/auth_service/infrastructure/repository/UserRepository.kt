package com.twopiradrian.auth_service.infrastructure.repository

import com.twopiradrian.auth_service.data.sql.mapper.UserEntityMapper
import com.twopiradrian.auth_service.data.sql.model.UserModel
import com.twopiradrian.auth_service.domain.entity.Status
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.repository.UserRepository
import com.twopiradrian.auth_service.infrastructure.datasource.sql.PostgresUserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val userRepository: PostgresUserRepository
) : UserRepository {

    override fun findById(id: String): User? {
        val userModel: UserModel = this.userRepository.findById(id).orElse(null)

        if (userModel.getStatus() == Status.INACTIVE || userModel.getStatus() == Status.DELETED) {
            return null
        }

        return UserEntityMapper.toDomain(userModel)
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