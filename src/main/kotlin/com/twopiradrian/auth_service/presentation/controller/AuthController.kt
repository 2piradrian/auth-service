package com.twopiradrian.auth_service.presentation.controller

import com.twopiradrian.auth_service.application.service.AuthServiceI
import com.twopiradrian.auth_service.domain.messages.OtherMessages
import com.twopiradrian.auth_service.presentation.dto.auth.mapper.*
import com.twopiradrian.auth_service.presentation.dto.auth.request.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthServiceI
) {

    @GetMapping("/authenticate")
    fun authenticate(
        @RequestHeader(value = "Authorization") token: String?
    ): ResponseEntity<Any> {
        val request: AuthenticateUserReq = AuthenticateUserMapper.toRequest(token)
        return ResponseEntity.ok(this.authService.authenticate(request))
    }

    @PostMapping("/login")
    fun login(
        @RequestBody payload: Map<String, Any>
    ): ResponseEntity<Any> {
        val request: LoginUserReq = LoginUserMapper.toRequest(payload)
        return ResponseEntity.ok(this.authService.login(request))
    }

    @PostMapping("/register")
    fun register(
        @RequestBody payload: Map<String, Any>
    ): ResponseEntity<Any> {
        val request: RegisterUserReq = RegisterUserMapper.toRequest(payload)
        return ResponseEntity.ok(this.authService.register(request))
    }

    @GetMapping("/verify-email/{token}")
    fun verifyEmail(
        @PathVariable(value = "token") token: String
    ): ResponseEntity<Any> {
        val request: VerifyEmailReq = VerifyEmailMapper.toRequest(token)
        this.authService.verifyEmail(request)
        return ResponseEntity.ok(OtherMessages.SUCCESS)
    }

    @PostMapping("/resend-verify-email")
    fun resendVerifyEmail(
        @RequestBody payload: Map<String, Any>
    ): ResponseEntity<Any> {
        val request: ResendEmailReq = ResendEmailMapper.toRequest(payload)
        this.authService.resendVerifyEmail(request)
        return ResponseEntity.ok(OtherMessages.SUCCESS)
    }

}
