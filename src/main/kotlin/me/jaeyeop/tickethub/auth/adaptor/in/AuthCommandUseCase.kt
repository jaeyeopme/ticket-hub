package me.jaeyeop.tickethub.auth.adaptor.`in`

import me.jaeyeop.tickethub.auth.domain.MemberPrincipal
import me.jaeyeop.tickethub.auth.domain.TokenPair
import me.jaeyeop.tickethub.auth.domain.TokenPayload

interface AuthCommandUseCase {

    fun updateRefreshToken(tokenPayload: TokenPayload): TokenPair

    fun logout(memberPrincipal: MemberPrincipal)

}
