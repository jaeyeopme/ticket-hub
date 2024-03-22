package me.jaeyeop.tickethub.support.security

import me.jaeyeop.tickethub.auth.domain.TokenPayload
import me.jaeyeop.tickethub.member.domain.Role
import me.jaeyeop.tickethub.support.config.time.DateTimeProvider
import me.jaeyeop.tickethub.support.error.ErrorCode
import me.jaeyeop.tickethub.support.properties.JwtProperties
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*

class JwtProviderTest {

    private lateinit var tokenProvider: JwtProvider

    private lateinit var expiredTokenProvider: JwtProvider

    private lateinit var invalidKeyTokenProvider: JwtProvider

    @BeforeEach
    fun setUp() {
        val accessKey = "asdfj08wqer98124j12l41k2h3lj1h2klj3h12lk"
        val refreshKey = "lsakjdflkajsdl;kfj;salkdjfl;ksajd;lfkjasl;kjdfl;kasjdl;fkjas;l"
        val accessExp = Duration.ofMinutes(10)
        val refreshExp = Duration.ofDays(180)
        val now = Date()

        tokenProvider = JwtProvider(
            jwtProperties = JwtProperties(
                accessKey = accessKey,
                refreshKey = refreshKey,
                accessExp = accessExp,
                refreshExp = refreshExp,
            ),
            dateTimeProvider = object : DateTimeProvider {
                override fun nowDate() = now
            }
        )

        expiredTokenProvider = JwtProvider(
            jwtProperties = JwtProperties(
                accessKey = accessKey,
                refreshKey = refreshKey,
                accessExp = accessExp,
                refreshExp = refreshExp,
            ),
            dateTimeProvider = object : DateTimeProvider {
                override fun nowDate() =
                    Date.from(now.toInstant().minus(10, ChronoUnit.MINUTES))
            }
        )

        invalidKeyTokenProvider = JwtProvider(
            jwtProperties = JwtProperties(
                accessKey = "INVALID_ACCESS_TOKEN_INVALID_ACCESS_TOKEN_INVALID_ACCESS_TOKEN",
                refreshKey = "INVALID_REFRESH_TOKEN_INVALID_REFRESH_TOKEN_INVALID_REFRESH_TOKEN",
                accessExp = accessExp,
                refreshExp = refreshExp,
            ),
            dateTimeProvider = object : DateTimeProvider {
                override fun nowDate() = now
            }
        )
    }

    @Test
    fun `엑세스 토큰 검증 성공`() {
        val tokenPayload = object : TokenPayload {
            override fun id() = 1L
            override fun role() = Role.BUYER
        }

        val accessToken = tokenProvider.generateAccessToken(tokenPayload)

        assertDoesNotThrow { tokenProvider.validateAccessToken(accessToken) }
    }

    @Test
    fun `만료된 엑세스 토큰 검증 실패`() {
        val tokenPayload = object : TokenPayload {
            override fun id() = 1L
            override fun role() = Role.BUYER
        }

        val expiredAccessToken = expiredTokenProvider.generateAccessToken(tokenPayload)

        assertThrows<CredentialsExpiredException>(
            ErrorCode.EXPIRED_TOKEN.message
        ) { tokenProvider.validateAccessToken(expiredAccessToken) }
    }

    @Test
    fun `잘못된 엑세스 토큰 검증 실패`() {
        val tokenPayload = object : TokenPayload {
            override fun id() = 1L
            override fun role() = Role.BUYER
        }

        val invalidKeyAccessToken = invalidKeyTokenProvider.generateAccessToken(tokenPayload)

        assertThrows<BadCredentialsException>(
            ErrorCode.INVALID_TOKEN.message
        ) { tokenProvider.validateAccessToken(invalidKeyAccessToken) }
    }

    @Test
    fun `잘못된 형식의 엑세스 토큰 검증 실패`() {
        val tokenPayload = object : TokenPayload {
            override fun id() = 1L
            override fun role() = Role.BUYER
        }
        val nonPrefixAccessToken =
            tokenProvider.generateAccessToken(tokenPayload).removePrefix(BEARER_PREFIX)

        assertThrows<BadCredentialsException>(
            ErrorCode.INVALID_TOKEN.message
        ) { tokenProvider.validateAccessToken(nonPrefixAccessToken) }
    }

    @Test
    fun `비어있는 엑세스 토큰 검증 실패`() {
        val blankAccessToken = ""

        assertThrows<BadCredentialsException>(
            ErrorCode.INVALID_TOKEN.message
        ) { tokenProvider.validateAccessToken(blankAccessToken) }
    }

}
