package com.twopiradrian.auth_service.application.use_case.user

import com.twopiradrian.auth_service.domain.entity.Role
import com.twopiradrian.auth_service.domain.entity.Status
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class GetUserUseCasesTest {

    private val userRepository: UserRepository = mockk()

    private lateinit var getByIdUseCase: GetUserByIdUseCase
    private lateinit var getByUsernameUseCase: GetUserByUsernameUseCase

    @BeforeEach
    fun setUp() {
        getByIdUseCase = GetUserByIdUseCase(userRepository)
        getByUsernameUseCase = GetUserByUsernameUseCase(userRepository)
    }

    @Test
    fun `GetUserByIdUseCase should return user when exists`() {
        val user = User("uid-1", "UserOne", "one@example.com", "pass", setOf(Role.USER), Status.ACTIVE, LocalDateTime.now(), null)
        every { userRepository.findById("uid-1") } returns user

        val result = getByIdUseCase.execute(GetUserByIdUseCase.Command("uid-1"))
        assertEquals(user, result.user)
    }

    @Test
    fun `GetUserByIdUseCase should throw USER_NOT_FOUND when user does not exist`() {
        every { userRepository.findById("missing-id") } returns null

        val ex = assertThrows(ErrorHandler::class.java) {
            getByIdUseCase.execute(GetUserByIdUseCase.Command("missing-id"))
        }
        assertEquals(ErrorType.USER_NOT_FOUND.getMessage(), ex.message)
    }

    @Test
    fun `GetUserByUsernameUseCase should return user when exists`() {
        val user = User("uid-1", "UserOne", "one@example.com", "pass", setOf(Role.USER), Status.ACTIVE, LocalDateTime.now(), null)
        every { userRepository.findByUsername("UserOne") } returns user

        val result = getByUsernameUseCase.execute(GetUserByUsernameUseCase.Command("UserOne"))
        assertEquals(user, result.user)
    }

    @Test
    fun `GetUserByUsernameUseCase should throw USER_NOT_FOUND when username does not exist`() {
        every { userRepository.findByUsername("Unknown") } returns null

        val ex = assertThrows(ErrorHandler::class.java) {
            getByUsernameUseCase.execute(GetUserByUsernameUseCase.Command("Unknown"))
        }
        assertEquals(ErrorType.USER_NOT_FOUND.getMessage(), ex.message)
    }
}
