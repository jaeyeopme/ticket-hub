package me.jaeyeop.tickethub.auth.application.service

import me.jaeyeop.tickethub.auth.adaptor.`in`.requset.LoginRequest
import me.jaeyeop.tickethub.auth.application.port.`in`.AuthQueryUseCase
import me.jaeyeop.tickethub.auth.domain.TokenPair
import org.springframework.stereotype.Service

@Service
class AuthQueryService : AuthQueryUseCase {
    override fun login(request: LoginRequest): TokenPair {
        return TokenPair(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }

}