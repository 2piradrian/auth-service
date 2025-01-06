package com.twopiradrian.auth_service.presentation.controller

import com.twopiradrian.auth_service.domain.dto.auth.mapper.AuthMapper
import com.twopiradrian.auth_service.domain.dto.auth.request.AuthenticateUserReq
import com.twopiradrian.auth_service.domain.dto.auth.request.LoginUserReq
import com.twopiradrian.auth_service.domain.dto.auth.request.RegisterUserReq
import com.twopiradrian.auth_service.presentation.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
){

    @GetMapping("/authenticate")
    fun authenticate(
        @RequestHeader(value = "Authorization") token: String?
    ): ResponseEntity<Any> {
        val dto: AuthenticateUserReq = AuthMapper.authenticate().toRequest(token)
        return ResponseEntity.ok(authService.authenticate(dto))
    }

    @PostMapping("/login")
    fun login(
        @RequestBody payload: Map<String?, Any?>
    ): ResponseEntity<Any> {
        val dto: LoginUserReq = AuthMapper.login().toRequest(payload)
        return ResponseEntity.ok(authService.login(dto))
    }

    @PostMapping("/register")
    fun register(
        @RequestBody payload: Map<String?, Any?>
    ): ResponseEntity<Any> {
        val dto: RegisterUserReq = AuthMapper.register().toRequest(payload)
        return ResponseEntity.ok(authService.register(dto))
    }

}