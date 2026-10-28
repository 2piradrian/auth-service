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

class RegisterUserUseCaseTest {

    private val authHelper: AuthHelper = mockk()
    private val emailHelper: EmailHelper = mockk()
    private val emailService: EmailService = mockk(relaxed = true)
    private val userRepository: UserRepository = mockk()

    private lateinit var useCase: RegisterUserUseCase

    @BeforeEach
    fun setUp() {
        useCase = RegisterUserUseCase(
            authHelper = authHelper,
            emailHelper = emailHelper,
            emailService = emailService,
            userRepository = userRepository
        )
    }

    @Test
    fun `should successfully register user when data is valid and unique`() {
        val command = RegisterUserUseCase.Command(
            username = "NewUser",
            email = "newuser@example.com",
            password = "SecurePassword123@"
        )

        every { userRepository.findByEmail(command.email) } returns null
        every { userRepository.findByUsername(command.username) } returns null
        every { authHelper.hashPassword(command.password) } returns "hashed_password"
        every { userRepository.save(any()) } answers { firstArg<User>().apply {
            // simulate saved user with id
        }}
        every { authHelper.createToken(any(), TokenType.EMAIL_VALIDATION) } returns "email_token"
        every { emailHelper.verifyEmailHTML("email_token") } returns "<html>Verify</html>"

        val result = useCase.execute(command)

        assertNotNull(result)
        assertEquals(command.username, result.user.getUsername())
        assertEquals(command.email, result.user.getEmail())
        assertEquals(Status.PENDING, result.user.getStatus())
        assertTrue(result.user.getRoles().contains(Role.USER))

        verify(exactly = 1) { userRepository.save(any()) }
        verify(exactly = 1) { emailService.sendEmail(command.email, "Email Validation", "<html>Verify</html>") }
    }

    @Test
    fun `should throw EMAIL_ALREADY_EXISTS when email is already registered`() {
        val command = RegisterUserUseCase.Command(
            username = "NewUser",
            email = "existing@example.com",
            password = "SecurePassword123@"
        )
        val existingUser = User("1", "OldUser", "existing@example.com", "pass", setOf(Role.USER), Status.ACTIVE, LocalDateTime.now(), null)
        every { userRepository.findByEmail(command.email) } returns existingUser

        val exception = assertThrows(ErrorHandler::class.java) {
            useCase.execute(command)
        }

        assertEquals(ErrorType.EMAIL_ALREADY_EXISTS.getMessage(), exception.message)
        assertEquals(ErrorType.EMAIL_ALREADY_EXISTS.getHttpCode(), exception.getHttpCode())
        verify(exactly = 0) { userRepository.save(any()) }
    }

    @Test
    fun `should throw USERNAME_ALREADY_EXISTS when username is already taken`() {
        val command = RegisterUserUseCase.Command(
            username = "ExistingUsername",
            email = "newemail@example.com",
            password = "SecurePassword123@"
        )
        val existingUser = User("2", "ExistingUsername", "other@example.com", "pass", setOf(Role.USER), Status.ACTIVE, LocalDateTime.now(), null)
        every { userRepository.findByEmail(command.email) } returns null
        every { userRepository.findByUsername(command.username) } returns existingUser

        val exception = assertThrows(ErrorHandler::class.java) {
            useCase.execute(command)
        }

        assertEquals(ErrorType.USERNAME_ALREADY_EXISTS.getMessage(), exception.message)
        assertEquals(ErrorType.USERNAME_ALREADY_EXISTS.getHttpCode(), exception.getHttpCode())
        verify(exactly = 0) { userRepository.save(any()) }
    }
}
