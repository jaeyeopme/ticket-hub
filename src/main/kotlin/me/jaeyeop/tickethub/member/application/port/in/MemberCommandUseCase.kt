package me.jaeyeop.tickethub.member.application.port.`in`

import me.jaeyeop.tickethub.member.adaptor.`in`.request.CreateMemberRequest

interface MemberCommandUseCase {

    fun create(request: CreateMemberRequest)

}
