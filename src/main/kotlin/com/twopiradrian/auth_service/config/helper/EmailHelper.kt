package com.twopiradrian.auth_service.config.helper

import com.twopiradrian.auth_service.config.env.EnvironmentVars
import org.springframework.stereotype.Component

@Component
class EmailHelper(
    private val environmentVars: EnvironmentVars
) {

    fun verifyEmailHTML(token: String): String {
        val link = "${environmentVars.getUrl()}/api/auth/verify-email/$token"

        return """
            <h1>Verify your email</h1>
            <p>Click the link below to verify your email</p>
            <a href="$link">Verify email</a>
        """.trimIndent()
    }

}