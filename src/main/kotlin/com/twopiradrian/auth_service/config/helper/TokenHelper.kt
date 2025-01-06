package com.twopiradrian.auth_service.config.helper

import com.twopiradrian.auth_service.domain.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey

@Component
class TokenHelper(
    @Value("\${application.jwt.secret}") private val secret: String,
    @Value("\${application.jwt.expiration}") private val expiration: Long
) {

    private val secretKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
    }

    fun validateToken(token: String): Boolean = getExpirationDate(token).after(Date())

    fun getSubject(token: String): String = getClaims(token, Claims::getSubject)

    fun createToken(user: User): String {
        val now = Date()
        return Jwts.builder()
            .setSubject(user.email)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + expiration))
            .signWith(secretKey)
            .compact()
    }

    private fun getExpirationDate(token: String): Date = getClaims(token, Claims::getExpiration)

    private fun <T> getClaims(token: String, resolver: (Claims) -> T): T =
        resolver(parseClaims(token))

    private fun parseClaims(token: String): Claims = Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .body

}
