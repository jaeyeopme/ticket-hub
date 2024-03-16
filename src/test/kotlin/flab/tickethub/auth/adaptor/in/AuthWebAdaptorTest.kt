package me.jaeyeop.tickethub.auth.adaptor.`in`

import me.jaeyeop.tickethub.auth.adaptor.`in`.request.LoginRequest
import me.jaeyeop.tickethub.auth.application.port.`in`.AuthQueryUseCase
import me.jaeyeop.tickethub.auth.domain.TokenPair
import me.jaeyeop.tickethub.support.RestDocsSupport
import me.jaeyeop.tickethub.support.constant.ApiEndpoint
import io.restassured.http.ContentType
import org.hamcrest.core.IsEqual.equalTo
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import java.net.URI

class AuthWebAdaptorTest : RestDocsSupport() {

    private val authQueryUseCase = mock(AuthQueryUseCase::class.java)

    @Test
    fun `로그인 성공`() {
        val request = LoginRequest(
            email = "email@email.com",
            password = "password",
        )
        val tokenPair = TokenPair(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )

        given(authQueryUseCase.login(request)).willReturn(tokenPair)

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

    override fun controller(): Any {
        return AuthWebAdaptor(authQueryUseCase)
    }

}
