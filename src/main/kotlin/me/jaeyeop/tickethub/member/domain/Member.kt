package me.jaeyeop.tickethub.member.domain

import me.jaeyeop.tickethub.auth.domain.TokenPayload
import me.jaeyeop.tickethub.member.adaptor.`in`.request.CreateMemberRequest
import me.jaeyeop.tickethub.support.domain.AbstractEntity
import me.jaeyeop.tickethub.support.error.ApiException
import me.jaeyeop.tickethub.support.error.ErrorCode
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDateTime
import kotlin.reflect.KFunction1

@Entity
class Member(
    @Column(name = "refresh_token", unique = true)
    private var refreshToken: String? = null,

    @Column(name = "email", unique = true, nullable = false)
    val email: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "name")
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: Role = Role.BUYER,

    @Column(name = "phone_number", unique = true, nullable = false)
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

    override fun id() = id
        ?: throw ApiException(ErrorCode.NOT_FOUND_IDENTITY)

    override fun role() = role

    fun login(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    fun logout() {
        this.refreshToken = null
    }

    fun validateRefreshToken(refreshToken: String) {
        if (refreshToken != this.refreshToken) throw ApiException(ErrorCode.INVALID_TOKEN)
    }

}
