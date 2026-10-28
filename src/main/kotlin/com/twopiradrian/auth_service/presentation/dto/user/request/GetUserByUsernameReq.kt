package com.twopiradrian.auth_service.presentation.dto.user.request

import jakarta.validation.constraints.NotBlank

data class GetUserByUsernameReq(
    @field:NotBlank
    val username: String?
)
