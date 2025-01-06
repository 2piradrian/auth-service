package com.twopiradrian.auth_service.presentation.service

import com.twopiradrian.auth_service.config.helper.AuthHelper
import com.twopiradrian.auth_service.domain.dto.auth.mapper.AuthMapper
import com.twopiradrian.auth_service.domain.dto.auth.request.AuthUserReq
import com.twopiradrian.auth_service.domain.dto.auth.request.LoginUserReq
import com.twopiradrian.auth_service.domain.dto.auth.request.RegisterUserReq
import com.twopiradrian.auth_service.domain.dto.auth.response.AuthUserRes
import com.twopiradrian.auth_service.domain.dto.auth.response.LoginUserRes
import com.twopiradrian.auth_service.domain.dto.auth.response.RegisterUserRes
import com.twopiradrian.auth_service.domain.entity.Role
import com.twopiradrian.auth_service.domain.entity.Status
import com.twopiradrian.auth_service.domain.entity.Token
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class AuthService(
    private val authHelper: AuthHelper,
    private val userRepository: UserRepository,
) : AuthServiceI {

    override fun authenticate(dto: AuthUserReq): AuthUserRes {
        val token: String = this.authHelper.validateToken(dto.token)
            ?: throw ErrorHandler(ErrorType.UNAUTHORIZED)

        val subject: String = this.authHelper.getSubject(token)

        val user: User = this.userRepository.findById(subject)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        if (user.getStatus() == Status.PENDING) {
            throw ErrorHandler(ErrorType.USER_NOT_ACTIVATED)
        }

        return AuthMapper().authenticate().toResponse(user)
    }

    override fun login(dto: LoginUserReq): LoginUserRes {
        val user: User = this.userRepository.findByEmail(dto.email)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        if (!this.authHelper.validatePassword(user, dto.password)) {
            throw ErrorHandler(ErrorType.PASSWORDS_DO_NOT_MATCH)
        }

        user.updateLastLogin()
        this.userRepository.update(user)

        val token: Token = this.authHelper.createToken(user)

        return AuthMapper().login().toResponse(token)
    }

    override fun register(dto: RegisterUserReq): RegisterUserRes {
        val emailCheck: User? = this.userRepository.findByEmail(dto.email)
        if (emailCheck != null) throw ErrorHandler(ErrorType.EMAIL_ALREADY_EXISTS)

        val usernameCheck: User? = this.userRepository.findByUsername(dto.username)
        if (usernameCheck != null) throw ErrorHandler(ErrorType.USERNAME_ALREADY_EXISTS)

        val user = User(
            id = null.toString(),
            username = dto.username,
            email = dto.email,
            password = this.authHelper.hashPassword(dto.password),
            roles = setOf(Role.USER),
            status = Status.PENDING,
            createdAt = LocalDateTime.now(),
            lastLogin = null
        )

        val saved: User = this.userRepository.save(user)

        return AuthMapper().register().toResponse(saved)
    }

}