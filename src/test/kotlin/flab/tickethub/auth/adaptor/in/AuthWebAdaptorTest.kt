package me.jaeyeop.tickethub.auth.adaptor.`in`

import me.jaeyeop.tickethub.auth.adaptor.`in`.request.LoginRequest
import me.jaeyeop.tickethub.auth.application.port.`in`.AuthQueryUseCase
import me.jaeyeop.tickethub.auth.domain.MemberPrincipal
import me.jaeyeop.tickethub.auth.domain.TokenPair
import me.jaeyeop.tickethub.auth.domain.TokenPayload
import me.jaeyeop.tickethub.member.domain.Role
import me.jaeyeop.tickethub.support.RestDocsSupport
import me.jaeyeop.tickethub.support.constant.ApiEndpoint
import me.jaeyeop.tickethub.support.error.ApiException
import me.jaeyeop.tickethub.support.error.ErrorCode
import me.jaeyeop.tickethub.support.security.WithMockMember
import io.restassured.http.ContentType
import org.hamcrest.core.IsEqual.equalTo
import org.hamcrest.core.IsNull.nullValue
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.willDoNothing
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import java.net.URI

class AuthWebAdaptorTest : RestDocsSupport() {

    private val authQueryUseCase: AuthQueryUseCase = mock(AuthQueryUseCase::class.java)

    private val authCommandUseCase: AuthCommandUseCase = mock(AuthCommandUseCase::class.java)

    @Test
    fun `로그인 성공`() {
        val tokenPayload = object : TokenPayload {
            override fun id() = 1L
            override fun role() = Role.BUYER
        }

        val tokenPair = TokenPair(
            accessToken = "accessToken",
            refreshToken = "refreshToken",
            tokenPayload = tokenPayload
        )

        val request = LoginRequest(
            email = "email@email.com",
            password = "password",
        )

        given(authQueryUseCase.login(request)).willReturn(tokenPayload)
        given(authCommandUseCase.updateRefreshToken(tokenPayload)).willReturn(tokenPair)

        given()
            .contentType(ContentType.JSON)
            .body(convert(request))
            .post(URI.create("${ApiEndpoint.AUTH}${ApiEndpoint.LOGIN_ENDPOINT}"))
            .then()
            .status(HttpStatus.OK)
            .body(
                "data.accessToken", equalTo("accessToken"),
                "data.refreshToken", equalTo("refreshToken")
            )
            .apply(
                document(
                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING)
                            .description("이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING)
                            .description("비밀번호"),
                    ),
                    responseFields(
                        fieldWithPath("data.accessToken").type(JsonFieldType.STRING)
                            .description("엑세스 토큰"),
                        fieldWithPath("data.refreshToken").type(JsonFieldType.STRING)
                            .description("리프레시 토큰"),
                    )
                )
            )
    }

    @Test
    fun `잘못된 이메일 또는 비밀번호 로그인 실패`() {
        val request = LoginRequest(
            email = "email@email.com",
            password = "password",
        )

        given(authQueryUseCase.login(request)).willThrow(ApiException(ErrorCode.INVALID_EMAIL_OR_PASSWORD))

        given()
            .contentType(ContentType.JSON)
            .body(convert(request))
            .post(URI.create("${ApiEndpoint.AUTH}${ApiEndpoint.LOGIN_ENDPOINT}"))
            .then()
            .status(HttpStatus.UNAUTHORIZED)
            .body(
                "code", equalTo("INVALID_EMAIL_OR_PASSWORD"),
                "message", equalTo("잘못된 이메일 또는 비밀번호입니다."),
                "data", nullValue()
            )
    }

    @WithMockMember
    @Test
    fun `로그아웃 성공`(memberPrincipal: MemberPrincipal) {
        willDoNothing().given(authCommandUseCase).logout(memberPrincipal)

        given()
            .post(URI.create("${ApiEndpoint.AUTH}${ApiEndpoint.LOGOUT_ENDPOINT}"))
            .then()
            .status(HttpStatus.OK)
    }

    override fun controller(): Any {
        return AuthWebAdaptor(authQueryUseCase, authCommandUseCase)
    }

}
