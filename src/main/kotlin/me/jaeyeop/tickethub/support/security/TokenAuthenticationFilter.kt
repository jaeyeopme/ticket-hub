package me.jaeyeop.tickethub.support.security

import me.jaeyeop.tickethub.auth.application.port.out.TokenProvider
import me.jaeyeop.tickethub.auth.domain.TokenPayload
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class TokenAuthenticationFilter(
    private val tokenProvider: TokenProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authentication = attemptAuthentication(request)
            successfulAuthentication(authentication)
        } catch (e: AuthenticationException) {
            unsuccessfulAuthentication(e)
        }

        filterChain.doFilter(request, response)
    }

    private fun attemptAuthentication(request: HttpServletRequest): Authentication {
        val tokenPayload = obtainToken(request)
        return createSuccessAuthentication(tokenPayload, request)
    }

    private fun obtainToken(request: HttpServletRequest): TokenPayload {
        val accessToken = request.getHeader(HttpHeaders.AUTHORIZATION)
        return tokenProvider.validateAccessToken(accessToken)
    }

    private fun createSuccessAuthentication(
        tokenPayload: TokenPayload,
        request: HttpServletRequest
    ): Authentication {
        return UsernamePasswordAuthenticationToken.authenticated(
            tokenPayload.memberId,
            null,
            // TODO: 권한 정보 처리 필요
            emptyList()
        ).apply {
            details = WebAuthenticationDetailsSource().buildDetails(request)
        }
    }

    private fun successfulAuthentication(authentication: Authentication) {
        val context = SecurityContextHolder.createEmptyContext()
        context.authentication = authentication
        SecurityContextHolder.setContext(context)
        logger.debug("Set SecurityContextHolder to $authentication")
    }

    private fun unsuccessfulAuthentication(failed: AuthenticationException) {
        SecurityContextHolder.clearContext()
        logger.trace("Failed to process authentication request", failed)
        logger.trace("Cleared SecurityContextHolder")
        logger.trace("Handling authentication failure")
    }

}
