package com.twopiradrian.auth_service.config.helper

import com.twopiradrian.auth_service.domain.entity.Token
import com.twopiradrian.auth_service.domain.entity.TokenType
import com.twopiradrian.auth_service.domain.entity.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthHelper(
    private val passwordEncoder: PasswordEncoder,
    private val tokenHelper: TokenHelper
) {

    fun hashPassword(password: String): String =
        passwordEncoder.encode(password)

    fun validatePassword(user: User, password: String): Boolean =
        passwordEncoder.matches(password, user.getPassword())

    fun createToken(user: User, tokenType: TokenType): String =
        tokenHelper.createToken(user, tokenType)

    fun validateToken(token: String, tokenType: TokenType): String? {

        var tokenValue: String = token
        if (token.startsWith("Bearer ")) {
            tokenValue = token.removePrefix("Bearer ")
        }

        if (!this.tokenHelper.validateToken(tokenValue, tokenType)) {
            return null
        }

        return tokenValue
    }

    fun getSubject(token: String, tokenType: TokenType): String =
        tokenHelper.getSubject(token, tokenType)

}