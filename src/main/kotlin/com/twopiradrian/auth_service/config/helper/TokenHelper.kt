package com.twopiradrian.auth_service.config.helper

import com.twopiradrian.auth_service.config.env.EnvironmentVars
import com.twopiradrian.auth_service.domain.entity.TokenType
import com.twopiradrian.auth_service.domain.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class TokenHelper(
    private val environmentVars: EnvironmentVars,
) {

    fun validateToken(token: String, tokenType: TokenType): Boolean =
        getExpirationDate(token, tokenType).after(Date())

    fun getSubject(token: String, tokenType: TokenType): String =
        getClaims(token, tokenType, Claims::getSubject)

    fun createToken(user: User, tokenType: TokenType): String {
        val now = Date()
        val secretKey = getSecretKey(tokenType)

        return Jwts.builder()
            .setSubject(user.getId())
            .setIssuedAt(now)
            .setExpiration(Date(now.time + environmentVars.getExpiration()))
            .signWith(secretKey)
            .compact()
    }

    private fun getSecretKey(tokenType: TokenType): SecretKey =
        when (tokenType) {
            TokenType.AUTHENTICATION -> Keys.hmacShaKeyFor(environmentVars.getSecretKeyLogin().encoded)
            TokenType.EMAIL_VALIDATION -> Keys.hmacShaKeyFor(environmentVars.getSecretKeyValidation().encoded)
        }

    private fun getExpirationDate(token: String, tokenType: TokenType): Date =
        getClaims(token, tokenType, Claims::getExpiration)

    private fun <T> getClaims(token: String, tokenType: TokenType, resolver: (Claims) -> T): T =
        resolver(parseClaims(token, tokenType))

    private fun parseClaims(token: String, tokenType: TokenType): Claims = Jwts.parserBuilder()
        .setSigningKey(getSecretKey(tokenType))
        .build()
        .parseClaimsJws(token)
        .body

}
