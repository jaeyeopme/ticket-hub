package me.jaeyeop.tickethub.support.config

import me.jaeyeop.tickethub.support.constant.ApiEndpoint
import me.jaeyeop.tickethub.support.security.TokenAuthenticationFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.HandlerExceptionResolver

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val tokenAuthenticationFilter: TokenAuthenticationFilter,
    private val handlerExceptionResolver: HandlerExceptionResolver
) {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .csrf { it.disable() }
            .cors { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        httpSecurity
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .logout { it.disable() }

        httpSecurity
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        HttpMethod.POST,
                        ApiEndpoint.MEMBER,
                        "${ApiEndpoint.AUTH}/login",
                        "${ApiEndpoint.AUTH}/refresh"
                    )
                    .permitAll()
                it.anyRequest().authenticated()
            }

        httpSecurity
            .addFilterBefore(
                tokenAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )

        httpSecurity.exceptionHandling {
            it.authenticationEntryPoint(::resolveException)
            it.accessDeniedHandler(::resolveException)
        }

        return httpSecurity.build()
    }

    private fun resolveException(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        exception: RuntimeException
    ) {
        handlerExceptionResolver.resolveException(
            httpServletRequest,
            httpServletResponse,
            null,
            exception
        )
    }

    @Bean
    fun passwordEncoder(): Argon2PasswordEncoder =
        Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()

}