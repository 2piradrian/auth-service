package com.twopiradrian.auth_service.presentation.dto.user.request

import jakarta.validation.constraints.NotBlank

data class GetUserByIdReq(
    @field:NotBlank
    val userId: String?
)
