package com.twopiradrian.auth_service.domain.entity

data class Token (
    private val accessToken: String,
){

    fun getAccessToken() = accessToken

}