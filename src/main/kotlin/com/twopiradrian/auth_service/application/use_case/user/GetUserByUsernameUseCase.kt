package com.twopiradrian.auth_service.application.use_case.user

import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class GetUserByUsernameUseCase(
    private val userRepository: UserRepository
) {

    data class Command(
        val username: String
    )

    data class Result(
        val user: User
    )

    fun execute(command: Command): Result {
        val user = this.userRepository.findByUsername(command.username)
            ?: throw ErrorHandler(ErrorType.USER_NOT_FOUND)

        return Result(user = user)
    }
}
