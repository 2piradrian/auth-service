package com.twopiradrian.auth_service.presentation.controller

import com.twopiradrian.auth_service.application.service.UserServiceI
import com.twopiradrian.auth_service.domain.entity.Role
import com.twopiradrian.auth_service.presentation.dto.user.response.GetUserByIdRes
import com.twopiradrian.auth_service.presentation.dto.user.response.GetUserByUsernameRes
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

class UserControllerUnitTest {

    private val userService: UserServiceI = mockk()
    private lateinit var controller: UserController

    @BeforeEach
    fun setUp() {
        controller = UserController(userService)
    }

    @Test
    fun `getById should call userService and return 200 with user data`() {
        val userRes = GetUserByIdRes(
            id = "user-1",
            username = "UserOne",
            email = "user@example.com",
            roles = setOf(Role.USER),
            memberSince = LocalDateTime.now(),
            lastLogin = null
        )
        every { userService.getById(any()) } returns userRes

        val response = controller.getById("user-1")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(userRes, response.body)
        verify(exactly = 1) { userService.getById(any()) }
    }

    @Test
    fun `getByUsername should call userService and return 200 with user data`() {
        val userRes = GetUserByUsernameRes(
            id = "user-1",
            username = "UserOne",
            email = "user@example.com",
            roles = setOf(Role.USER),
            memberSince = LocalDateTime.now(),
            lastLogin = null
        )
        every { userService.getByUsername(any()) } returns userRes

        val response = controller.getByUsername("UserOne")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(userRes, response.body)
        verify(exactly = 1) { userService.getByUsername(any()) }
    }

    @Test
    fun `setRoles should invoke userService and return 200 OK`() {
        val payload = mapOf(
            "userId" to "target-123",
            "roles" to listOf("USER", "ADMIN")
        )
        every { userService.setRoles(any()) } just runs

        val response = controller.setRoles("Bearer admin_token", payload)

        assertEquals(HttpStatus.OK, response.statusCode)
        verify(exactly = 1) { userService.setRoles(any()) }
    }

    @Test
    fun `delete should invoke userService and return 200 OK`() {
        every { userService.delete(any()) } just runs

        val response = controller.delete("Bearer user_token")

        assertEquals(HttpStatus.OK, response.statusCode)
        verify(exactly = 1) { userService.delete(any()) }
    }
}
