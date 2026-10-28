package com.twopiradrian.auth_service.application.use_case.auth

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
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class AuthenticateUserUseCaseTest {

    private val authHelper: AuthHelper = mockk()
    private val userRepository: UserRepository = mockk()

    private lateinit var useCase: AuthenticateUserUseCase

    @BeforeEach
    fun setUp() {
        useCase = AuthenticateUserUseCase(
            authHelper = authHelper,
            userRepository = userRepository
        )
    }

    @Test
    fun `should authenticate successfully when token is valid and user is active`() {
        val rawToken = "Bearer valid_jwt_token"
        val cleanToken = "valid_jwt_token"
        val userId = "user-abc"
        val user = User(userId, "ActiveUser", "user@example.com", "pass", setOf(Role.USER), Status.ACTIVE, LocalDateTime.now(), null)

        every { authHelper.validateToken(rawToken, TokenType.AUTHENTICATION) } returns cleanToken
        every { authHelper.getSubject(cleanToken, TokenType.AUTHENTICATION) } returns userId
        every { userRepository.findById(userId) } returns user

        val result = useCase.execute(AuthenticateUserUseCase.Command(token = rawToken))

        assertNotNull(result)
        assertEquals(user, result.user)
    }

    @Test
    fun `should throw UNAUTHORIZED when token validation fails`() {
        every { authHelper.validateToken("invalid_token", TokenType.AUTHENTICATION) } returns null

        val exception = assertThrows(ErrorHandler::class.java) {
            useCase.execute(AuthenticateUserUseCase.Command(token = "invalid_token"))
        }

        assertEquals(ErrorType.UNAUTHORIZED.getMessage(), exception.message)
        assertEquals(ErrorType.UNAUTHORIZED.getHttpCode(), exception.getHttpCode())
    }

    @Test
    fun `should throw USER_NOT_ACTIVATED when user status is PENDING`() {
        val token = "valid_token"
        val userId = "pending-user"
        val user = User(userId, "PendingUser", "pending@example.com", "pass", setOf(Role.USER), Status.PENDING, LocalDateTime.now(), null)

        every { authHelper.validateToken(token, TokenType.AUTHENTICATION) } returns token
        every { authHelper.getSubject(token, TokenType.AUTHENTICATION) } returns userId
        every { userRepository.findById(userId) } returns user

        val exception = assertThrows(ErrorHandler::class.java) {
            useCase.execute(AuthenticateUserUseCase.Command(token = token))
        }

        assertEquals(ErrorType.USER_NOT_ACTIVATED.getMessage(), exception.message)
        assertEquals(ErrorType.USER_NOT_ACTIVATED.getHttpCode(), exception.getHttpCode())
    }
}
