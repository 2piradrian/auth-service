package com.twopiradrian.auth_service.presentation.app.controller

import com.twopiradrian.auth_service.domain.dto.user.mapper.UserMapper
import com.twopiradrian.auth_service.domain.dto.user.request.DeleteUserReq
import com.twopiradrian.auth_service.domain.dto.user.request.GetUserByIdReq
import com.twopiradrian.auth_service.domain.dto.user.request.SetUserRolesReq
import com.twopiradrian.auth_service.presentation.app.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService
){

    @GetMapping("/get-by-id/{userId}")
    fun getById(
        @PathVariable(value = "userId") userId: String?
    ): ResponseEntity<Any> {
        val dto: GetUserByIdReq = UserMapper.getById().toRequest(userId)
        return ResponseEntity.ok(userService.getById(dto))
    }

    @GetMapping("/get-by-username/{username}")
    fun getByUsername(
        @PathVariable(value = "username") username: String?
    ): ResponseEntity<Any> {
        val dto = UserMapper.getByUsername().toRequest(username)
        return ResponseEntity.ok(userService.getByUsername(dto))
    }

    @PatchMapping("/set-roles")
    fun setRoles(
        @RequestHeader(value = "Authorization") token: String?,
        @RequestBody payload: Map<String?, Any?>
    ): ResponseEntity<Any> {
        val dto: SetUserRolesReq = UserMapper.setRoles().toRequest(token, payload)
        return ResponseEntity.ok(userService.setRoles(dto))
    }

    @DeleteMapping("/delete")
    fun delete(
        @RequestHeader(value = "Authorization") token: String?
    ): ResponseEntity<Any> {
        val dto: DeleteUserReq = UserMapper.delete().toRequest(token)
        this.userService.delete(dto)
        return ResponseEntity.ok().build()
    }

}