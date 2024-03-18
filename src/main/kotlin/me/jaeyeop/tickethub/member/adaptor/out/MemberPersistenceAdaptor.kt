package me.jaeyeop.tickethub.member.adaptor.out

import me.jaeyeop.tickethub.member.application.port.out.MemberCommandPort
import me.jaeyeop.tickethub.member.application.port.out.MemberQueryPort
import me.jaeyeop.tickethub.member.domain.Member
import org.springframework.stereotype.Component

@Component
class MemberPersistenceAdaptor(
    private val memberCrudRepository: MemberCrudRepository
) : MemberCommandPort,
    MemberQueryPort {

    override fun create(member: Member) {
        memberCrudRepository.save(member)
    }

    override fun existsByEmail(email: String): Boolean {
        return memberCrudRepository.existsByEmail(email)
    }

    override fun findByEmail(email: String): Member? {
        return memberCrudRepository.findByEmail(email)
    }

    override fun findById(memberId: Long): Member? {
        return memberCrudRepository.findById(memberId).get()
    }

}
