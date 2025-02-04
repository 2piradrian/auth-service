package com.twopiradrian.auth_service.config.bean

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests { auth ->
                auth.anyRequest().permitAll()
            }
        return http.build()
    }

    @Bean
    fun passEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

}