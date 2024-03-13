package me.jaeyeop.tickethub.member.adaptor.`in`

import me.jaeyeop.tickethub.member.adaptor.`in`.request.CreateMemberRequest
import me.jaeyeop.tickethub.member.application.port.`in`.MemberCommandUseCase
import me.jaeyeop.tickethub.support.RestDocsSupport
import me.jaeyeop.tickethub.support.endpoint.MEMBER_URL
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.willDoNothing
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import java.net.URI

class MemberWebAdaptorTest : RestDocsSupport() {

    private val memberCommandUseCase = mock(MemberCommandUseCase::class.java)

    @Test
    fun `회원가입 성공`() {
        val request = CreateMemberRequest(
            email = "email@email.com",
            password = "password",
            name = "name",
            phoneNumber = "nickname"
        )

        willDoNothing().given(memberCommandUseCase).create(request)

        mockMvc
            .contentType(ContentType.JSON)
            .body(convert(request))
            .post(URI.create(MEMBER_URL))
            .then()
            .status(HttpStatus.CREATED)
            .apply(
                document(
                    requestFields(
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("password").description("비밀번호"),
                        fieldWithPath("name").description("이름"),
                        fieldWithPath("phoneNumber").description("전화번호")
                    )
                )
            )
    }

    override fun controller(): Any {
        return MemberWebAdaptor(memberCommandUseCase)
    }

}
