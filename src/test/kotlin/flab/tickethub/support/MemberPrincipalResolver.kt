package me.jaeyeop.tickethub.support

import me.jaeyeop.tickethub.auth.domain.MemberPrincipal
import me.jaeyeop.tickethub.support.security.WithMockMember
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.springframework.security.test.context.TestSecurityContextHolder

class MemberPrincipalResolver : ParameterResolver {

    override fun supportsParameter(
        parameterContext: ParameterContext,
        extensionContext: ExtensionContext
    ): Boolean {
        return extensionContext.requiredTestMethod.isAnnotationPresent(WithMockMember::class.java) &&
                parameterContext.parameter.type == MemberPrincipal::class.java
    }

    override fun resolveParameter(
        parameterContext: ParameterContext?,
        extensionContext: ExtensionContext?
    ): Any {
        return TestSecurityContextHolder.getContext().authentication.principal
    }

}
