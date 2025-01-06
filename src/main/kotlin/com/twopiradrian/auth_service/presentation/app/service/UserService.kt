package com.twopiradrian.auth_service.presentation.app.service

import com.twopiradrian.auth_service.config.helper.AuthHelper
import com.twopiradrian.auth_service.domain.dto.user.mapper.UserMapper
import com.twopiradrian.auth_service.domain.dto.user.request.DeleteUserReq
import com.twopiradrian.auth_service.domain.dto.user.request.GetUserByIdReq
import com.twopiradrian.auth_service.domain.dto.user.response.GetUserByIdRes
import com.twopiradrian.auth_service.domain.entity.Status
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.infrastructure.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService(
    private val authHelper: AuthHelper,
    private val userRepository: UserRepository,
) : UserServiceI {

    override fun getById(dto: GetUserByIdReq): GetUserByIdRes {
        val user: User = this.userRepository.findById(dto.userId)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        return UserMapper.getById().toResponse(user)
    }

    override fun delete(dto: DeleteUserReq) {
        val token: String = this.authHelper.validateToken(dto.token)
            ?: throw ErrorHandler(ErrorType.UNAUTHORIZED)

        val subject: String = this.authHelper.getSubject(token)

        val user: User = this.userRepository.findById(subject)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        user.updateStatus(Status.DELETED)
        this.userRepository.update(user)
    }

}