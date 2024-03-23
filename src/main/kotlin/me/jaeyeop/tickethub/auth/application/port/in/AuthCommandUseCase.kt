package me.jaeyeop.tickethub.auth.application.port.`in`

import me.jaeyeop.tickethub.auth.adaptor.`in`.request.RefreshAccessTokenRequest
import me.jaeyeop.tickethub.auth.domain.MemberPrincipal
import me.jaeyeop.tickethub.auth.domain.TokenPair
import me.jaeyeop.tickethub.auth.domain.TokenPayload

interface AuthCommandUseCase {

    fun logout(memberPrincipal: MemberPrincipal)

    fun updateRefreshToken(tokenPayload: TokenPayload): TokenPair

    fun refreshAccessToken(request: RefreshAccessTokenRequest): String

}
