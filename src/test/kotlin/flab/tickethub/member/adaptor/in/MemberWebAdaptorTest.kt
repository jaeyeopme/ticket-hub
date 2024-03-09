package me.jaeyeop.tickethub.member.adaptor.`in`

import me.jaeyeop.tickethub.member.adaptor.`in`.request.CreateMemberRequest
import me.jaeyeop.tickethub.support.RestDocsSupport
import me.jaeyeop.tickethub.support.endpoint.MEMBER_URL
import io.restassured.http.ContentType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import java.net.URI

class MemberWebAdaptorTest : RestDocsSupport() {

    @DisplayName("회원가입")
    @Test
    fun `success create member`() {
        val request = CreateMemberRequest(
            email = "email@email.com",
            password = "password",
            name = "name",
            phoneNumber = "nickname"
        )

        given()
            .contentType(ContentType.JSON)
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
        return MemberWebAdaptor()
    }

}
