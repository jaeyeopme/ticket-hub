package me.jaeyeop.tickethub.support.constant

object Regexp {
    const val EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}\$"
    const val PHONE_NUMBER = "^\\d{3}\\d{3,4}\\d{4}\$"
}

object Message {
    const val EMAIL = "유효하지 않은 이메일 형식입니다"
    const val PHONE_NUMBER = "유효하지 않은 전화번호 형식입니다"
}