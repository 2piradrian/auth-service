package com.twopiradrian.auth_service.application.use_case.user

import com.twopiradrian.auth_service.config.helper.AuthHelper
import com.twopiradrian.auth_service.domain.entity.Role
import com.twopiradrian.auth_service.domain.entity.TokenType
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class SetUserRolesUseCase(
    private val authHelper: AuthHelper,
    private val userRepository: UserRepository
) {

    data class Command(
        val token: String,
        val userId: String,
        val roles: List<String>
    )

    fun execute(command: Command) {
        val validatedToken: String = this.authHelper.validateToken(command.token, TokenType.AUTHENTICATION)
            ?: throw ErrorHandler(ErrorType.UNAUTHORIZED)

        val subject: String = this.authHelper.getSubject(validatedToken, TokenType.AUTHENTICATION)

        val admin: User = this.userRepository.findById(subject)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        if (!admin.getRoles().contains(Role.ADMIN)) {
            throw ErrorHandler(ErrorType.UNAUTHORIZED)
        }

        for (role in command.roles) {
            if (Role.entries.find { it.name == role } == null) {
                throw ErrorHandler(ErrorType.INVALID_FIELDS)
            }
        }

        val rolesSet = command.roles.map { Role.valueOf(it) }.toSet()

        val user: User = this.userRepository.findById(command.userId)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        user.updateRoles(rolesSet)
        this.userRepository.update(user)
    }
}
