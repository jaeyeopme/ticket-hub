package me.jaeyeop.tickethub.auth.application.service

import me.jaeyeop.tickethub.auth.adaptor.`in`.AuthCommandUseCase
import me.jaeyeop.tickethub.member.application.port.out.MemberQueryPort
import me.jaeyeop.tickethub.support.error.ApiException
import me.jaeyeop.tickethub.support.error.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class AuthCommandService(
    private val memberQueryPort: MemberQueryPort
) : AuthCommandUseCase {

    override fun updateRefreshToken(
        memberId: Long,
        refreshToken: String
    ) {
        val member = memberQueryPort.findById(memberId)
            ?: throw ApiException(ErrorCode.NOT_FOUND_MEMBER)
        member.refreshToken = refreshToken
    }

}
