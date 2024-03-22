package me.jaeyeop.tickethub.support.security

import me.jaeyeop.tickethub.auth.domain.MemberPrincipal
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.test.context.TestSecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory

class WithMockMemberSecurityContextFactory : WithSecurityContextFactory<WithMockMember> {

    override fun createSecurityContext(annotation: WithMockMember): SecurityContext {
        val context = TestSecurityContextHolder.getContext()
        val authentication = UsernamePasswordAuthenticationToken.authenticated(
            MemberPrincipal { annotation.id },
            null,
            listOf(SimpleGrantedAuthority(annotation.role.name))
        )
        context.authentication = authentication

        return context
    }

}
