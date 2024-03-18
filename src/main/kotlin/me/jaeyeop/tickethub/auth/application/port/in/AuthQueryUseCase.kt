package me.jaeyeop.tickethub.auth.application.port.`in`

import me.jaeyeop.tickethub.auth.adaptor.`in`.request.LoginRequest
import me.jaeyeop.tickethub.auth.domain.TokenPayload

interface AuthQueryUseCase {

    fun login(request: LoginRequest): TokenPayload

}
