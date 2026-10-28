package com.twopiradrian.auth_service.application.use_case.auth

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
class VerifyEmailUseCase(
    private val authHelper: AuthHelper,
    private val userRepository: UserRepository
) {

    data class Command(
        val token: String
    )

    fun execute(command: Command) {
        val token: String = this.authHelper.validateToken(command.token, TokenType.EMAIL_VALIDATION)
            ?: throw ErrorHandler(ErrorType.UNAUTHORIZED)

        val subject: String = this.authHelper.getSubject(token, TokenType.EMAIL_VALIDATION)

        val user: User = this.userRepository.findById(subject)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        if (user.getStatus() == Status.ACTIVE) {
            throw ErrorHandler(ErrorType.USER_ALREADY_ACTIVATED)
        }

        user.updateStatus(Status.ACTIVE)
        this.userRepository.update(user)
    }
}
