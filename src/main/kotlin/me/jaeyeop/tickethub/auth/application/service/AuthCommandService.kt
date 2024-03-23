package me.jaeyeop.tickethub.auth.application.service

import me.jaeyeop.tickethub.auth.adaptor.`in`.request.RefreshAccessTokenRequest
import me.jaeyeop.tickethub.auth.application.port.`in`.AuthCommandUseCase
import me.jaeyeop.tickethub.auth.domain.MemberPrincipal
import me.jaeyeop.tickethub.auth.domain.TokenPair
import me.jaeyeop.tickethub.auth.domain.TokenPayload
import me.jaeyeop.tickethub.member.application.port.out.MemberQueryPort
import me.jaeyeop.tickethub.support.error.ApiException
import me.jaeyeop.tickethub.support.error.ErrorCode
import me.jaeyeop.tickethub.support.security.TokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class AuthCommandService(
    private val memberQueryPort: MemberQueryPort,
    private val tokenProvider: TokenProvider
) : AuthCommandUseCase {

    override fun updateRefreshToken(tokenPayload: TokenPayload): TokenPair {
        val tokenPair = tokenProvider.generateTokenPair(tokenPayload)
        val memberId = tokenPair.tokenPayload.id()

        val member = findById(memberId)
        member.login(tokenPair.refreshToken)

        return tokenPair
    }

    override fun refreshAccessToken(request: RefreshAccessTokenRequest): String {
        val tokenPayload = tokenProvider.validateRefreshToken(request.refreshToken)
        val member = memberQueryPort.findById(tokenPayload.id())
            ?: throw ApiException(ErrorCode.NOT_FOUND_MEMBER)
        member.validateRefreshToken(request.refreshToken)

        return tokenProvider.generateAccessToken(tokenPayload)
    }

    override fun logout(memberPrincipal: MemberPrincipal) {
        val member = findById(memberPrincipal.id())
        member.logout()
    }

    private fun findById(memberId: Long) =
        memberQueryPort.findById(memberId)
            ?: throw ApiException(ErrorCode.NOT_FOUND_MEMBER)

}
