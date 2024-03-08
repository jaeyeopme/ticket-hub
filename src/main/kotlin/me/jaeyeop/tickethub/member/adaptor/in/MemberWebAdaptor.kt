package me.jaeyeop.tickethub.member.adaptor.`in`

import me.jaeyeop.tickethub.member.adaptor.`in`.request.CreateMemberRequest
import me.jaeyeop.tickethub.support.endpoint.MEMBER_URL
import me.jaeyeop.tickethub.support.response.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(MEMBER_URL)
@RestController
class MemberWebAdaptor {

    @PostMapping
    fun create(@RequestBody request: CreateMemberRequest): ApiResponse<Unit> {
        return ApiResponse.created()
    }

}
