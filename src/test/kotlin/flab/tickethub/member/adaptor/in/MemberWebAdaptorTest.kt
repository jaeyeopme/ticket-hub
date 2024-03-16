package me.jaeyeop.tickethub.member.adaptor.`in`

import me.jaeyeop.tickethub.member.adaptor.`in`.request.CreateMemberRequest
import me.jaeyeop.tickethub.member.application.port.`in`.MemberCommandUseCase
import me.jaeyeop.tickethub.support.RestDocsSupport
import me.jaeyeop.tickethub.support.constant.ApiEndpoint
import me.jaeyeop.tickethub.support.error.ApiException
import me.jaeyeop.tickethub.support.error.ErrorCode
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.willDoNothing
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import java.net.URI

class MemberWebAdaptorTest : RestDocsSupport() {

    private val memberCommandUseCase = mock(MemberCommandUseCase::class.java)

    @Test
    fun `회원가입 성공`() {
        val request = CreateMemberRequest(
            email = "email@email.com",
            password = "password",
            name = "name",
            phoneNumber = "01012345678"
        )

        willDoNothing().given(memberCommandUseCase).create(request)

        given()
            .contentType(ContentType.JSON)
            .body(convert(request))
            .post(URI.create(ApiEndpoint.MEMBER))
            .then()
            .status(HttpStatus.CREATED)
            .apply(
                document(
                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING)
                            .description("이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING)
                            .description("비밀번호"),
                        fieldWithPath("name").type(JsonFieldType.STRING)
                            .description("이름"),
                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING)
                            .description("전화번호")
                    )
                )
            )
    }

    @Test
    fun `회원가입 실패 이메일 중복`() {
        val request = CreateMemberRequest(
            email = "email@email.com",
            password = "password",
            name = "name",
            phoneNumber = "01012345678"
        )

        given(memberCommandUseCase.create(request)).willThrow(ApiException(ErrorCode.DUPLICATED_MEMBER_EMAIL))

        given()
            .contentType(ContentType.JSON)
            .body(convert(request))
            .post(URI.create(ApiEndpoint.MEMBER))
            .then()
            .status(HttpStatus.CONFLICT)
            .body(
                "code", equalTo("DUPLICATED_MEMBER_EMAIL"),
                "message", equalTo("이미 존재하는 이메일입니다."),
                "data", nullValue()
            )
            .apply(
                document(
                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING)
                            .description("이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING)
                            .description("비밀번호"),
                        fieldWithPath("name").type(JsonFieldType.STRING)
                            .description("이름"),
                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING)
                            .description("전화번호")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING)
                            .description("에러 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("에러 메시지"),
                        fieldWithPath("data").type(JsonFieldType.NULL)
                            .description("에러 데이터"),
                    )
                )
            )
    }

    override fun controller(): Any {
        return MemberWebAdaptor(memberCommandUseCase)
    }

}
