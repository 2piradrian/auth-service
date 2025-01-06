package com.twopiradrian.auth_service.data.sql.model

import com.twopiradrian.auth_service.domain.entity.Role
import com.twopiradrian.auth_service.domain.entity.Status
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class UserModel () {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private var id: String? = ""

    private var username: String = ""

    private var email: String = ""

    private var password: String = ""

    @Enumerated(EnumType.STRING)
    private var roles: Set<Role> = emptySet()

    @Enumerated(EnumType.STRING)
    private var status: Status = Status.PENDING

    private var createdAt: LocalDateTime = LocalDateTime.now()

    private var lastLogin: LocalDateTime? = null

    constructor(
        id: String?,
        username: String,
        email: String,
        password: String,
        roles: Set<Role>,
        status: Status,
        createdAt: LocalDateTime,
        lastLogin: LocalDateTime?
    ) : this() {
        this.id = id
        this.username = username
        this.email = email
        this.password = password
        this.roles = roles
        this.status = status
        this.createdAt = createdAt
        this.lastLogin = lastLogin
    }

    fun getId(): String? = id
    fun getUsername(): String = username
    fun getEmail(): String = email
    fun getPassword(): String = password
    fun getRoles(): Set<Role> = roles
    fun getStatus(): Status = status
    fun getCreatedAt(): LocalDateTime = createdAt
    fun getLastLogin(): LocalDateTime? = lastLogin

    fun setId(id: String) = run { this.id = id }
    fun setUsername(username: String) = run { this.username = username }
    fun setEmail(email: String) = run { this.email = email }
    fun setPassword(password: String) = run { this.password = password }
    fun setRoles(roles: Set<Role>) = run { this.roles = roles }
    fun setStatus(status: Status) = run { this.status = status }
    fun setCreatedAt(createdAt: LocalDateTime) = run { this.createdAt = createdAt }
    fun setLastLogin(lastLogin: LocalDateTime?) = run { this.lastLogin = lastLogin }

}