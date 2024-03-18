package me.jaeyeop.tickethub.member.adaptor.out

import me.jaeyeop.tickethub.member.domain.Member
import org.springframework.data.repository.CrudRepository

interface MemberCrudRepository : CrudRepository<Member, Long> {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): Member?

}
