package me.jaeyeop.tickethub.support.security

import me.jaeyeop.tickethub.member.domain.Role
import org.springframework.security.test.context.support.WithSecurityContext

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithMockMemberSecurityContextFactory::class)
annotation class WithMockMember(
    val id: Long = 1L,
    val role: Role = Role.BUYER
)
