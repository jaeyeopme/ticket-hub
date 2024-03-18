package me.jaeyeop.tickethub.auth.domain

import me.jaeyeop.tickethub.support.domain.Identifiable

data class TokenPair(
    @field:Transient val memberId: Identifiable,
    val accessToken: String,
    val refreshToken: String
)
