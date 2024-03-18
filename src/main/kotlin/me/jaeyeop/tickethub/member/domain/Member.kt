package me.jaeyeop.tickethub.member.domain

import me.jaeyeop.tickethub.member.adaptor.`in`.request.CreateMemberRequest
import me.jaeyeop.tickethub.support.domain.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.LocalDateTime

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

    @Column(name = "phone_number", unique = true)
    val phoneNumber: String,

    @Column(name = "deleted_at")
    private var deletedAt: LocalDateTime? = null,
) : AbstractEntity() {

    companion object {
        fun from(
            request: CreateMemberRequest,
            encodedPassword: String
        ): Member {
            return Member(
                email = request.email,
                password = encodedPassword,
                name = request.name,
                phoneNumber = request.phoneNumber
            )
        }
    }

}
