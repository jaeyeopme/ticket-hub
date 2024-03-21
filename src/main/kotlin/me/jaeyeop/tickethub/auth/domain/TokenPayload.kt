package me.jaeyeop.tickethub.auth.domain

import me.jaeyeop.tickethub.member.domain.Role
import io.jsonwebtoken.Claims

private const val MEMBER_ID_KEY = "memberId"
private const val MEMBER_ROLE_KEY = "memberRole"

interface TokenPayload {

    fun id(): Long

    fun role(): Role

    fun claims(): Map<String, Any> =
        mapOf(
            MEMBER_ID_KEY to id(),
            MEMBER_ROLE_KEY to role()
        )

    companion object {
        fun from(claims: Claims): TokenPayload {
            return object : TokenPayload {
                override fun id() = claims[MEMBER_ID_KEY] as Long
                override fun role() = claims[MEMBER_ROLE_KEY] as Role
            }
        }
    }

}
