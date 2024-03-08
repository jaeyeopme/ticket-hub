package me.jaeyeop.tickethub.member.adaptor.`in`.request

data class CreateMemberRequest(
    val email: String,
    val password: String,
    val name: String,
    val phoneNumber: String
)
