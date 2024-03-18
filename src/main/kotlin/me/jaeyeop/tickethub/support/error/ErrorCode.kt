package me.jaeyeop.tickethub.support.error

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val message: String,
    val logLevel: LogLevel,
) {

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력 값이 존재합니다.", LogLevel.ERROR),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.", LogLevel.ERROR),

    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다.", LogLevel.ERROR),

    DUPLICATED_MEMBER_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.", LogLevel.ERROR),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.", LogLevel.ERROR),

    INVALID_EMAIL_OR_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 이메일 또는 비밀번호입니다.", LogLevel.ERROR),

}
