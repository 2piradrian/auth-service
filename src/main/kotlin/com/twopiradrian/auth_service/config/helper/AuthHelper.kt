package com.twopiradrian.auth_service.config.helper

import com.twopiradrian.auth_service.domain.entity.Token
import com.twopiradrian.auth_service.domain.entity.User
import org.springframework.security.crypto.password.PasswordEncoder

class AuthHelper(
    private val passwordEncoder: PasswordEncoder,
    private val tokenHelper: TokenHelper
) {
    // TODO: Finish the implementation
    fun hashPassword(password: String): String {
        return this.passwordEncoder.encode(password)
    }

    fun validatePassword(user: User, password: String): Boolean {
        return this.passwordEncoder.matches(password, user.password)
    }

    fun createToken(user: User?): Token {
        return Token(this.tokenHelper.createToken(user))
    }

    fun validateToken(token: String?): String? {
        if (token.isNullOrEmpty()) {
            return null
        }

        if (!token.startsWith("Bearer ")) {
            return null
        }

        val tokenValue = token.replace("Bearer ", "")
        if (this.tokenHelper.validateToken(tokenValue)) {
            return tokenValue
        }

        return null
    }

    fun getSubject(token: String?): String {
        return this.tokenHelper.getSubject(token)
    }


}