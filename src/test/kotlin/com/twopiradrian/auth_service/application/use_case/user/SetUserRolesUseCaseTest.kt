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

class SetUserRolesUseCaseTest {

    private val authHelper: AuthHelper = mockk()
    private val userRepository: UserRepository = mockk()

    private lateinit var useCase: SetUserRolesUseCase

    @BeforeEach
    fun setUp() {
        useCase = SetUserRolesUseCase(authHelper, userRepository)
    }

    @Test
    fun `should successfully update roles when caller is ADMIN`() {
        val token = "admin_token"
        val adminId = "admin-id"
        val targetUserId = "target-id"
        val adminUser = User(adminId, "Admin", "admin@example.com", "pass", setOf(Role.ADMIN), Status.ACTIVE, LocalDateTime.now(), null)
        val targetUser = User(targetUserId, "NormalUser", "user@example.com", "pass", setOf(Role.USER), Status.ACTIVE, LocalDateTime.now(), null)

        every { authHelper.validateToken(token, TokenType.AUTHENTICATION) } returns token
        every { authHelper.getSubject(token, TokenType.AUTHENTICATION) } returns adminId
        every { userRepository.findById(adminId) } returns adminUser
        every { userRepository.findById(targetUserId) } returns targetUser
        every { userRepository.update(targetUser) } returns targetUser

        assertDoesNotThrow {
            useCase.execute(
                SetUserRolesUseCase.Command(
                    token = token,
                    userId = targetUserId,
                    roles = listOf("USER", "ADMIN")
                )
            )
        }

        assertTrue(targetUser.getRoles().contains(Role.ADMIN))
        assertTrue(targetUser.getRoles().contains(Role.USER))
        verify(exactly = 1) { userRepository.update(targetUser) }
    }

    @Test
    fun `should throw UNAUTHORIZED when caller does not have ADMIN role`() {
        val token = "user_token"
        val regularUserId = "reg-id"
        val regularUser = User(regularUserId, "Regular", "reg@example.com", "pass", setOf(Role.USER), Status.ACTIVE, LocalDateTime.now(), null)

        every { authHelper.validateToken(token, TokenType.AUTHENTICATION) } returns token
        every { authHelper.getSubject(token, TokenType.AUTHENTICATION) } returns regularUserId
        every { userRepository.findById(regularUserId) } returns regularUser

        val ex = assertThrows(ErrorHandler::class.java) {
            useCase.execute(
                SetUserRolesUseCase.Command(
                    token = token,
                    userId = "any-target",
                    roles = listOf("ADMIN")
                )
            )
        }

        assertEquals(ErrorType.UNAUTHORIZED.getMessage(), ex.message)
        verify(exactly = 0) { userRepository.update(any()) }
    }

    @Test
    fun `should throw INVALID_FIELDS when role does not exist`() {
        val token = "admin_token"
        val adminId = "admin-id"
        val adminUser = User(adminId, "Admin", "admin@example.com", "pass", setOf(Role.ADMIN), Status.ACTIVE, LocalDateTime.now(), null)

        every { authHelper.validateToken(token, TokenType.AUTHENTICATION) } returns token
        every { authHelper.getSubject(token, TokenType.AUTHENTICATION) } returns adminId
        every { userRepository.findById(adminId) } returns adminUser

        val ex = assertThrows(ErrorHandler::class.java) {
            useCase.execute(
                SetUserRolesUseCase.Command(
                    token = token,
                    userId = "target-id",
                    roles = listOf("NON_EXISTING_ROLE")
                )
            )
        }

        assertEquals(ErrorType.INVALID_FIELDS.getMessage(), ex.message)
    }
}
