package com.twopiradrian.auth_service.application.use_case.auth

import com.twopiradrian.auth_service.config.helper.AuthHelper
import com.twopiradrian.auth_service.config.helper.EmailHelper
import com.twopiradrian.auth_service.domain.entity.Role
import com.twopiradrian.auth_service.domain.entity.Status
import com.twopiradrian.auth_service.domain.entity.TokenType
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType
import com.twopiradrian.auth_service.domain.repository.UserRepository
import com.twopiradrian.auth_service.presentation.service.EmailService
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class VerifyEmailUseCaseTest {

    private val authHelper: AuthHelper = mockk()
    private val userRepository: UserRepository = mockk()

    private lateinit var useCase: VerifyEmailUseCase

    @BeforeEach
    fun setUp() {
        useCase = VerifyEmailUseCase(
            authHelper = authHelper,
            userRepository = userRepository
        )
    }

    @Test
    fun `should verify email and activate user successfully`() {
        val token = "email_validation_token"
        val userId = "user-123"
        val user = User(userId, "John", "john@example.com", "pass", setOf(Role.USER), Status.PENDING, LocalDateTime.now(), null)

        every { authHelper.validateToken(token, TokenType.EMAIL_VALIDATION) } returns token
        every { authHelper.getSubject(token, TokenType.EMAIL_VALIDATION) } returns userId
        every { userRepository.findById(userId) } returns user
        every { userRepository.update(user) } returns user

        assertDoesNotThrow {
            useCase.execute(VerifyEmailUseCase.Command(token = token))
        }

        assertEquals(Status.ACTIVE, user.getStatus())
        verify(exactly = 1) { userRepository.update(user) }
    }

    @Test
    fun `should throw USER_ALREADY_ACTIVATED when user is already active`() {
        val token = "email_validation_token"
        val userId = "user-123"
        val user = User(userId, "John", "john@example.com", "pass", setOf(Role.USER), Status.ACTIVE, LocalDateTime.now(), null)

        every { authHelper.validateToken(token, TokenType.EMAIL_VALIDATION) } returns token
        every { authHelper.getSubject(token, TokenType.EMAIL_VALIDATION) } returns userId
        every { userRepository.findById(userId) } returns user

        val exception = assertThrows(ErrorHandler::class.java) {
            useCase.execute(VerifyEmailUseCase.Command(token = token))
        }

        assertEquals(ErrorType.USER_ALREADY_ACTIVATED.getMessage(), exception.message)
        assertEquals(ErrorType.USER_ALREADY_ACTIVATED.getHttpCode(), exception.getHttpCode())
        verify(exactly = 0) { userRepository.update(any()) }
    }
}
