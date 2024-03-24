package me.jaeyeop.tickethub.auth.domain

data class TokenPair(
    @field:Transient val tokenPayload: TokenPayload,
    val accessToken: String,
    val refreshToken: String,
)
