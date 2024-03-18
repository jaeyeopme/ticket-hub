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
    private var email: String,
    private var password: String,
    private var name: String,
    private var phoneNumber: String
) {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private val id: Long? = null

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "modified_at")
    private var modifiedAt: LocalDateTime? = null

    @Column(name = "deleted_at")
    private var deletedAt: LocalDateTime? = null

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
