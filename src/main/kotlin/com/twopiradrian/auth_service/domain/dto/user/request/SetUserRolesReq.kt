package com.twopiradrian.auth_service.domain.dto.user.request

import com.twopiradrian.auth_service.domain.entity.Role
import com.twopiradrian.auth_service.domain.error.ErrorHandler
import com.twopiradrian.auth_service.domain.error.ErrorType

class SetUserRolesReq private constructor(
    val userId: String,
    val token: String,
    val roles: Set<Role>
) {
    companion object {
        fun create(
            userId: String?,
            token: String?,
            roles: List<String>?
        ): SetUserRolesReq {

            if (userId.isNullOrEmpty() || roles.isNullOrEmpty() || token.isNullOrEmpty()) {
                throw ErrorHandler(ErrorType.MISSING_REQUIRED_FIELDS)
            }

            for (role in roles) {
                if (Role.entries.find { it.name == role } == null) {
                    throw ErrorHandler(ErrorType.INVALID_FIELDS)
                }
            }

            val rolesSet = roles.map { Role.valueOf(it) }.toSet()

            return SetUserRolesReq(userId, token, rolesSet)
        }
    }
}
