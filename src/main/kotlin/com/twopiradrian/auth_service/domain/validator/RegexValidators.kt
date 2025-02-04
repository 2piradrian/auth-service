package com.twopiradrian.auth_service.domain.validator

enum class RegexValidators(private val regex: String) {
    USERNAME("^[a-zA-Z0-9]{3,20}$"),
    PASSWORD("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"),
    EMAIL("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    fun getRegex(): String = regex
}
