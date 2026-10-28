package com.twopiradrian.auth_service.presentation.dto.auth.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class ResendEmailReq(
    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    val email: String?
)
