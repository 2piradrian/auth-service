package com.twopiradrian.auth_service.data.sql.mapper

import com.twopiradrian.auth_service.data.sql.model.UserModel
import com.twopiradrian.auth_service.domain.entity.User

class UserEntityMapper {

    companion object {

        fun toDomain(userModel: UserModel): User {
            return User(
                userModel.getId(),
                userModel.getUsername(),
                userModel.getEmail(),
                userModel.getPassword(),
                userModel.getRoles(),
                userModel.getStatus(),
                userModel.getCreatedAt(),
                userModel.getLastLogin()
            )
        }

        fun toModel(user: User): UserModel {
            return UserModel(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getLastLogin(),
            )
        }

    }

}