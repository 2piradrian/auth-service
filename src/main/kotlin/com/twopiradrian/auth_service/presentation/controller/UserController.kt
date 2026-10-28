package com.twopiradrian.auth_service.presentation.controller

import com.twopiradrian.auth_service.application.service.UserServiceI
import com.twopiradrian.auth_service.presentation.dto.user.mapper.*
import com.twopiradrian.auth_service.presentation.dto.user.request.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserServiceI
) {

    @GetMapping("/get-by-id/{userId}")
    fun getById(
        @PathVariable(value = "userId") userId: String?
    ): ResponseEntity<Any> {
        val request: GetUserByIdReq = GetUserByIdMapper.toRequest(userId)
        return ResponseEntity.ok(this.userService.getById(request))
    }

    @GetMapping("/get-by-username/{username}")
    fun getByUsername(
        @PathVariable(value = "username") username: String?
    ): ResponseEntity<Any> {
        val request: GetUserByUsernameReq = GetUserByUsernameMapper.toRequest(username)
        return ResponseEntity.ok(this.userService.getByUsername(request))
    }

    @PatchMapping("/set-roles")
    fun setRoles(
        @RequestHeader(value = "Authorization") token: String?,
        @RequestBody payload: Map<String, Any>
    ): ResponseEntity<Any> {
        val request: SetUserRolesReq = SetUserRolesMapper.toRequest(token, payload)
        this.userService.setRoles(request)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/delete")
    fun delete(
        @RequestHeader(value = "Authorization") token: String?
    ): ResponseEntity<Any> {
        val request: DeleteUserReq = DeleteUserMapper.toRequest(token)
        this.userService.delete(request)
        return ResponseEntity.ok().build()
    }

}
