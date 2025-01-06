package com.twopiradrian.auth_service.config.helper

import com.twopiradrian.auth_service.config.env.EnvironmentVars

class EmailHelper(
    private val environmentVars: EnvironmentVars
) {

    fun validateEmailHTML(token: String): String {
        val link = "${environmentVars.getUrl()}/api/auth/validate-email?token=$token"

        return """
            <h1>Validate your email</h1>
            <p>Click the link below to validate your email</p>
            <a href="$link">Validate email</a>
        """.trimIndent()
    }

}