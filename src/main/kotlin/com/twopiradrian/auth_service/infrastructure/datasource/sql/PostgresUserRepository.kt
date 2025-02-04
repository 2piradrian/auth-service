package com.twopiradrian.auth_service.infrastructure.datasource.sql

import com.twopiradrian.auth_service.data.sql.model.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostgresUserRepository : JpaRepository<UserModel, String>  {

    fun findByEmail(email: String): Optional<UserModel>

    fun findByUsername(username: String): Optional<UserModel>

}