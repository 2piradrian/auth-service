package com.twopiradrian.auth_service.application.service

import com.twopiradrian.auth_service.application.use_case.auth.*
import com.twopiradrian.auth_service.presentation.dto.auth.mapper.*
import com.twopiradrian.auth_service.presentation.dto.auth.request.*
import com.twopiradrian.auth_service.presentation.dto.auth.response.*
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticateUseCase: AuthenticateUserUseCase,
    private val loginUseCase: LoginUserUseCase,
    private val registerUseCase: RegisterUserUseCase,
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val resendVerifyEmailUseCase: ResendVerifyEmailUseCase
) : AuthServiceI {

    override fun authenticate(dto: AuthenticateUserReq): AuthenticateUserRes {
        val result = this.authenticateUseCase.execute(
            command = AuthenticateUserUseCase.Command(
                token = dto.token!!
            )
        )
        return AuthenticateUserMapper.toResponse(result.user)
    }

    override fun login(dto: LoginUserReq): LoginUserRes {
        val result = this.loginUseCase.execute(
            command = LoginUserUseCase.Command(
                email = dto.email!!,
                password = dto.password!!
            )
        )
        return LoginUserMapper.toResponse(
            user = result.user,
            token = result.token
        )
    }

    override fun register(dto: RegisterUserReq): RegisterUserRes {
        val result = this.registerUseCase.execute(
            command = RegisterUserUseCase.Command(
                username = dto.username!!,
                password = dto.password!!,
                email = dto.email!!
            )
        )
        return RegisterUserMapper.toResponse(result.user)
    }

    override fun verifyEmail(dto: VerifyEmailReq) {
        this.verifyEmailUseCase.execute(
            command = VerifyEmailUseCase.Command(
                token = dto.token!!
            )
        )
    }

    override fun resendVerifyEmail(dto: ResendEmailReq) {
        this.resendVerifyEmailUseCase.execute(
            command = ResendVerifyEmailUseCase.Command(
                email = dto.email!!
            )
        )
    }
}
