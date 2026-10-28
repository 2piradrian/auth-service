package com.twopiradrian.auth_service.application.use_case.auth

import com.twopiradrian.auth_service.config.helper.AuthHelper
import com.twopiradrian.auth_service.config.helper.EmailHelper
import com.twopiradrian.auth_service.domain.entity.Role
import com.twopiradrian.auth_service.domain.entity.Status
import com.twopiradrian.auth_service.domain.entity.TokenType
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.repository.UserRepository
import com.twopiradrian.auth_service.presentation.service.EmailService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
@Transactional
class RegisterUserUseCase(
    private val authHelper: AuthHelper,
    private val emailHelper: EmailHelper,
    private val emailService: EmailService,
    private val userRepository: UserRepository
) {

    data class Command(
        val username: String,
        val password: String,
        val email: String
    )

    data class Result(
        val user: User
    )

    fun execute(command: Command): Result {
        val emailCheck: User? = this.userRepository.findByEmail(command.email)
        if (emailCheck != null) throw ErrorHandler(ErrorType.EMAIL_ALREADY_EXISTS)

        val usernameCheck: User? = this.userRepository.findByUsername(command.username)
        if (usernameCheck != null) throw ErrorHandler(ErrorType.USERNAME_ALREADY_EXISTS)

        val user = User(
            id = null,
            username = command.username,
            email = command.email,
            password = this.authHelper.hashPassword(command.password),
            roles = setOf(Role.USER),
            status = Status.PENDING,
            createdAt = LocalDateTime.now(),
            lastLogin = null
        )

        val saved: User = this.userRepository.save(user)

        val token: String = this.authHelper.createToken(saved, TokenType.EMAIL_VALIDATION)
        this.emailService.sendEmail(
            to = saved.getEmail(),
            subject = "Email Validation",
            text = this.emailHelper.verifyEmailHTML(token)
        )

        return Result(user = saved)
    }
}
