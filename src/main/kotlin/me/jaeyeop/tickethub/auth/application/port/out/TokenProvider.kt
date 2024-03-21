package me.jaeyeop.tickethub.auth.application.port.out

import me.jaeyeop.tickethub.auth.domain.TokenPair
import me.jaeyeop.tickethub.auth.domain.TokenPayload

interface TokenProvider {

    fun generateTokenPair(tokenPayload: TokenPayload): TokenPair

    fun generateAccessToken(tokenPayload: TokenPayload): String

    fun generateRefreshToken(tokenPayload: TokenPayload): String

    fun validateAccessToken(token: String?): TokenPayload

    fun validateRefreshToken(token: String?): TokenPayload

}
