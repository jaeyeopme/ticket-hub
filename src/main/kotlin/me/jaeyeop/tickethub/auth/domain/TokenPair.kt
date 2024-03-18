package me.jaeyeop.tickethub.auth.domain

data class TokenPair(
    @field:Transient val memberId: Long,
    val accessToken: String,
    val refreshToken: String
)
