package com.twopiradrian.auth_service.infrastructure.datasource.sql

import com.twopiradrian.auth_service.data.sql.model.UserModel
import org.springframework.data.jpa.repository.JpaRepository

interface PostgresUserRepository : JpaRepository<UserModel, String>  {
}