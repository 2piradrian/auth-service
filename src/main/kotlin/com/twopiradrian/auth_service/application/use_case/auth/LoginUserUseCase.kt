package com.twopiradrian.auth_service.application.use_case.auth

import com.twopiradrian.auth_service.config.helper.AuthHelper
import com.twopiradrian.auth_service.domain.entity.Token
import com.twopiradrian.auth_service.domain.entity.TokenType
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class LoginUserUseCase(
    private val authHelper: AuthHelper,
    private val userRepository: UserRepository
) {

    data class Command(
        val email: String,
        val password: String
    )

    data class Result(
        val user: User,
        val token: Token
    )

    fun execute(command: Command): Result {
        val user = this.userRepository.findByEmail(command.email)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        if (!this.authHelper.validatePassword(user, command.password)) {
            throw ErrorHandler(ErrorType.PASSWORDS_DO_NOT_MATCH)
        }

        user.updateLastLogin()
        this.userRepository.update(user)

        val token = Token(
            accessToken = this.authHelper.createToken(user, TokenType.AUTHENTICATION)
        )

        return Result(
            user = user,
            token = token
        )
    }
}
