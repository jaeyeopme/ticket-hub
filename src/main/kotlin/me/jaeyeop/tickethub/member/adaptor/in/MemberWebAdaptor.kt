package me.jaeyeop.tickethub.member.adaptor.`in`

import me.jaeyeop.tickethub.member.adaptor.`in`.request.CreateMemberRequest
import me.jaeyeop.tickethub.member.application.port.`in`.MemberCommandUseCase
import me.jaeyeop.tickethub.support.constant.ApiEndpoint
import me.jaeyeop.tickethub.support.response.ApiResult
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(ApiEndpoint.MEMBER)
@RestController
class MemberWebAdaptor(
    private val memberCommandUseCase: MemberCommandUseCase
) {

    @PostMapping
    fun create(@Valid @RequestBody request: CreateMemberRequest): ResponseEntity<Unit> {
        memberCommandUseCase.create(request)
        return ApiResult.created()
    }

}
