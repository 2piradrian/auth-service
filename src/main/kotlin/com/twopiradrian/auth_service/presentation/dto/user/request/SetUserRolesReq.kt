package com.twopiradrian.auth_service.presentation.dto.user.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class SetUserRolesReq(
    @field:NotBlank
    val token: String?,

    @field:NotBlank
    val userId: String?,

    @field:NotEmpty
    val roles: List<String>?
)
