package com.twopiradrian.auth_service.application.use_case.auth

import com.twopiradrian.auth_service.config.helper.AuthHelper
import com.twopiradrian.auth_service.config.helper.EmailHelper
import com.twopiradrian.auth_service.domain.entity.Status
import com.twopiradrian.auth_service.domain.entity.TokenType
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.repository.UserRepository
import com.twopiradrian.auth_service.presentation.service.EmailService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class ResendVerifyEmailUseCase(
    private val authHelper: AuthHelper,
    private val emailHelper: EmailHelper,
    private val emailService: EmailService,
    private val userRepository: UserRepository
) {

    data class Command(
        val email: String
    )

    fun execute(command: Command) {
        val user: User = this.userRepository.findByEmail(command.email)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        if (user.getStatus() == Status.ACTIVE) {
            throw ErrorHandler(ErrorType.USER_ALREADY_ACTIVATED)
        }

        val token: String = this.authHelper.createToken(user, TokenType.EMAIL_VALIDATION)
        this.emailService.sendEmail(
            to = user.getEmail(),
            subject = "Email Verification",
            text = this.emailHelper.verifyEmailHTML(token)
        )
    }
}
