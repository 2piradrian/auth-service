package com.twopiradrian.auth_service.domain.messages

enum class OtherMessages(
    private val message: String,
    private val httpCode: Int
) {
    SUCCESS("Success", 200),
}