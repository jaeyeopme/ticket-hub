package me.jaeyeop.tickethub.auth.domain

import io.jsonwebtoken.Claims

private const val MEMBER_ID_KEY = "memberId"

class TokenPayload(
    private val memberId: Long
) {

    constructor(claims: Claims) : this((claims[MEMBER_ID_KEY] as Int).toLong())

    fun toClaims(): Map<String, Any> {
        return mapOf(
            MEMBER_ID_KEY to memberId
        )
    }

}
