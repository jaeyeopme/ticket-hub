package me.jaeyeop.tickethub.auth.domain

import me.jaeyeop.tickethub.support.domain.Identifiable
import io.jsonwebtoken.Claims

private const val MEMBER_ID_KEY = "memberId"

data class TokenPayload(
    val memberId: Identifiable
) {

    val claims: Map<String, Any> =
        mapOf(
            MEMBER_ID_KEY to memberId
        )

    constructor(claims: Claims) : this(
        object : Identifiable {
            override fun id(): Long? = claims[MEMBER_ID_KEY] as Long?
        }
    )

}
