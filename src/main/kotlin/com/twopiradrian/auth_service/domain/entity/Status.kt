package com.twopiradrian.auth_service.domain.entity

enum class Status(
    name: String
) {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    DELETED("DELETED"),
    PENDING("PENDING"),
    BANNED("BANNED")
}