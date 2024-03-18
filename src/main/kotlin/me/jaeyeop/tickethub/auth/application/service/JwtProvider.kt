package me.jaeyeop.tickethub.auth.application.service

import me.jaeyeop.tickethub.auth.application.port.out.TokenProvider
import me.jaeyeop.tickethub.auth.domain.TokenPair
import me.jaeyeop.tickethub.auth.domain.TokenPayload
import me.jaeyeop.tickethub.support.error.ApiException
import me.jaeyeop.tickethub.support.error.ErrorCode
import me.jaeyeop.tickethub.support.properties.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.time.Clock
import java.util.*
import javax.crypto.SecretKey

const val BEARER_PREFIX = "Bearer "

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val clock: Clock
) : TokenProvider {

    private val accessSecretKey =
        Keys.hmacShaKeyFor(jwtProperties.accessKey.toByteArray(Charsets.UTF_8))

    private val refreshSecretKey =
        Keys.hmacShaKeyFor(jwtProperties.refreshKey.toByteArray(Charsets.UTF_8))

    override fun generateTokenPair(tokenPayload: TokenPayload): TokenPair {
        val accessToken = generateAccessToken(tokenPayload)
        val refreshToken = generateRefreshToken(tokenPayload)
        return TokenPair(tokenPayload.memberId, accessToken, refreshToken)
    }

    override fun generateAccessToken(tokenPayload: TokenPayload): String {
        return generateToken(
            accessSecretKey,
            jwtProperties.accessExp.toMillis(),
            tokenPayload.claims
        )
    }

    override fun generateRefreshToken(tokenPayload: TokenPayload): String {
        return generateToken(
            refreshSecretKey,
            jwtProperties.refreshExp.toMillis(),
            tokenPayload.claims
        )
    }

    override fun validateAccessToken(token: String) {
        validateToken(token, accessSecretKey)
    }

    override fun validateRefreshToken(token: String) {
        validateToken(token, refreshSecretKey)
    }

    private fun generateToken(
        secretKey: SecretKey,
        exp: Long,
        claims: Map<String, Any>
    ): String {
        val instant = clock.instant()

        return BEARER_PREFIX + Jwts.builder()
            .signWith(secretKey, Jwts.SIG.HS256)
            .issuedAt(Date.from(instant))
            .expiration(Date.from(instant.plusMillis(exp)))
            .claims(claims)
            .compact()
    }

    private fun validateToken(
        token: String,
        secretKey: SecretKey
    ): TokenPayload {
        try {
            require(token.isNotBlank() && token.startsWith(BEARER_PREFIX))
            val parsedClaims = parsedClaims(token.removePrefix(BEARER_PREFIX), secretKey)
            return TokenPayload(parsedClaims)
        } catch (e: ExpiredJwtException) {
            throw ApiException(ErrorCode.EXPIRED_TOKEN)
        } catch (e: JwtException) {
            throw ApiException(ErrorCode.INVALID_TOKEN)
        } catch (e: IllegalArgumentException) {
            throw ApiException(ErrorCode.INVALID_TOKEN)
        }
    }

    private fun parsedClaims(
        token: String,
        secretKey: SecretKey
    ): Claims {
        return Jwts.parser()
            .verifyWith(secretKey)
            .clock { Date.from(clock.instant()) }
            .build()
            .parseSignedClaims(token)
            .payload
    }

}
