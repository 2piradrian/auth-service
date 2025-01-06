package com.twopiradrian.auth_service.config.env

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import javax.crypto.SecretKey

@Component
class EnvironmentVars(
    @Value("\${application.jwt.secret.login}") private val secretLogin: String,
    @Value("\${application.jwt.secret.validation}") private val secretValidation: String,
    @Value("\${application.jwt.expiration}") private val expiration: Long,
    @Value("\${application.api.url}") private val url: String
) {

    fun getSecretKeyLogin(): SecretKey {
        return Keys.hmacShaKeyFor(secretLogin.toByteArray(StandardCharsets.UTF_8))
    }

    fun getSecretKeyValidation(): SecretKey {
        return Keys.hmacShaKeyFor(secretValidation.toByteArray(StandardCharsets.UTF_8))
    }

    fun getExpiration(): Long {
        return expiration
    }

    fun getUrl(): String {
        return url
    }

}