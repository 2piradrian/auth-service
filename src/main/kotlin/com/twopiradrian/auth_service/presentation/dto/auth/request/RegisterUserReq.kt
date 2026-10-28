package com.twopiradrian.auth_service.presentation.dto.auth.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class RegisterUserReq(
    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9]{3,20}$")
    val username: String?,

    @field:NotBlank
    @field:Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    val password: String?,

    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    val email: String?
)
