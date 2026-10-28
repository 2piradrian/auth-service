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
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ResendVerifyEmailUseCaseTest {

    private val authHelper: AuthHelper = mockk()
    private val emailHelper: EmailHelper = mockk()
    private val emailService: EmailService = mockk(relaxed = true)
    private val userRepository: UserRepository = mockk()

    private lateinit var useCase: ResendVerifyEmailUseCase

    @BeforeEach
    fun setUp() {
        useCase = ResendVerifyEmailUseCase(
            authHelper = authHelper,
            emailHelper = emailHelper,
            emailService = emailService,
            userRepository = userRepository
        )
    }

    @Test
    fun `should resend verification email for pending user`() {
        val email = "pending@example.com"
        val user = User("uid", "PendingUser", email, "pass", setOf(Role.USER), Status.PENDING, LocalDateTime.now(), null)

        every { userRepository.findByEmail(email) } returns user
        every { authHelper.createToken(user, TokenType.EMAIL_VALIDATION) } returns "newToken"
        every { emailHelper.verifyEmailHTML("newToken") } returns "<html>Verify</html>"

        assertDoesNotThrow {
            useCase.execute(ResendVerifyEmailUseCase.Command(email = email))
        }

        verify(exactly = 1) { emailService.sendEmail(email, "Email Verification", "<html>Verify</html>") }
    }

    @Test
    fun `should throw USER_ALREADY_ACTIVATED if user is active`() {
        val email = "active@example.com"
        val user = User("uid", "ActiveUser", email, "pass", setOf(Role.USER), Status.ACTIVE, LocalDateTime.now(), null)

        every { userRepository.findByEmail(email) } returns user

        val exception = assertThrows(ErrorHandler::class.java) {
            useCase.execute(ResendVerifyEmailUseCase.Command(email = email))
        }

        assertEquals(ErrorType.USER_ALREADY_ACTIVATED.getMessage(), exception.message)
    }
}
