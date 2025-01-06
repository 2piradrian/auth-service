package com.twopiradrian.auth_service.config.helper

import com.twopiradrian.auth_service.domain.entity.Token
import com.twopiradrian.auth_service.domain.entity.User
import org.springframework.security.crypto.password.PasswordEncoder


class AuthHelper(
    private val passwordEncoder: PasswordEncoder,
    private val tokenHelper: TokenHelper
) {

    fun hashPassword(password: String): String = passwordEncoder.encode(password)

    fun validatePassword(user: User, password: String): Boolean =
        passwordEncoder.matches(password, user.password)

    fun createToken(user: User): Token = Token(tokenHelper.createToken(user))

    fun validateToken(token: String): String? {
        val tokenValue = token.takeIf { it.startsWith("Bearer ") }
            ?.removePrefix("Bearer ")

        return if (tokenValue != null && tokenHelper.validateToken(tokenValue)) tokenValue else null
    }

    fun getSubject(token: String): String = tokenHelper.getSubject(token)

}