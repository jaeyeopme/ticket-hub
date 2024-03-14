package me.jaeyeop.tickethub.support.error

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val message: String,
    val logLevel: LogLevel,
) {

    DUPLICATED_MEMBER_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.", LogLevel.ERROR)

}