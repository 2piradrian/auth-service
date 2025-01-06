package com.twopiradrian.auth_service.presentation.service

import com.twopiradrian.auth_service.config.helper.AuthHelper
import com.twopiradrian.auth_service.domain.dto.auth.mapper.implementation.AuthMapper
import com.twopiradrian.auth_service.domain.dto.auth.request.AuthUserReq
import com.twopiradrian.auth_service.domain.dto.auth.request.LoginUserReq
import com.twopiradrian.auth_service.domain.dto.auth.request.RegisterUserReq
import com.twopiradrian.auth_service.domain.dto.auth.response.AuthUserRes
import com.twopiradrian.auth_service.domain.dto.auth.response.LoginUserRes
import com.twopiradrian.auth_service.domain.dto.auth.response.RegisterUserRes
import com.twopiradrian.auth_service.domain.dto.user.mapper.UserMapper
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class AuthService(
    private val authHelper: AuthHelper,
    private val userRepository: UserRepository,
) : AuthServiceI {

    override fun auth(dto: AuthUserReq): AuthUserRes {
        val token: String = this.authHelper.validateToken(dto.token)
            ?: throw ErrorHandler(ErrorType.UNAUTHORIZED)

        val subject: String = this.authHelper.getSubject(token)

        val user: User = this.userRepository.findById(subject)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        //return AuthMapper.auth().toResponse(user)
    }

    override fun login(dto: LoginUserReq): LoginUserRes {
        TODO("Not yet implemented")
    }

    override fun register(dto: RegisterUserReq): RegisterUserRes {
        TODO("Not yet implemented")
    }

}