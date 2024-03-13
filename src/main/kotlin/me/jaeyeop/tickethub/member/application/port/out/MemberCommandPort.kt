package me.jaeyeop.tickethub.member.application.port.out

import me.jaeyeop.tickethub.member.domain.Member

interface MemberCommandPort {

    fun create(member: Member)

}
