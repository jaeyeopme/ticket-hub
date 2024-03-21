package me.jaeyeop.tickethub.auth.application.service

import me.jaeyeop.tickethub.auth.adaptor.`in`.AuthCommandUseCase
import me.jaeyeop.tickethub.auth.application.port.out.TokenProvider
import me.jaeyeop.tickethub.auth.domain.TokenPayload
import me.jaeyeop.tickethub.auth.domain.TokenPair
import me.jaeyeop.tickethub.member.application.port.out.MemberQueryPort
import me.jaeyeop.tickethub.support.error.ApiException
import me.jaeyeop.tickethub.support.error.ErrorCode
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

        val member = memberQueryPort.findById(memberId)
            ?: throw ApiException(ErrorCode.NOT_FOUND_MEMBER)

        member.refreshToken = tokenPair.refreshToken

        return tokenPair
    }

}
