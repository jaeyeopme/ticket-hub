package me.jaeyeop.tickethub.auth.adaptor.`in`

interface AuthCommandUseCase {

    fun updateRefreshToken(memberId: Long, refreshToken: String)

}
