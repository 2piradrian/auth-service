package com.twopiradrian.auth_service.presentation.dto.auth.request

import jakarta.validation.constraints.NotBlank

data class AuthenticateUserReq(
    @field:NotBlank
    val token: String?
)
