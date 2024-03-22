package me.jaeyeop.tickethub.support.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class AbstractEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected val id: Long? = null,

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "modified_at")
    private var modifiedAt: LocalDateTime? = null,
)
