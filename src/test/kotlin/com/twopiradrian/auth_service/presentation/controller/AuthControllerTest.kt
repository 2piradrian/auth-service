package com.twopiradrian.auth_service.presentation.controller

import com.twopiradrian.auth_service.application.service.AuthServiceI
import com.twopiradrian.auth_service.domain.entity.Role
import com.twopiradrian.auth_service.domain.entity.Status
import com.twopiradrian.auth_service.domain.entity.Token
import com.twopiradrian.auth_service.presentation.dto.auth.response.AuthenticateUserRes
import com.twopiradrian.auth_service.presentation.dto.auth.response.LoginUserRes
import com.twopiradrian.auth_service.presentation.dto.auth.response.RegisterUserRes
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class AuthControllerUnitTest {

    private val authService: AuthServiceI = mockk()
    private lateinit var controller: AuthController

    @BeforeEach
    fun setUp() {
        controller = AuthController(authService)
    }

    @Test
    fun `register should call authService and return 200 with RegisterUserRes`() {
        val payload = mapOf(
            "username" to "ValidUser",
            "email" to "valid@example.com",
            "password" to "ValidPass123@"
        )
        val responseDto = RegisterUserRes(
            id = "user-id-1",
            username = "ValidUser",
            email = "valid@example.com",
            roles = setOf(Role.USER),
            createdAt = LocalDateTime.now(),
            lastLogin = null
        )
        every { authService.register(any()) } returns responseDto

        val response = controller.register(payload)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(responseDto, response.body)
        verify(exactly = 1) { authService.register(any()) }
    }

    @Test
    fun `login should call authService and return 200 with LoginUserRes`() {
        val payload = mapOf(
            "email" to "valid@example.com",
            "password" to "ValidPass123@"
        )
        val responseDto = LoginUserRes(
            token = Token("token_abc"),
            status = Status.ACTIVE,
            email = "valid@example.com"
        )
        every { authService.login(any()) } returns responseDto

        val response = controller.login(payload)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(responseDto, response.body)
        verify(exactly = 1) { authService.login(any()) }
    }

    @Test
    fun `authenticate should pass token to authService and return 200`() {
        val responseDto = AuthenticateUserRes(
            id = "user-id-1",
            email = "user@example.com",
            roles = setOf(Role.USER)
        )
        every { authService.authenticate(any()) } returns responseDto

        val response = controller.authenticate("Bearer test_token")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(responseDto, response.body)
        verify(exactly = 1) { authService.authenticate(any()) }
    }

    @Test
    fun `verifyEmail should invoke service and return SUCCESS`() {
        every { authService.verifyEmail(any()) } just runs

        val response = controller.verifyEmail("token_val_123")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(com.twopiradrian.auth_service.domain.messages.OtherMessages.SUCCESS, response.body)
        verify(exactly = 1) { authService.verifyEmail(any()) }
    }

    @Test
    fun `resendVerifyEmail should invoke service and return SUCCESS`() {
        val payload = mapOf("email" to "user@example.com")
        every { authService.resendVerifyEmail(any()) } just runs

        val response = controller.resendVerifyEmail(payload)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(com.twopiradrian.auth_service.domain.messages.OtherMessages.SUCCESS, response.body)
        verify(exactly = 1) { authService.resendVerifyEmail(any()) }
    }
}
