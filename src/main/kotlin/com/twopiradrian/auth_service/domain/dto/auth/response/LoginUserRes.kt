package com.twopiradrian.auth_service.domain.dto.auth.response

import com.twopiradrian.auth_service.domain.entity.Status
import com.twopiradrian.auth_service.domain.entity.Token

data class LoginUserRes (

    val token: Token,

    val status: Status,

    val email: String,

)
