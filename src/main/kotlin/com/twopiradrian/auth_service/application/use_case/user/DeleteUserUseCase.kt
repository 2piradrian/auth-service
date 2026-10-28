package com.twopiradrian.auth_service.application.use_case.user

import com.twopiradrian.auth_service.config.helper.AuthHelper
import com.twopiradrian.auth_service.domain.entity.Status
import com.twopiradrian.auth_service.domain.entity.TokenType
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class DeleteUserUseCase(
    private val authHelper: AuthHelper,
    private val userRepository: UserRepository
) {

    data class Command(
        val token: String
    )

    fun execute(command: Command) {
        val validatedToken: String = this.authHelper.validateToken(command.token, TokenType.AUTHENTICATION)
            ?: throw ErrorHandler(ErrorType.UNAUTHORIZED)

        val subject: String = this.authHelper.getSubject(validatedToken, TokenType.AUTHENTICATION)

        val user: User = this.userRepository.findById(subject)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        user.updateStatus(Status.DELETED)
        this.userRepository.update(user)
    }
}
