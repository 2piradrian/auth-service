package com.twopiradrian.auth_service.application.service

import com.twopiradrian.auth_service.application.use_case.user.*
import com.twopiradrian.auth_service.presentation.dto.user.mapper.*
import com.twopiradrian.auth_service.presentation.dto.user.request.*
import com.twopiradrian.auth_service.presentation.dto.user.response.*
import org.springframework.stereotype.Service

@Service
class UserService(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getUserByUsernameUseCase: GetUserByUsernameUseCase,
    private val setUserRolesUseCase: SetUserRolesUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : UserServiceI {

    override fun getById(dto: GetUserByIdReq): GetUserByIdRes {
        val result = this.getUserByIdUseCase.execute(
            command = GetUserByIdUseCase.Command(
                userId = dto.userId!!
            )
        )
        return GetUserByIdMapper.toResponse(result.user)
    }

    override fun getByUsername(dto: GetUserByUsernameReq): GetUserByUsernameRes {
        val result = this.getUserByUsernameUseCase.execute(
            command = GetUserByUsernameUseCase.Command(
                username = dto.username!!
            )
        )
        return GetUserByUsernameMapper.toResponse(result.user)
    }

    override fun setRoles(dto: SetUserRolesReq) {
        this.setUserRolesUseCase.execute(
            command = SetUserRolesUseCase.Command(
                token = dto.token!!,
                userId = dto.userId!!,
                roles = dto.roles!!
            )
        )
    }

    override fun delete(dto: DeleteUserReq) {
        this.deleteUserUseCase.execute(
            command = DeleteUserUseCase.Command(
                token = dto.token!!
            )
        )
    }
}
