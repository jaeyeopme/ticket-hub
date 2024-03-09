package me.jaeyeop.tickethub.auth.domain

data class TokenPair(
    val accessToken: String,
    val refreshToken: String
)
