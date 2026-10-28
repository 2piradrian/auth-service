package com.twopiradrian.auth_service.presentation.dto.auth

import com.twopiradrian.auth_service.domain.entity.Role
import com.twopiradrian.auth_service.domain.entity.Status
import com.twopiradrian.auth_service.domain.entity.Token
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.presentation.dto.auth.mapper.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class AuthDtoMappersTest {

    @Test
    fun `RegisterUserMapper should map payload and response correctly`() {
        val payload = mapOf(
            "username" to "TestUser",
            "password" to "Password123@",
            "email" to "test@example.com"
        )
        val req = RegisterUserMapper.toRequest(payload)
        assertEquals("TestUser", req.username)
        assertEquals("Password123@", req.password)
        assertEquals("test@example.com", req.email)

        val user = User("uid", "TestUser", "test@example.com", "hash", setOf(Role.USER), Status.ACTIVE, LocalDateTime.now(), null)
        val res = RegisterUserMapper.toResponse(user)
        assertEquals("uid", res.id)
        assertEquals("TestUser", res.username)
        assertEquals("test@example.com", res.email)
        assertEquals(setOf(Role.USER), res.roles)
    }

    @Test
    fun `LoginUserMapper should map payload and response correctly`() {
        val payload = mapOf(
            "email" to "user@example.com",
            "password" to "pass123"
        )
        val req = LoginUserMapper.toRequest(payload)
        assertEquals("user@example.com", req.email)
        assertEquals("pass123", req.password)

        val user = User("uid", "User", "user@example.com", "pass", setOf(Role.USER), Status.ACTIVE, LocalDateTime.now(), null)
        val token = Token("access_token_123")
        val res = LoginUserMapper.toResponse(user, token)
        assertEquals("user@example.com", res.email)
        assertEquals(Status.ACTIVE, res.status)
        assertEquals("access_token_123", res.token.getAccessToken())
    }

    @Test
    fun `AuthenticateUserMapper should map token and user response`() {
        val req = AuthenticateUserMapper.toRequest("Bearer token123")
        assertEquals("Bearer token123", req.token)

        val user = User("uid", "User", "user@example.com", "pass", setOf(Role.USER), Status.ACTIVE, LocalDateTime.now(), null)
        val res = AuthenticateUserMapper.toResponse(user)
        assertEquals("uid", res.id)
        assertEquals("user@example.com", res.email)
        assertEquals(setOf(Role.USER), res.roles)
    }

    @Test
    fun `VerifyEmailMapper should map token`() {
        val req = VerifyEmailMapper.toRequest("verify_token_123")
        assertEquals("verify_token_123", req.token)
    }

    @Test
    fun `ResendEmailMapper should map payload`() {
        val payload = mapOf("email" to "resend@example.com")
        val req = ResendEmailMapper.toRequest(payload)
        assertEquals("resend@example.com", req.email)
    }
}
