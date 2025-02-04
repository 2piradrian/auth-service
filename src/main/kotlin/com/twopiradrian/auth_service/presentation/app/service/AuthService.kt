package com.twopiradrian.auth_service.presentation.app.service

import com.twopiradrian.auth_service.config.helper.AuthHelper
import com.twopiradrian.auth_service.config.helper.EmailHelper
import com.twopiradrian.auth_service.domain.dto.auth.mapper.AuthMapper
import com.twopiradrian.auth_service.domain.dto.auth.request.*
import com.twopiradrian.auth_service.domain.dto.auth.response.AuthenticateUserRes
import com.twopiradrian.auth_service.domain.dto.auth.response.LoginUserRes
import com.twopiradrian.auth_service.domain.dto.auth.response.RegisterUserRes
import com.twopiradrian.auth_service.domain.entity.*
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.repository.UserRepository
import com.twopiradrian.auth_service.presentation.service.EmailService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class AuthService(
    private val authHelper: AuthHelper,
    private val emailHelper: EmailHelper,
    private val emailService: EmailService,
    private val userRepository: UserRepository,
) : AuthServiceI {

    override fun authenticate(dto: AuthenticateUserReq): AuthenticateUserRes {
        val token: String = this.authHelper.validateToken(dto.token, TokenType.AUTHENTICATION)
            ?: throw ErrorHandler(ErrorType.UNAUTHORIZED)

        val subject: String = this.authHelper.getSubject(token, TokenType.AUTHENTICATION)

        val user: User = this.userRepository.findById(subject)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        if (user.getStatus() == Status.PENDING) {
            throw ErrorHandler(ErrorType.USER_NOT_ACTIVATED)
        }

        return AuthMapper.authenticate().toResponse(user)
    }

    override fun login(dto: LoginUserReq): LoginUserRes {
        val user: User = this.userRepository.findByEmail(dto.email)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        if (!this.authHelper.validatePassword(user, dto.password)) {
            throw ErrorHandler(ErrorType.PASSWORDS_DO_NOT_MATCH)
        }

        user.updateLastLogin()
        this.userRepository.update(user)

        val token = Token(
            accessToken = this.authHelper.createToken(user, TokenType.AUTHENTICATION)
        )

        return AuthMapper.login().toResponse(user, token)
    }

    override fun register(dto: RegisterUserReq): RegisterUserRes {
        val emailCheck: User? = this.userRepository.findByEmail(dto.email)
        if (emailCheck != null) throw ErrorHandler(ErrorType.EMAIL_ALREADY_EXISTS)

        val usernameCheck: User? = this.userRepository.findByUsername(dto.username)
        if (usernameCheck != null) throw ErrorHandler(ErrorType.USERNAME_ALREADY_EXISTS)

        val user = User(
            id = null,
            username = dto.username,
            email = dto.email,
            password = this.authHelper.hashPassword(dto.password),
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

        return AuthMapper.register().toResponse(saved)
    }

    override fun verifyEmail(dto: VerifyEmailReq) {
        val token: String = this.authHelper.validateToken(dto.token, TokenType.EMAIL_VALIDATION)
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

    override fun resendVerifyEmail(dto: ResendEmailReq) {
        val user: User = this.userRepository.findByEmail(dto.email)
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