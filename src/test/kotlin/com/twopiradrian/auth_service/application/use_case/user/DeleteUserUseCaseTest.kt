package com.twopiradrian.auth_service.application.use_case.user

import com.twopiradrian.auth_service.config.helper.AuthHelper
import com.twopiradrian.auth_service.domain.entity.Role
import com.twopiradrian.auth_service.domain.entity.Status
import com.twopiradrian.auth_service.domain.entity.TokenType
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class DeleteUserUseCaseTest {

    private val authHelper: AuthHelper = mockk()
    private val userRepository: UserRepository = mockk()

    private lateinit var useCase: DeleteUserUseCase

    @BeforeEach
    fun setUp() {
        useCase = DeleteUserUseCase(authHelper, userRepository)
    }

    @Test
    fun `should mark user as DELETED`() {
        val token = "user_token"
        val userId = "user-123"
        val user = User(userId, "ActiveUser", "user@example.com", "pass", setOf(Role.USER), Status.ACTIVE, LocalDateTime.now(), null)

        every { authHelper.validateToken(token, TokenType.AUTHENTICATION) } returns token
        every { authHelper.getSubject(token, TokenType.AUTHENTICATION) } returns userId
        every { userRepository.findById(userId) } returns user
        every { userRepository.update(user) } returns user

        assertDoesNotThrow {
            useCase.execute(DeleteUserUseCase.Command(token = token))
        }

        assertEquals(Status.DELETED, user.getStatus())
        verify(exactly = 1) { userRepository.update(user) }
    }

    @Test
    fun `should throw UNAUTHORIZED when token is invalid`() {
        every { authHelper.validateToken("bad_token", TokenType.AUTHENTICATION) } returns null

        val ex = assertThrows(ErrorHandler::class.java) {
            useCase.execute(DeleteUserUseCase.Command(token = "bad_token"))
        }

        assertEquals(ErrorType.UNAUTHORIZED.getMessage(), ex.message)
    }
}
