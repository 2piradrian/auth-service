package com.twopiradrian.auth_service.application.use_case.auth

import com.twopiradrian.auth_service.config.helper.AuthHelper
import com.twopiradrian.auth_service.domain.entity.Role
import com.twopiradrian.auth_service.domain.entity.Status
import com.twopiradrian.auth_service.domain.entity.TokenType
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.repository.UserRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class LoginUserUseCaseTest {

    private val authHelper: AuthHelper = mockk()
    private val userRepository: UserRepository = mockk()

    private lateinit var useCase: LoginUserUseCase

    @BeforeEach
    fun setUp() {
        useCase = LoginUserUseCase(
            authHelper = authHelper,
            userRepository = userRepository
        )
    }

    @Test
    fun `should login successfully when credentials are correct`() {
        val command = LoginUserUseCase.Command(
            email = "john@example.com",
            password = "SecretPassword123@"
        )
        val user = User(
            id = "user-123",
            username = "JohnDoe",
            email = "john@example.com",
            password = "hashed_secret",
            roles = setOf(Role.USER),
            status = Status.ACTIVE,
            createdAt = LocalDateTime.now().minusDays(10),
            lastLogin = null
        )

        every { userRepository.findByEmail(command.email) } returns user
        every { authHelper.validatePassword(user, command.password) } returns true
        every { userRepository.update(user) } returns user
        every { authHelper.createToken(user, TokenType.AUTHENTICATION) } returns "valid_access_token"

        val result = useCase.execute(command)

        assertNotNull(result)
        assertEquals(user, result.user)
        assertEquals("valid_access_token", result.token.getAccessToken())
        assertNotNull(user.getLastLogin())

        verify(exactly = 1) { userRepository.update(user) }
        verify(exactly = 1) { authHelper.createToken(user, TokenType.AUTHENTICATION) }
    }

    @Test
    fun `should throw USER_NOT_FOUND when email does not exist`() {
        val command = LoginUserUseCase.Command(
            email = "notfound@example.com",
            password = "Password123@"
        )

        every { userRepository.findByEmail(command.email) } returns null

        val exception = assertThrows(ErrorHandler::class.java) {
            useCase.execute(command)
        }

        assertEquals(ErrorType.USER_NOT_FOUND.getMessage(), exception.message)
        assertEquals(ErrorType.USER_NOT_FOUND.getHttpCode(), exception.getHttpCode())
        verify(exactly = 0) { authHelper.validatePassword(any(), any()) }
    }

    @Test
    fun `should throw PASSWORDS_DO_NOT_MATCH when password is incorrect`() {
        val command = LoginUserUseCase.Command(
            email = "john@example.com",
            password = "WrongPassword123@"
        )
        val user = User("user-123", "JohnDoe", "john@example.com", "hashed_secret", setOf(Role.USER), Status.ACTIVE, LocalDateTime.now(), null)

        every { userRepository.findByEmail(command.email) } returns user
        every { authHelper.validatePassword(user, command.password) } returns false

        val exception = assertThrows(ErrorHandler::class.java) {
            useCase.execute(command)
        }

        assertEquals(ErrorType.PASSWORDS_DO_NOT_MATCH.getMessage(), exception.message)
        assertEquals(ErrorType.PASSWORDS_DO_NOT_MATCH.getHttpCode(), exception.getHttpCode())
        verify(exactly = 0) { userRepository.update(any()) }
    }
}
