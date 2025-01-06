package com.twopiradrian.auth_service.config.helper

import com.twopiradrian.auth_service.config.env.Environment

class EmailHelper(
    private val environment: Environment
) {

    fun validateEmailHTML(token: String): String {
        val link = "${environment.getUrl()}/api/auth/validate-email?token=$token"

        return """
            <h1>Validate your email</h1>
            <p>Click the link below to validate your email</p>
            <a href="$link">Validate email</a>
        """.trimIndent()
    }

}