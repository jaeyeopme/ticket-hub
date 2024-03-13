package me.jaeyeop.tickethub.member.application.port.out

interface MemberQueryPort {

    fun existsByEmail(email: String): Boolean

}
