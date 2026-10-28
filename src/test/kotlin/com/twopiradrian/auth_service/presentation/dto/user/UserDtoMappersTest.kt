package com.twopiradrian.auth_service.presentation.dto.user

import com.twopiradrian.auth_service.domain.entity.Role
import com.twopiradrian.auth_service.domain.entity.Status
import com.twopiradrian.auth_service.domain.entity.User
import com.twopiradrian.auth_service.presentation.dto.user.mapper.DeleteUserMapper
import com.twopiradrian.auth_service.presentation.dto.user.mapper.GetUserByIdMapper
import com.twopiradrian.auth_service.presentation.dto.user.mapper.GetUserByUsernameMapper
import com.twopiradrian.auth_service.presentation.dto.user.mapper.SetUserRolesMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class UserDtoMappersTest {

    @Test
    fun `GetUserByIdMapper should map request and response correctly`() {
        val req = GetUserByIdMapper.toRequest("user-123")
        assertEquals("user-123", req.userId)

        val now = LocalDateTime.now()
        val user = User("user-123", "UserOne", "user@example.com", "pass", setOf(Role.USER), Status.ACTIVE, now, now)
        val res = GetUserByIdMapper.toResponse(user)

        assertEquals("user-123", res.id)
        assertEquals("UserOne", res.username)
        assertEquals("user@example.com", res.email)
        assertEquals(setOf(Role.USER), res.roles)
        assertEquals(now, res.memberSince)
        assertEquals(now, res.lastLogin)
    }

    @Test
    fun `GetUserByUsernameMapper should map request and response correctly`() {
        val req = GetUserByUsernameMapper.toRequest("cool_user")
        assertEquals("cool_user", req.username)

        val now = LocalDateTime.now()
        val user = User("user-456", "cool_user", "cool@example.com", "pass", setOf(Role.USER, Role.ADMIN), Status.ACTIVE, now, null)
        val res = GetUserByUsernameMapper.toResponse(user)

        assertEquals("user-456", res.id)
        assertEquals("cool_user", res.username)
        assertEquals("cool@example.com", res.email)
        assertEquals(setOf(Role.USER, Role.ADMIN), res.roles)
        assertEquals(now, res.memberSince)
        assertNull(res.lastLogin)
    }

    @Test
    fun `SetUserRolesMapper should map token and payload correctly`() {
        val payload = mapOf(
            "userId" to "target-user-789",
            "roles" to listOf("USER", "ADMIN")
        )
        val token = "Bearer admin_token"
        val req = SetUserRolesMapper.toRequest(token, payload)

        assertEquals("Bearer admin_token", req.token)
        assertEquals("target-user-789", req.userId)
        assertEquals(listOf("USER", "ADMIN"), req.roles)
    }

    @Test
    fun `DeleteUserMapper should map token correctly`() {
        val req = DeleteUserMapper.toRequest("Bearer user_token")
        assertEquals("Bearer user_token", req.token)
    }
}
