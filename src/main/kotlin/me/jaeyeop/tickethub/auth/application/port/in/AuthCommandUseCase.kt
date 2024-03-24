package me.jaeyeop.tickethub.auth.application.port.`in`

import me.jaeyeop.tickethub.auth.adaptor.`in`.request.LoginRequest
import me.jaeyeop.tickethub.auth.adaptor.`in`.request.RefreshAccessTokenRequest
import me.jaeyeop.tickethub.auth.domain.MemberPrincipal
import me.jaeyeop.tickethub.auth.domain.TokenPair

interface AuthCommandUseCase {
    fun login(request: LoginRequest): TokenPair

    fun logout(memberPrincipal: MemberPrincipal)

    fun refreshAccessToken(request: RefreshAccessTokenRequest): String
}
