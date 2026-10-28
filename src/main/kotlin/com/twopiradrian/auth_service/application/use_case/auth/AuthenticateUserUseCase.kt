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
class AuthenticateUserUseCase(
    private val authHelper: AuthHelper,
    private val userRepository: UserRepository
) {

    data class Command(
        val token: String
    )

    data class Result(
        val user: User
    )

    fun execute(command: Command): Result {
        val token = this.authHelper.validateToken(command.token, TokenType.AUTHENTICATION)
            ?: throw ErrorHandler(ErrorType.UNAUTHORIZED)

        val subject = this.authHelper.getSubject(token, TokenType.AUTHENTICATION)

        val user = this.userRepository.findById(subject)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        if (user.getStatus() == Status.PENDING) {
            throw ErrorHandler(ErrorType.USER_NOT_ACTIVATED)
        }

        return Result(user = user)
    }
}
