package com.twopiradrian.auth_service.domain.dto.user.response

import com.twopiradrian.auth_service.domain.entity.Token

data class LoginUserRes (

    val token: Token? = null

)
