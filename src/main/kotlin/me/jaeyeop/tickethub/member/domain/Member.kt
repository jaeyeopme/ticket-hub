package me.jaeyeop.tickethub.member.domain

import me.jaeyeop.tickethub.auth.domain.TokenPayload
import me.jaeyeop.tickethub.member.adaptor.`in`.request.CreateMemberRequest
import me.jaeyeop.tickethub.support.domain.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDateTime
import kotlin.reflect.KFunction1

@Entity
class Member(
    @Column(name = "refresh_token", unique = true)
    var refreshToken: String? = null,

    @Column(name = "email", unique = true)
    val email: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "name")
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: Role = Role.BUYER,

    @Column(name = "phone_number", unique = true)
    val phoneNumber: String,

    @Column(name = "deleted_at")
    private var deletedAt: LocalDateTime? = null,
) : AbstractEntity(), TokenPayload {

    companion object {
        fun from(
            request: CreateMemberRequest,
            encode: KFunction1<String, String>
        ): Member {
            return Member(
                email = request.email,
                password = encode(request.password),
                name = request.name,
                phoneNumber = request.phoneNumber
            )
        }
    }

    override fun role(): Role = role

}
