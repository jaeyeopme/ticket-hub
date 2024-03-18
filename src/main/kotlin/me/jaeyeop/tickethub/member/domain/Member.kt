package me.jaeyeop.tickethub.member.domain

import me.jaeyeop.tickethub.member.adaptor.`in`.request.CreateMemberRequest
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity
class Member(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    val id: Long? = null,

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

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private val createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "modified_at")
    private val modifiedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    private val deletedAt: LocalDateTime? = null,
) {

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
